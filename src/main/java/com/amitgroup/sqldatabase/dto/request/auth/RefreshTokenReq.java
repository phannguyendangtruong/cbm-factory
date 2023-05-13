package com.amitgroup.sqldatabase.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenReq {
//    @NotEmpty(message = "Missing refresh token")
    private String refreshToken;
}
