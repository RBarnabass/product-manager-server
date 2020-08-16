package com.product.manager.server.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ProductManagerException extends RuntimeException {

    private String message;
    private ExceptionCode exceptionCode;
}
