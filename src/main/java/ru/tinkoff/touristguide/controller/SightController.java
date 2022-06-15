package ru.tinkoff.touristguide.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.touristguide.dto.SightCreateAndUpdateRequest;
import ru.tinkoff.touristguide.exception.NotValidSightException;
import ru.tinkoff.touristguide.model.Sight;
import ru.tinkoff.touristguide.service.CategoryService;
import ru.tinkoff.touristguide.service.SightService;
import ru.tinkoff.touristguide.util.ErrorUtil;
import ru.tinkoff.touristguide.util.SightUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sights")
@AllArgsConstructor
public class SightController {

    private final SightService sightService;

    private final CategoryService categoryService;

    @PostMapping
    @Operation(
            description = "Add new sight to database",
            summary = "Add new sight"
    )
    @Transactional
    public void createSight(@Valid @RequestBody SightCreateAndUpdateRequest sightCreateAndUpdateRequest, Errors errors) {
        if (errors.hasErrors()) {
            throw new NotValidSightException(ErrorUtil.getExceptionMessage(errors));
        }

        if (sightCreateAndUpdateRequest.getCategoryId() != null) {
            categoryService.getById(sightCreateAndUpdateRequest.getCategoryId());
        }

        Sight sight = SightUtil.createSightFromRequest(sightCreateAndUpdateRequest, 0);
        sightService.save(sight);
    }

    @GetMapping("/id/{id}")
    @Operation(
            description = "Get sight from database by id",
            summary = "Get all sights by id"
    )
    public Sight getSightById(@PathVariable("id") long id) {
        return sightService.getById(id);
    }

    @GetMapping("/category-id/{id}")
    @Operation(
            description = "Get all sights from database by category id",
            summary = "Get all sights by category id"
    )
    public List<Sight> getSightsByCategoryId(@PathVariable("id") long categoryId) {
        categoryService.getById(categoryId);
        return sightService.getAllByCategoryId(categoryId);
    }

    @GetMapping("/all")
    @Operation(
            description = "Get all sights from database",
            summary = "Get all sights"
    )
    public List<Sight> getAllSights() {
        return sightService.getAll();
    }

    @PutMapping("/{id}")
    @Operation(
            description = "Update existing sight in database",
            summary = "Update sight"
    )
    @Transactional
    public void updateSight(@Valid @RequestBody SightCreateAndUpdateRequest sightCreateAndUpdateRequest, Errors errors,
                            @PathVariable("id") long id) {
        if (errors.hasErrors()) {
            throw new NotValidSightException(ErrorUtil.getExceptionMessage(errors));
        }

        sightService.getById(id);
        if (sightCreateAndUpdateRequest.getCategoryId() != null) {
            categoryService.getById(sightCreateAndUpdateRequest.getCategoryId());
        }

        Sight sight = SightUtil.createSightFromRequest(sightCreateAndUpdateRequest, id);
        sightService.update(sight);
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete sight from database",
            summary = "Delete sight"
    )
    public void deleteSight(@PathVariable("id") long id) {
        sightService.delete(id);
    }
}
