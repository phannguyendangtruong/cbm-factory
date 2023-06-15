package com.amitgroup.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.amitgroup.sqldatabase.dto.request.user.UserDTO;

@Component
public class UserDtoValidate implements Validator{

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDto = (UserDTO) target;
        if(userDto.getId() == null) {
            errors.rejectValue("id", "id.empty", "Id không tồn tại");
        }
        if (userDto.getUsername() == null||userDto.getUsername().isEmpty()) {
            errors.rejectValue("username", "username.empty", "Username is not empty");
        }
        if (userDto.getFullName() == null || userDto.getFullName().isEmpty()) {
            errors.rejectValue("fullName", "fullName.empty", "Fullname is not empty");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            errors.rejectValue("email", "email.empty", "Email is not empty");
        }
        if (!userDto.getEmail().matches(EMAIL_PATTERN)) {
            errors.rejectValue("email", "email.invalid", "Email invalid");
        }
        if (userDto.getPassword()==null ||userDto.getPassword().isEmpty()) {
            errors.rejectValue("password", "password.empty", "Password  is not empty");
        }
        if (userDto.getPassword() == null || userDto.getPassword().length() < 8 || userDto.getPassword().length() > 16) {
            errors.rejectValue("password", "password.length", "Password must be between 8 and 16 characters");
        }
        if (userDto.getPhone() == null || userDto.getPhone().isEmpty()) {
            errors.rejectValue("phone", "phone.empty", "Phone number is not empty");
        if (userDto.getPhone().length() < 10 || userDto.getPhone().length() > 11) {
            errors.rejectValue("phone", "phone.length", "Phone number must be between 10 and 11 characters");
        }
        if (userDto.getRoleId() == 0) {
            errors.rejectValue("roleId", "roleId.empty", "Role id is not empty");
        }
    }

}
}

