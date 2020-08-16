package com.product.manager.server.service.validator.impl;

import com.product.manager.server.entity.Product;
import com.product.manager.server.service.validator.CategoryValidator;
import com.product.manager.server.service.validator.CurrencyValidator;
import com.product.manager.server.service.validator.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductValidatorImpl implements ProductValidator {

    private final CurrencyValidator currencyValidator;
    private final CategoryValidator categoryValidator;

    @Autowired
    public ProductValidatorImpl(final CurrencyValidator currencyValidator, final CategoryValidator categoryValidator) {
        this.currencyValidator = currencyValidator;
        this.categoryValidator = categoryValidator;
    }


    @Override
    public void validate(final Product product) {
        currencyValidator.validate(product.getCurrency());
        categoryValidator.validate(product.getCategories());
    }
}
