package com.product.manager.server.service.category;

import com.product.manager.server.entity.Category;
import com.product.manager.server.service.BaseService;

import java.util.List;

public interface CategoryService extends BaseService<Category, Long> {

    List<Category> getByName(final List<Category> categories);
}
