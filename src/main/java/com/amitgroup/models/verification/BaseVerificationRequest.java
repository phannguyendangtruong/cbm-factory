package com.amitgroup.models.verification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseVerificationRequest {

    @Schema(description = "Session ID", defaultValue = "h723154a-3d3e-4573-9aka-2d5b0uv703m0", required = true)
    private String sessionId;

    @Schema(description = "One time password", example = "999999", required = true)
    private String otp;
}
