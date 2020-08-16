package com.product.manager.server.exception;

import com.product.manager.server.exception.api.ExceptionCode;
import com.product.manager.server.exception.api.ProductManagerException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ResponseStatus(SERVICE_UNAVAILABLE)
public class CurrencyApiException extends ProductManagerException {

    public CurrencyApiException(final String message, final ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }
}
