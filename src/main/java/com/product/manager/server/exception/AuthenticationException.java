package com.product.manager.server.exception;

import com.product.manager.server.exception.api.ExceptionCode;
import com.product.manager.server.exception.api.ProductManagerException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ResponseStatus(FORBIDDEN)
public class AuthenticationException extends ProductManagerException {

    public AuthenticationException(final String message, final ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }
}
