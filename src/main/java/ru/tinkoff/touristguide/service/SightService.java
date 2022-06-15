package ru.tinkoff.touristguide.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.touristguide.dao.SightCategoryRepository;
import ru.tinkoff.touristguide.dao.SightRepository;
import ru.tinkoff.touristguide.exception.SightNotFoundException;
import ru.tinkoff.touristguide.model.Sight;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SightService {

    private final SightRepository sightRepository;

    private final SightCategoryRepository sightCategoryRepository;

    @Transactional
    public void save(Sight sight) {
        sightRepository.save(sight);

        if (sight.getCategoryId() != null) {
            sightCategoryRepository.save(sight.getId(), sight.getCategoryId());
        }
    }

    private Optional<Sight> findById(long id) {
        return sightRepository.findById(id);
    }

    public Sight getById(long id) {
        return findById(id)
                .orElseThrow(() -> new SightNotFoundException("Sight with id = " + id + " not found!"));
    }

    public List<Sight> getAll() {
        return sightRepository.findAll();
    }

    public List<Sight> getAllByCategoryId(long categoryId) {
        return sightRepository.findAllByCategoryId(categoryId);
    }

    @Transactional
    public void update(Sight sight) {
        sightRepository.update(sight);
        sightCategoryRepository.deleteCategoryBySightId(sight.getId());

        if (sight.getCategoryId() != null) {
            sightCategoryRepository.save(sight.getId(), sight.getCategoryId());
        }
    }

    public void delete(long id) {
        sightRepository.delete(id);
    }
}
