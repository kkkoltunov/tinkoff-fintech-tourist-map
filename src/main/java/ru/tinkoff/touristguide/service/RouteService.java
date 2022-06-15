package ru.tinkoff.touristguide.service;

import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.tinkoff.touristguide.dto.RouteRequest;
import ru.tinkoff.touristguide.exception.CreateRouteException;
import ru.tinkoff.touristguide.model.Category;
import ru.tinkoff.touristguide.model.Sight;

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static ru.tinkoff.touristguide.util.RouteUtil.BEGIN_TIME_IN_DAY;
import static ru.tinkoff.touristguide.util.RouteUtil.LAST_TIME_IN_DAY;

@Service
@AllArgsConstructor
public class RouteService {

    private final SightService sightService;

    private final CategoryService categoryService;

    public Map<Pair<OffsetTime, OffsetTime>, Sight> getRoute(RouteRequest routeRequest) {
        List<Long> categoriesId = findAllCategories(routeRequest.getCategoriesId());
        List<Sight> sights = getAllSights(categoriesId);
        BigDecimal budget = routeRequest.getBudget();

        OffsetTime timeStart = routeRequest.getVisitTime().getFirst().withOffsetSameInstant(ZoneOffset.UTC);
        OffsetTime timeEnd = routeRequest.getVisitTime().getSecond().withOffsetSameInstant(ZoneOffset.UTC);
        long countHours = findCountHours(timeStart, timeEnd);

        timeEnd = timeStart.plusHours(1);
        return findOptimalRoute(sights, budget, timeStart, timeEnd, countHours);
    }

    private Map<Pair<OffsetTime, OffsetTime>, Sight> findOptimalRoute(List<Sight> sights, BigDecimal budget,
                                                                      OffsetTime timeStart, OffsetTime timeEnd,
                                                                      long countHours) {
        Map<Pair<OffsetTime, OffsetTime>, Sight> recommendedSights = new LinkedHashMap<>();
        for (int i = 0; i < countHours; ++i) {
            int j;
            for (j = 0; j < sights.size(); ++j) {
                Sight sight = sights.get(j);
                if (checkSightForWorkingTime(timeStart, timeEnd, sight, budget)) {
                    budget = budget.subtract(sight.getCost());
                    recommendedSights.put(Pair.of(timeStart, timeEnd), sight);
                    break;
                }
            }

            if (j == sights.size()) {
                throw new CreateRouteException("Impossible to create a route!");
            }
            sights.remove(j);

            timeStart = timeEnd;
            timeEnd = timeEnd.plusHours(1);
        }

        return recommendedSights;
    }

    private boolean checkSightForWorkingTime(OffsetTime timeStart, OffsetTime timeEnd,
                                             Sight sight, BigDecimal budget) {
        if (sight.getOpeningTime().isAfter(sight.getClosingTime())) {
            return sight.getCost().compareTo(budget) <= 0 &&
                    (checkWorkTimeUntilMidnight(timeStart, timeEnd, sight) ||
                            checkWorkTimeInMidnight(timeStart, timeEnd, sight) ||
                            checkWorkTimeAfterMidnight(timeStart, timeEnd, sight));
        } else {
            return sight.getCost().compareTo(budget) <= 0 &&
                    checkWorkTimeDuringDay(timeStart, timeEnd, sight);
        }
    }

    private boolean checkWorkTimeUntilMidnight(OffsetTime timeStart, OffsetTime timeEnd, Sight sight) {
        return (timeStart.isAfter(sight.getOpeningTime()) || timeStart.equals(sight.getOpeningTime())) &&
                (timeEnd.isBefore(LAST_TIME_IN_DAY) || timeEnd.equals(LAST_TIME_IN_DAY));
    }

    private boolean checkWorkTimeInMidnight(OffsetTime timeStart, OffsetTime timeEnd, Sight sight) {
        return timeStart.isAfter(timeEnd) &&
                timeEnd.isBefore(sight.getClosingTime()) &&
                (timeStart.isAfter(sight.getOpeningTime()) || timeStart.equals(sight.getOpeningTime()));
    }

    private boolean checkWorkTimeAfterMidnight(OffsetTime timeStart, OffsetTime timeEnd, Sight sight) {
        return (timeStart.equals(BEGIN_TIME_IN_DAY) || timeStart.isAfter(BEGIN_TIME_IN_DAY)) &&
                (timeEnd.isBefore(sight.getClosingTime()) || timeEnd.equals(sight.getClosingTime()));
    }

    private boolean checkWorkTimeDuringDay(OffsetTime timeStart, OffsetTime timeEnd, Sight sight) {
        return (sight.getOpeningTime().isBefore(timeStart) || sight.getOpeningTime().equals(timeStart)) &&
                (sight.getClosingTime().isAfter(timeEnd) || sight.getClosingTime().equals(timeEnd)) &&
                timeStart.isBefore(timeEnd);
    }

    private List<Sight> getAllSights(List<Long> categoriesId) {
        return sightService.getAll()
                .stream()
                .filter(sight -> sight.getCategoryId() != null)
                .filter(sight -> categoriesId.contains(sight.getCategoryId()))
                .collect(Collectors.toList());
    }

    private long findCountHours(OffsetTime timeStart, OffsetTime timeEnd) {
        if (timeStart.isAfter(timeEnd)) {
            return 24 + timeStart.until(timeEnd, ChronoUnit.HOURS);
        } else {
            return timeStart.until(timeEnd, ChronoUnit.HOURS);
        }
    }

    private List<Long> findAllCategories(List<Long> categoriesId) {
        List<Category> allCategories = categoryService.getAll();

        for (int i = 0; i < categoriesId.size(); ++i) {
            categoryService.getById(categoriesId.get(i));
            categoriesId.addAll(findChildrenForParentCategory(categoriesId.get(i), allCategories));
        }

        categoriesId = categoriesId.stream()
                .distinct()
                .collect(Collectors.toList());

        return categoriesId;
    }

    private Set<Long> findChildrenForParentCategory(long parentId, List<Category> allCategories) {
        Set<Long> categoriesId = new HashSet<>();
        for (Category category : allCategories) {
            if (category.getParentId() != null && category.getParentId() == parentId) {
                categoriesId.add(category.getId());
            }
        }

        return categoriesId;
    }
}
