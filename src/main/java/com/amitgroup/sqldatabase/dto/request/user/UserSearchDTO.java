package com.amitgroup.sqldatabase.dto.request.user;

import lombok.Data;

@Data
public class UserSearchDTO {
    private String searchValue;
    private Integer pageIndex = 0;
    private Integer pageSize = 10;
}
