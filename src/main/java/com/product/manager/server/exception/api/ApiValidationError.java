package com.product.manager.server.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
class ApiValidationError extends ApiSubError {

    private String object;
    private String field;
    private Object rejectedMessage;
    private String message;

    ApiValidationError(final String object, final String message) {

        this.object = object;
        this.message = message;
    }
}
