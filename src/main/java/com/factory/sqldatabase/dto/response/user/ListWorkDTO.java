package com.factory.sqldatabase.dto.response.user;

import java.time.OffsetDateTime;

import com.factory.sqldatabase.entities.MaintenanceRequest;

import lombok.Data;

@Data
public class ListWorkDTO {
    private Long id;
    private String content;
    private String description;
    private String status;
    private OffsetDateTime createdDate;
    private Boolean confirm;
    private String level;
    private String areaName;
    
    public ListWorkDTO(MaintenanceRequest maintenanceRequest){
        this.id = maintenanceRequest.getId();
        this.content = maintenanceRequest.getContent();
        this.description = maintenanceRequest.getDescription();
        this.status = maintenanceRequest.getStatus();
        this.createdDate = maintenanceRequest.getCreatedAt();
        this.level = maintenanceRequest.getLevel();
        this.areaName = maintenanceRequest.getArea().getName();
    }
}
