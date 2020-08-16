package com.product.manager.server.service.validator.impl;

import com.product.manager.server.entity.Category;
import com.product.manager.server.exception.DataNotFoundException;
import com.product.manager.server.exception.DuplicationDataException;
import com.product.manager.server.exception.api.ExceptionCode;
import com.product.manager.server.repository.CategoryRepository;
import com.product.manager.server.service.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.product.manager.server.exception.api.ExceptionCode.CATEGORY_NOT_FOUND;
import static com.product.manager.server.exception.api.ExceptionCode.DUPLICATE_VALUE;

@Slf4j
@Service
public class CategoryValidatorImpl implements CategoryValidator {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryValidatorImpl(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void validate(final Category category) {
        if (categoryRepository.exists(Example.of(category))) {
            throw new DuplicationDataException("Category already exist.", DUPLICATE_VALUE);
        }
    }

    @Override
    public void validate(final List<Category> categories) {
        final List<String> categoriesNames = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        if (!categoryRepository.existsByNameIn(categoriesNames)) {
            throw new DataNotFoundException("Category does not exist.", CATEGORY_NOT_FOUND);
        }
    }
}
