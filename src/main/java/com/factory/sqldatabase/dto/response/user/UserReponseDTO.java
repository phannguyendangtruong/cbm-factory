package com.factory.sqldatabase.dto.response.user;

import com.factory.sqldatabase.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReponseDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private long roleId;
    private String roleName;
    private String roleCode;
    private String avatar;
    private Boolean isActive;
    private String token;

    
}