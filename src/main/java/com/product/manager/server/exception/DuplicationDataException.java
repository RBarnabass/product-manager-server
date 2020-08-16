package com.product.manager.server.exception;

import com.product.manager.server.exception.api.ExceptionCode;
import com.product.manager.server.exception.api.ProductManagerException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class DuplicationDataException extends ProductManagerException {

    public DuplicationDataException(final String message, final ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }
}
