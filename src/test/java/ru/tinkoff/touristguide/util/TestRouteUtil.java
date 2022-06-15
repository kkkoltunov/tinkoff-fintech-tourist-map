package ru.tinkoff.touristguide.util;

import org.springframework.data.util.Pair;
import ru.tinkoff.touristguide.dto.RouteRequest;
import ru.tinkoff.touristguide.exception.ExceptionMessages;

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

public class TestRouteUtil {

        public static final RouteRequest ROUTE_REQUEST_1 = new RouteRequest()
                .setCategoriesId(new ArrayList<>(List.of(1L, 2L)))
                .setBudget(new BigDecimal(1000))
                .setVisitTime(Pair.of(OffsetTime.parse("19:00:00+00:00"), OffsetTime.parse("20:00:00+00:00")));

        public static final RouteRequest ROUTE_REQUEST_2 = new RouteRequest()
                .setCategoriesId(new ArrayList<>(List.of(1L)))
                .setBudget(new BigDecimal(1000))
                .setVisitTime(Pair.of(OffsetTime.parse("18:00:00+00:00"), OffsetTime.parse("21:00:00+00:00")));

        public static final RouteRequest ROUTE_REQUEST_3 = new RouteRequest()
                .setCategoriesId(new ArrayList<>(List.of(333L)))
                .setBudget(new BigDecimal(1000))
                .setVisitTime(Pair.of(OffsetTime.parse("18:00:00+00:00"), OffsetTime.parse("21:00:00+00:00")));

        public static final RouteRequest ROUTE_REQUEST_4 = new RouteRequest()
                .setCategoriesId(new ArrayList<>(List.of(1L)))
                .setBudget(new BigDecimal(1000))
                .setVisitTime(Pair.of(OffsetTime.parse("10:00:00+00:00"), OffsetTime.parse("21:00:00+00:00")));

        public static final RouteRequest ROUTE_REQUEST_5 = new RouteRequest()
                .setCategoriesId(new ArrayList<>(List.of(1L)))
                .setBudget(new BigDecimal(1000))
                .setVisitTime(Pair.of(OffsetTime.parse("23:00:00+00:00"), OffsetTime.parse("02:00:00+00:00")));

        public static final String QUERY_FOR_CREATE_SIGHTS_1 = """
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('new-sight', 'new-description', 'new-sight.com', 1.00, '18:00:00'::time,
            '19:00:00'::time, 1.123456, 1.123456),
            ('new-sight', 'new-description', 'new-sight.com', 1.00, '19:00:00'::time,
            '20:00:00'::time, 1.123456, 1.123456),
            ('new-sight', 'new-description', 'new-sight.com', 1.00, '20:00:00'::time,
            '21:00:00'::time, 1.123456, 1.123456);""";

        public static final String QUERY_FOR_CREATE_SIGHTS_2 = """
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('new-sight', 'new-description', 'new-sight.com', 1.00, '23:00:00'::time,
            '00:00:00'::time, 1.123456, 1.123456),
            ('new-sight', 'new-description', 'new-sight.com', 1.00, '00:00:00'::time,
            '01:00:00'::time, 1.123456, 1.123456),
            ('new-sight', 'new-description', 'new-sight.com', 1.00, '01:00:00'::time,
            '02:00:00'::time, 1.123456, 1.123456);""";

        public static final String QUERY_FOR_CREATE_SIGHT = """
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('name', 'description', 'website', 1.00, '18:00:00'::time, '21:00:00'::time, 1.123456, 1.123456);""";

        public static final String QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR =
                "INSERT INTO sights_categories (sight_id, category_id) VALUES (1, 1)";

        public static final String QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIRS = """
                INSERT INTO sights_categories (sight_id, category_id)
                VALUES (1, 1),
                (2, 1),
                (3, 1);""";

        public static final ExceptionMessages EXCEPTION_MESSAGE_CREATE_ROUTE =
                new ExceptionMessages(new ArrayList<>(List.of(
                "Impossible to create a route!"
        )));
}
