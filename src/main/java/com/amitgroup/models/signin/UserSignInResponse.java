package com.amitgroup.models.signin;

import com.amitgroup.models.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignInResponse extends BaseResponse {
    private ResponseData data;

    @Data
    public static class ResponseData{
        @Schema(defaultValue = "xxxxxxxxxxxxxxxxxxxxxx" , description = "Access Token")
        private String accessToken;
        @Schema(defaultValue = "2023-02-17T04:30:30.926+0000" , description = "Expired at (yyyy-MM-dd'T'HH:mm:ss.SSSZ)")
        private String accessTokenExpiredAt;
    }
}
