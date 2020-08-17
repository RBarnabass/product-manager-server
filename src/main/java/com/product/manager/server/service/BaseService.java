package com.product.manager.server.service;

import java.util.List;

/**
 * Base service interface for providing CRUD operations
 * @param <Entity> model
 * @param <ID> primary key
 */
public interface BaseService<Entity, ID> {

    /**
     * Create new model
     * @param entity model
     * @return created model with primary key
     */
    Entity add(final Entity entity);

    /**
     * Retrieve model by primary key
     * @param id specified primary key
     * @return stored model
     */
    Entity get(final ID id);

    /**
     * Retrieve all models
     * @return list of all models
     */
    List<Entity> getAll();

    /**
     * Update existed model by specified primary key
     * @param entity modified model
     * @param id primary key
     * @return updated model
     */
    Entity update(final Entity entity, final ID id);

    /**
     * Remove stored model
     * @param id primary key
     */
    void delete(final ID id);
}
