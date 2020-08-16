package com.product.manager.server.controller;

import com.product.manager.server.converter.CategoryConverter;
import com.product.manager.server.dto.category.CategoryDTO;
import com.product.manager.server.entity.Category;
import com.product.manager.server.service.category.CategoryService;
import com.product.manager.server.dto.category.CreateCategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/product/manager/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @Autowired
    public CategoryController(final CategoryService categoryService, final CategoryConverter categoryConverter) {
        this.categoryService = categoryService;
        this.categoryConverter = categoryConverter;
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public CategoryDTO add(@Validated @RequestBody final CreateCategoryDTO createCategoryDTO) {
        final Category category = categoryConverter.convertToEntity(createCategoryDTO);
        final Category savedCategory = categoryService.add(category);
        return categoryConverter.convertToDto(savedCategory);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public CategoryDTO get(@PathVariable("id") final Long categoryId) {
        final Category category = categoryService.get(categoryId);
        return categoryConverter.convertToDto(category);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<CategoryDTO> getAll() {
        final List<Category> categories = categoryService.getAll();
        return categoryConverter.convertToDto(categories);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public CategoryDTO update(@Validated @RequestBody final CreateCategoryDTO createCategoryDTO,
                              @PathVariable("id") final Long id) {
        final Category category = categoryConverter.convertToEntity(createCategoryDTO);
        final Category updatedCategory = categoryService.update(category, id);
        return categoryConverter.convertToDto(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable("id") final Long id) {
        categoryService.delete(id);
    }
}
