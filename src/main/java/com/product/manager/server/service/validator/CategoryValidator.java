package com.product.manager.server.service.validator;

import com.product.manager.server.entity.Category;

import java.util.List;

public interface CategoryValidator extends Validator<Category> {

    void validate(final List<Category> categories);
}
