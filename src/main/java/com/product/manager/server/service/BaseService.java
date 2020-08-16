package com.product.manager.server.service;

import java.util.List;

public interface BaseService<Entity, ID> {

    Entity add(final Entity entity);
    Entity get(final Long id);
    List<Entity> getAll();
    Entity update(final Entity entity, final Long id);
    void delete(final Long id);
}
