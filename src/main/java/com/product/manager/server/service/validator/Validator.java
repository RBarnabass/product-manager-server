package com.product.manager.server.service.validator;

public interface Validator<E> {

    void validate(final E e);
}
