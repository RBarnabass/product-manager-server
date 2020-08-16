package com.product.manager.server.converter;

import java.util.List;
import java.util.stream.Collectors;

public interface Converter<E, D> {

    D convertToDto(final E e);

    default List<D> convertToDto(List<E> entities) {
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
