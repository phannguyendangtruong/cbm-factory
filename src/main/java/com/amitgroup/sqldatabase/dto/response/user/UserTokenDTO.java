package com.amitgroup.sqldatabase.dto.response.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTokenDTO {
    private String token;
    private Date expiredTime;

}
