package com.factory.sqldatabase.dto.request.user;

import lombok.Data;

@Data
public class UserActiveDTO {
    private Long userId;
    private Boolean isActive;
}
