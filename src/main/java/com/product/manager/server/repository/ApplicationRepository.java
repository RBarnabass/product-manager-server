package com.product.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Abstraction repository for apply restrictions for all repositories
 * @param <Entity> entity to store
 * @param <ID> primary key
 */
@NoRepositoryBean
public interface ApplicationRepository<Entity, ID> extends JpaRepository<Entity, ID> {

}
