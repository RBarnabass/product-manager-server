package com.product.manager.server.exception;

import com.product.manager.server.exception.api.ExceptionCode;
import com.product.manager.server.exception.api.ProductManagerException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class ConsistencyException extends ProductManagerException {

    public ConsistencyException(final String message, final ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }
}
