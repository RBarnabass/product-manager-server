package com.product.manager.server.constant;

import static com.product.manager.server.constant.DigitConstant.*;
import static com.product.manager.server.constant.DigitConstant.MAX_CATEGORY_NAME_LENGTH;

public class ValidationMessage {

    public static final String CATEGORIES_CAN_NOT_BE_NULL = "Categories can not be null";
    public static final String CATEGORY_NAME_CAN_NOT_BE_BLANK = "Category name can not be null or empty.";
    public static final String CATEGORY_NAME_LENGTH_SHOULD_BE_IN_SCOPES = "Category name should be between " +
            MIN_CATEGORY_NAME_LENGTH + " and " + MAX_CATEGORY_NAME_LENGTH + ".";

    public static final String PRODUCT_NAME_CAN_NOT_BE_BLANK = "Product name can not be null or empty.";
    public static final String PRODUCT_NAME_LENGTH_SHOULD_BE_IN_SCOPES = "Product name should be between " +
            MIN_PRODUCT_NAME_LENGTH + " and " + MAX_PRODUCT_NAME_LENGTH + ".";

    public static final String PRODUCT_MODEL_CAN_NOT_BE_BLANK = "Product model can not be null or empty.";
    public static final String PRODUCT_MODEL_LENGTH_SHOULD_BE_IN_SCOPES = "Product model should be between " +
            MIN_PRODUCT_NAME_LENGTH + " and " + MAX_PRODUCT_NAME_LENGTH + ".";

    public static final String PRODUCT_QUANTITY_CAN_NOT_BE_NULL = "Product quantity can not be null.";
    public static final String PRODUCT_QUANTITY_SHOULD_BE_POSITIVE_OR_ZERO = "Product quantity should be positive or zero.";

    public static final String PRODUCT_PRICE_CAN_NOT_BE_NULL = "Product price can not be null.";
    public static final String PRODUCT_PRICE_SHOULD_BE_POSITIVE = "Product price should be positive.";

    public static final String CURRENCY_NAME_CAN_NOT_BE_BLANK = "Currency name can not be null or empty.";
    public static final String CURRENCY_NAME_LENGTH_SHOULD_BE_IN_SCOPE = "Currency name length should be " +
            MAX_CURRENCY_NAME_LENGTH + " chars.";

    public static final String USERNAME_CAN_NOT_BE_BLANK = "Username can not be null or empty.";
    public static final String USERNAME_LENGTH_SHOULD_BE_IN_SCOPE = "Username length should be " +
            MIN_USERNAME_LENGTH + " and " + MAX_USERNAME_LENGTH + ".";

    public static final String PASSWORD_CAN_NOT_BE_BLANK = "Password can not be null or empty.";
    public static final String PASSWORD_LENGTH_SHOULD_BE_IN_SCOPE = "Password length should be " +
            MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + ".";
}
