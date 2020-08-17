package com.product.manager.server.service.validator;

import com.product.manager.server.entity.Category;

import java.util.List;

/**
 * Category validator for validation specific
 */
public interface CategoryValidator extends Validator<Category> {

    /**
     * Validate all categories as existence models
     * @param categories list of categories already persisted
     */
    void validate(final List<Category> categories);
}
