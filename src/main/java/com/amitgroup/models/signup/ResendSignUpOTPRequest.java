package com.amitgroup.models.signup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendSignUpOTPRequest {
    @Schema(defaultValue = "SignUpStep1SessionId" , description = "request session ID (previous step)")
    private String sessionId;
}
