package com.product.manager.server.dto.product;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

import static com.product.manager.server.constant.DigitConstant.*;
import static com.product.manager.server.constant.ValidationMessage.*;

@Data
public class CreateProductDTO {

    @NotBlank(message = PRODUCT_NAME_CAN_NOT_BE_BLANK)
    @Length(min = MIN_PRODUCT_NAME_LENGTH, max = MAX_PRODUCT_NAME_LENGTH,
            message = PRODUCT_NAME_LENGTH_SHOULD_BE_IN_SCOPES)
    private String name;

    @NotBlank(message = PRODUCT_MODEL_CAN_NOT_BE_BLANK)
    @Length(min = MIX_PRODUCT_MODEL_LENGTH, max = MAX_PRODUCT_MODEL_LENGTH,
            message = PRODUCT_MODEL_LENGTH_SHOULD_BE_IN_SCOPES)
    private String model;

    @NotNull(message = PRODUCT_QUANTITY_CAN_NOT_BE_NULL)
    @PositiveOrZero(message = PRODUCT_QUANTITY_SHOULD_BE_POSITIVE_OR_ZERO)
    private Integer quantity;

    @NotNull(message = PRODUCT_PRICE_CAN_NOT_BE_NULL)
    @Positive(message = PRODUCT_PRICE_SHOULD_BE_POSITIVE)
    private BigDecimal price;

    @NotNull(message = CATEGORIES_CAN_NOT_BE_NULL)
    private List<String> categories;

    @NotBlank(message = CURRENCY_NAME_CAN_NOT_BE_BLANK)
    @Length(min = MAX_CURRENCY_NAME_LENGTH, max = MAX_CURRENCY_NAME_LENGTH,
            message = CURRENCY_NAME_LENGTH_SHOULD_BE_IN_SCOPE)
    private String currencyName;
}
