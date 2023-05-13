package com.factory.security;


import javax.servlet.http.HttpServletRequest;

public interface IAuthenticationFacade {
    CustomUserDetail getAuthentication();
    AMGUserDetail getUserDetail();
    Long getUserId();
    String getUsername();
    HttpServletRequest getHttpServletRequest();
}
