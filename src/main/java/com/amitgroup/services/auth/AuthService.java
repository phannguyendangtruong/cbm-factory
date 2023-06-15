package com.amitgroup.services.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.amitgroup.models.BaseResponse;
import com.amitgroup.sqldatabase.dto.request.auth.LoginReq;
import com.amitgroup.sqldatabase.dto.request.user.UserDTO;

public interface AuthService {
    
    BaseResponse confirmPassword(String token, String password);
    BaseResponse login(LoginReq loginRequest, HttpServletRequest request,  Errors validate);
    BaseResponse forgotPassword(UserDTO user);
    BaseResponse logOut(String token, HttpServletRequest request);
}
