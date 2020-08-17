package com.product.manager.server.service.validator;

/**
 * General validator interface
 * @param <E> model to validate
 */
public interface Validator<E> {

    /**
     * Case model is invalid should throw an exception
     * @param e model to validate
     */
    void validate(final E e);
}
