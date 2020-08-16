package com.product.manager.server.service.category.impl;

import com.product.manager.server.entity.Category;
import com.product.manager.server.exception.ConsistencyException;
import com.product.manager.server.exception.DataNotFoundException;
import com.product.manager.server.repository.CategoryRepository;
import com.product.manager.server.service.category.CategoryService;
import com.product.manager.server.service.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.product.manager.server.exception.api.ExceptionCode.CATEGORY_CAN_NOT_BE_REMOVED;
import static com.product.manager.server.exception.api.ExceptionCode.CATEGORY_NOT_FOUND;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryValidator categoryValidator;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(final CategoryValidator categoryValidator, final CategoryRepository categoryRepository) {
        this.categoryValidator = categoryValidator;
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    @Override
    public Category add(final Category category) {
        categoryValidator.validate(category);
        return categoryRepository.save(category);
    }

    @Override
    public Category get(final Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("Category does not exist.", CATEGORY_NOT_FOUND);
        });
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public Category update(final Category category, final Long id) {
        categoryValidator.validate(category);
        if (!categoryRepository.existsById(id)) {
            throw new DataNotFoundException("Category does not exist.", CATEGORY_NOT_FOUND);
        }
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        final Category category = get(id);
        if (category.getProducts().isEmpty()) {
            categoryRepository.deleteById(id);
        } else {
            throw new ConsistencyException("Category is in usage.", CATEGORY_CAN_NOT_BE_REMOVED);
        }
    }

    @Override
    public List<Category> getByName(final List<Category> categories) {
        final List<String> categoryNames = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        return categoryRepository.findByNameIn(categoryNames);
    }
}
