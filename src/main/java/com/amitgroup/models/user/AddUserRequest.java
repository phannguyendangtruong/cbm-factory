package com.amitgroup.models.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequest {
    @Schema(description = "email", example = "user@mail.com", required = false)
    private String email;

    @Schema(description = "strong password", example = "!@#!@D!@F123fc123x312", required = false)
    private String password;

    @Schema(description = "new first name", example = "Bui", maxLength = 32, required = false)
    private String firstName;

    @Schema(description = "new last name", example = "Thanh Phuong", maxLength = 32, required = false)
    private String lastName;

    @Schema(description = "new user activation status", example = "true", maxLength = 32, required = false)
    private Boolean isActivated;

}
