package com.amitgroup.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class AuthAPI {
    public static final String AUTH_LOGIN = "/user/auth/login";
    public static final String AUTH_LOGOUT = "/user/auth/logout";
    public static final String AUTH_REGISTER = "/management/create-account";
    public static final String AUTH_FORGOT_PASSWORD = "/user/auth/reset-password";
    public static final String AUTH_CONFIRM = "/user/auth/confirm";

}
