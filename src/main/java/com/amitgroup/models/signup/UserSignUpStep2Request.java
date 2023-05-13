package com.amitgroup.models.signup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpStep2Request extends ResendSignUpOTPRequest{
    @Schema(defaultValue = "999999" , description = "One time password")
    private String otp;
}
