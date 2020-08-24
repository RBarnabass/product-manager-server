package com.product.manager.server.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * General converter
 * @param <E> entity
 * @param <D> DTO
 */
public interface Converter<E, D> {

    /**
     * Conver entity to DTO
     * @param e entity
     * @return DTO
     */
    D convertToDto(final E e);

    /**
     * Convert list of entities to list of DTO's
     * @param entities list of entities
     * @return list of DTO's
     */
    default List<D> convertToDto(List<E> entities) {
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
