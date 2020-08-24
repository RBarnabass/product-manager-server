package com.product.manager.server.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static com.product.manager.server.constant.DigitConstant.*;
import static com.product.manager.server.constant.ValidationMessage.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = USERNAME_CAN_NOT_BE_BLANK)
    @Length(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH, message = USERNAME_LENGTH_SHOULD_BE_IN_SCOPE)
    private String username;

    @NotBlank(message = PASSWORD_CAN_NOT_BE_BLANK)
    @Length(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = PASSWORD_LENGTH_SHOULD_BE_IN_SCOPE)
    private String password;
}
