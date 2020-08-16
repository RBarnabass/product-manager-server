package com.product.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ApplicationRepository<Entity, ID> extends JpaRepository<Entity, ID> {

}
