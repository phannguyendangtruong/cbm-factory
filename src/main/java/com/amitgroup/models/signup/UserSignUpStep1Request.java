package com.amitgroup.models.signup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpStep1Request {
    @Schema(description = "email", example = "email", maxLength = 32, required = true)
    private String email;

    @Schema(description = "password", example = "Test@123", minLength = 6, required = true)
    private String password;

    @Schema(description = "first name", example = "Bui", minLength = 32, required = true)
    private String firstName;

    @Schema(description = "last name", example = "Thanh Phuong", minLength = 32, required = true)
    private String lastName;
}