package com.factory.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.factory.sqldatabase.dto.request.user.UserUpdateDTO;

@Component
public class UserUpdateValidate implements Validator{



    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateDTO userUpdateDTO = (UserUpdateDTO) target;
        if(userUpdateDTO.getFullName().isEmpty()) {
            errors.rejectValue("fullName", "fullName.empty", "Họ tên không được để trống");
        }
        if(userUpdateDTO.getPhone().isEmpty()) {
            errors.rejectValue("phone", "phone.empty", "Số điện thoại không được để trống");
        }
        
    }
    
}
