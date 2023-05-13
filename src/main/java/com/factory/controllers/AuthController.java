package com.factory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.factory.controllers.api.AuthAPI;
import com.factory.models.ApiException;
import com.factory.models.BaseResponse;
import com.factory.services.auth.AuthService;
import com.factory.services.user.UserService;
import com.factory.sqldatabase.dto.request.auth.LoginReq;
import com.factory.sqldatabase.dto.request.user.ResetPassword;
import com.factory.sqldatabase.dto.request.user.UserDTO;
import com.factory.sqldatabase.dto.response.ResponseHandler;
import com.factory.utils.validate.LoginRequestValidate;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(AuthAPI.AUTH_LOGIN)
    public BaseResponse login(@RequestBody LoginReq req, HttpServletRequest request, Errors validate,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String errorMessage = "Invalid param: " + fieldError.getField();
            BaseResponse errorResponse = new BaseResponse(errorMessage);
            return errorResponse;
        }
        BaseResponse response = authService.login(req, request, validate);
        if (response.getMessage() != null &&response.getMessage().equalsIgnoreCase("failed")) {
            return response;
        }
        return response;
    }

    @PostMapping(AuthAPI.AUTH_FORGOT_PASSWORD)
    public BaseResponse forgotPassword(@RequestBody UserDTO req) {
        return authService.forgotPassword(req);
    }

    @PostMapping(AuthAPI.AUTH_CONFIRM)
    public BaseResponse confirmResetPassword(@RequestBody ResetPassword req) {
        return authService.confirmPassword(req.getToken(), req.getPassword());
    }

    @PostMapping(AuthAPI.AUTH_LOGOUT)
    public BaseResponse logOut(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        return authService.logOut(token, request);
    }

}
