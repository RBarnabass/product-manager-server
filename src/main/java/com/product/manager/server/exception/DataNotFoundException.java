package com.product.manager.server.exception;

import com.product.manager.server.exception.api.ExceptionCode;
import com.product.manager.server.exception.api.ProductManagerException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class DataNotFoundException extends ProductManagerException {

    public DataNotFoundException(final String message, final ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }
}
