package com.amitgroup.sqldatabase.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.amitgroup.sqldatabase.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResp {
    public Long userId;
    public String userName;
    public String email;
    public String phone;
    public String accessToken;
    public Date accessTokenExpired;
    public String refreshToken;
    public Boolean remember;
    public Boolean isVerify;
    public String role;

    public LoginResp(String accessToken, Date accessTokenExpired, User user, String refreshToken, Boolean remember) {
        this.setUserId(user.getId());
        this.setEmail(user.getEmail());
        this.setAccessToken(accessToken);
        this.setAccessTokenExpired(accessTokenExpired);
        this.setRefreshToken(refreshToken);
        this.setRemember(remember);
//        this.phone = user.getPhone();
//        this.isVerify = user.getIsVerify();
//        this.role = user.getRoleName();
        // this.setPhone(user.getPhone());
        // this.setUserName(user.getUsername());
    }

    public LoginResp(User user, Boolean isNonActive) {
        this.userId = user.getId();
        this.email = user.getEmail();
//        this.phone = user.getPhone();
//        this.role = user.getRoleName();
    }
}
