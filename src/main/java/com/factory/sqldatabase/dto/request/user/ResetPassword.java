package com.factory.sqldatabase.dto.request.user;

import lombok.Data;

@Data
public class ResetPassword {
    private String token;
    private String password;
}
