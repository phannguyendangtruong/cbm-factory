package com.amitgroup.sqldatabase.dto.request;

import lombok.Data;

@Data
public class PageDTO {
    private int pageIndex = 0;
    private int pageSize = 10;
}
