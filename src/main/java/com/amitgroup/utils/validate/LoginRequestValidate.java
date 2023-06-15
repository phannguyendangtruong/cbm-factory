package com.amitgroup.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.amitgroup.sqldatabase.dto.request.auth.LoginReq;

@Component
public class LoginRequestValidate implements Validator{
    
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginReq.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginReq loginReq = (LoginReq) target;
        // error null
        if (loginReq.getEmail() == null||loginReq.getEmail().isEmpty() ) {
            errors.rejectValue("email", "email.empty", "Email or username is not empty");
        }

        if (loginReq.getPassword() == null||loginReq.getPassword().isEmpty()) {
            errors.rejectValue("password", "password.empty", "Password is not empty");
        }
        if ((loginReq.getPassword().isEmpty())==false &&loginReq.getPassword().length() < 8 || loginReq.getPassword().length() > 16) {
            errors.rejectValue("password", "password.length", "Password must be between 8 and 16 characters");
        }
    }
    
}
