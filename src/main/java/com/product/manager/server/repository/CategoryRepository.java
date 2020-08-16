package com.product.manager.server.repository;

import com.product.manager.server.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends ApplicationRepository<Category, Long> {

    boolean existsByNameIn(final List<String> names);
    List<Category> findByNameIn(final List<String> names);
}
