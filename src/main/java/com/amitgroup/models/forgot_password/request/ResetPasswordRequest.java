package com.amitgroup.models.forgot_password.request;

import com.amitgroup.models.verification.BaseVerificationRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest extends BaseVerificationRequest {

    @Schema(description = "New password", example = "Password@123", required = true)
    private String newPassword;
}
