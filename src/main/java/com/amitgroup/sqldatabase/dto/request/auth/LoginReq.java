package com.amitgroup.sqldatabase.dto.request.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class LoginReq {
   @NotEmpty(message = "Email không được để trống")
   @NotBlank(message = "Email không được để trống")
   @Email(message = "Email không hợp lệ")
    public String email;
   @NotEmpty(message = "Password không được để trống")
    public String password;
    public Boolean remember = false;

}

