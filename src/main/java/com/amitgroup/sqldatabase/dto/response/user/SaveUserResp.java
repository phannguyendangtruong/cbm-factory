package com.amitgroup.sqldatabase.dto.response.user;

import lombok.Data;

@Data
public class SaveUserResp {
    public Long userId;
    public String email;

    public SaveUserResp(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
