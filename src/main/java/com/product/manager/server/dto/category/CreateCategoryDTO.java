package com.product.manager.server.dto.category;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static com.product.manager.server.constant.DigitConstant.MAX_CATEGORY_NAME_LENGTH;
import static com.product.manager.server.constant.DigitConstant.MIN_CATEGORY_NAME_LENGTH;
import static com.product.manager.server.constant.ValidationMessage.CATEGORY_NAME_CAN_NOT_BE_BLANK;
import static com.product.manager.server.constant.ValidationMessage.CATEGORY_NAME_LENGTH_SHOULD_BE_IN_SCOPES;

@Data
public class CreateCategoryDTO {

    @NotBlank(message = CATEGORY_NAME_CAN_NOT_BE_BLANK)
    @Length(min = MIN_CATEGORY_NAME_LENGTH, max = MAX_CATEGORY_NAME_LENGTH,
            message = CATEGORY_NAME_LENGTH_SHOULD_BE_IN_SCOPES)
    private String name;
}
