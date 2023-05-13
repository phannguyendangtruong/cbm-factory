package com.factory.services.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.factory.models.BaseResponse;
import com.factory.sqldatabase.dto.request.auth.LoginReq;
import com.factory.sqldatabase.dto.request.user.UserDTO;

public interface AuthService {
    
    BaseResponse confirmPassword(String token, String password);
    BaseResponse login(LoginReq loginRequest, HttpServletRequest request,  Errors validate);
    BaseResponse forgotPassword(UserDTO user);
    BaseResponse logOut(String token, HttpServletRequest request);
}
