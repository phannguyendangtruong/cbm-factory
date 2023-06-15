package com.amitgroup.models.signup;

import com.amitgroup.models.BaseResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpStep1Response extends BaseResponse {
    private UserSignUpStep1Response.ResponseData data;

    @Data
    public static class ResponseData{

        @Schema(defaultValue = "SignUpStep1SessionId" , description = "request session ID")
        private String sessionId;

        @Schema(defaultValue = "2023-02-17T04:16:30.926+0000" , description = "Expired at (yyyy-MM-dd'T'HH:mm:ss.SSSZ)")
        private String sessionExpiredAt;
    }
}