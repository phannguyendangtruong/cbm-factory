package com.amitgroup.models.signin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignInRequest {
    @Schema(description = "email", example = "email", maxLength = 32, required = true)
    private String email;

    @Schema(description = "password", example = "Test@123", minLength = 6, required = true)
    private String password;

}
