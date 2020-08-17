package com.product.manager.server.service.category;

import com.product.manager.server.entity.Category;
import com.product.manager.server.service.BaseService;

import java.util.List;

/**
 * Category service for base and specific operations
 */
public interface CategoryService extends BaseService<Category, Long> {

    /**
     * Retrieves stored categories by name
     * @param categories raw categories
     * @return stored categories
     */
    List<Category> getByName(final List<Category> categories);
}
