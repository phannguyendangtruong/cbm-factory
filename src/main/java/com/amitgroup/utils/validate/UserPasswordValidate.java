package com.amitgroup.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.amitgroup.sqldatabase.dto.request.user.UserPasswordDTO;

@Component
public class UserPasswordValidate implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return UserPasswordDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserPasswordDTO userPasswordDTO = (UserPasswordDTO) target;
        if(userPasswordDTO.getOldPassword().isEmpty()) {
            errors.rejectValue("oldPassword", "oldPassword.empty", "Mật khẩu cũ không được để trống");
        }
        if(userPasswordDTO.getNewPassword().isEmpty()) {
            errors.rejectValue("newPassword", "newPassword.empty", "Mật khẩu mới không được để trống");
        }
        if(userPasswordDTO.getNewPassword().length() < 8 || userPasswordDTO.getNewPassword().length() > 16) {
            errors.rejectValue("newPassword", "newPassword.length", "Mật khẩu mới phải từ 8 đến 16 ký tự");
        }
      
    }
    
}
