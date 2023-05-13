package com.amitgroup.sqldatabase.dto.request.user;

import lombok.Data;

@Data
public class UserPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
