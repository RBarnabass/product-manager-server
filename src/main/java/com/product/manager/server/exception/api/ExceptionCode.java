package com.product.manager.server.exception.api;

/**
 * Exception codes provides possibility for internationalisation
 */
public enum ExceptionCode {

    CATEGORY_NOT_FOUND(1001),
    PRODUCT_NOT_FOUND(1002),
    CURRENCY_NOT_FOUND(1003),
    DUPLICATE_VALUE(1004),
    CURRENCY_UPDATE_FAILURE(1005),
    CATEGORY_CAN_NOT_BE_REMOVED(1006),
    VALIDATION_FAILED(1007),
    AUTHORIZATION_FAILED(1008),
    JSON_IS_MALFORMED(1009),
    NO_EXCEPTION_HANDLER(1010);

    private final int exceptionCode;

    ExceptionCode(final int code) {
        this.exceptionCode = code;
    }

    public int getCode() {
        return exceptionCode;
    }
}
