package com.amitgroup.sqldatabase.dto.request.maintenance;

import lombok.Data;

@Data
public class SearchMaintenance {
    private String searchValue;
    private Integer pageIndex = 0;
    private Integer pageSize = 10;
}
