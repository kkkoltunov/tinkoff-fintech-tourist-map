package ru.tinkoff.touristguide.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.touristguide.model.Category;
import ru.tinkoff.touristguide.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/sights/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/id/{id}")
    @Operation(
            description = "Get category from database by id",
            summary = "Get category by id"
    )
    public Category getCategoryById(@PathVariable("id") long id) {
        return categoryService.getById(id);
    }

    @Operation(
            description = "Get category from database by name",
            summary = "Get category by name"
    )
    @GetMapping("/name/{name}")
    public Category getCategoryByName(@PathVariable("name") String name) {
        return categoryService.getByName(name);
    }

    @Operation(
            description = "Get all categories from database",
            summary = "Get all categories"
    )
    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }
}
