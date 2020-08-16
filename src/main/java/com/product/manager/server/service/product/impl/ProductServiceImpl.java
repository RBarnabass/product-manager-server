package com.product.manager.server.service.product.impl;

import com.product.manager.server.entity.Category;
import com.product.manager.server.entity.Currency;
import com.product.manager.server.entity.Product;
import com.product.manager.server.exception.DataNotFoundException;
import com.product.manager.server.repository.ProductRepository;
import com.product.manager.server.service.category.CategoryService;
import com.product.manager.server.service.product.ProductService;
import com.product.manager.server.service.currency.CurrencyService;
import com.product.manager.server.service.validator.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.product.manager.server.exception.api.ExceptionCode.PRODUCT_NOT_FOUND;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final CurrencyService currencyService;
    private final CategoryService categoryService;
    private final ProductValidator productValidator;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(final CurrencyService currencyService,
                              final CategoryService categoryService,
                              final ProductValidator productValidator,
                              final ProductRepository productRepository) {
        this.currencyService = currencyService;
        this.categoryService = categoryService;
        this.productValidator = productValidator;
        this.productRepository = productRepository;
    }


    @Transactional
    @Override
    public Product add(final Product product) {
        productValidator.validate(product);
        final List<Category> categories = categoryService.getByName(product.getCategories());
        final Currency currency = currencyService.getByName(product.getCurrency().getName());
        product.setCategories(categories);
        product.setCurrency(currency);
        return productRepository.save(product);
    }

    @Override
    public Product get(final Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("dd", PRODUCT_NOT_FOUND);
        });
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional
    @Override
    public Product update(final Product product, final Long id) {
        if (!productRepository.existsById(id)) {
            throw new DataNotFoundException("Product does not exist.", PRODUCT_NOT_FOUND);
        }
        productValidator.validate(product);
        product.setId(id);
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        if (!productRepository.existsById(id)) {
            throw new DataNotFoundException("Product does not exist.", PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
    }
}
