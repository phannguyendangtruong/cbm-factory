package com.amitgroup.sqldatabase.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id = 0L;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private long roleId;
    private Boolean isActive = false;
}
