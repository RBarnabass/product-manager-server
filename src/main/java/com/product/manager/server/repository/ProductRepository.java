package com.product.manager.server.repository;

import com.product.manager.server.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ApplicationRepository<Product, Long> {

}
