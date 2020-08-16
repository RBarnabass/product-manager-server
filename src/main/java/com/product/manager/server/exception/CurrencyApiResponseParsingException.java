package com.product.manager.server.exception;

import com.product.manager.server.exception.api.ExceptionCode;
import com.product.manager.server.exception.api.ProductManagerException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ResponseStatus(SERVICE_UNAVAILABLE)
public class CurrencyApiResponseParsingException extends ProductManagerException {

    public CurrencyApiResponseParsingException(final String message, final ExceptionCode exceptionCode) {
        super(message, exceptionCode);
    }
}
