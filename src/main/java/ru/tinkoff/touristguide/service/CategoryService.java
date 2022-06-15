package ru.tinkoff.touristguide.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.touristguide.dao.CategoryRepository;
import ru.tinkoff.touristguide.exception.CategoryNotFoundException;
import ru.tinkoff.touristguide.model.Category;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    private Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category getById(long id) {
        return findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id = " + id + " not found!"));
    }

    public Category getByName(String name) {
        return findByName(name.toLowerCase(Locale.ROOT))
                .orElseThrow(() -> new CategoryNotFoundException("Category with name = " + name + " not found!"));
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
