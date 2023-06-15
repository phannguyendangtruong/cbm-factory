package com.amitgroup.sqldatabase.dto.request.maintenance;


import java.util.Date;

import lombok.Data;

@Data
public class MaintenanceRequestDTO {
    private Long id;
    private String content;
    private String status = "Chưa giao việc";
    private String level;
    private String description;
    private Long areaId;
    private Long machineId;
    private Date endDate;
    
}
