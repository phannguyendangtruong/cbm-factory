package com.amitgroup.models.verification;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseVerificationResponse {

    @Schema(description = "Session ID", defaultValue = "h723154a-3d3e-4573-9aka-2d5b0uv703m0", required = true)
    private String sessionId;

    @Schema(description = "Session expired at (yyyy-MM-dd'T'HH:mm:ss.SSSZ)", defaultValue = "2023-02-17T04:16:30.926+0000")
    private String sessionExpiredAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Otp expired at (yyyy-MM-dd'T'HH:mm:ss.SSSZ)", defaultValue = "2023-02-17T04:04:30.926+0000")
    private String otpExpiredAt;
}
