package com.amitgroup.sqldatabase.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordReq {
//    @NotEmpty(message = "Mã xác thực không được để trống")
//    public String code;
//    @NotEmpty(message = "Mật khẩu không được để trống")
//    @Pattern(regexp = "(?=^.{8,50}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)(?=.*[!@#$%^&*.()_+])[0-9a-zA-Z!@#$%^&*.()_+]*$"
//            , message = "Mật khẩu yêu cầu tối thiểu 8 kí tự và không vượt quá 50 kí tự, bao gồm ít nhất 1 kí tự thường, " +
//            "một kí tự hoa, một kí tự số và một kí tự đặc biệt")
    public String password;
}
