package com.amitgroup.sqldatabase.dto.response.user;

import java.util.Date;
import java.util.List;

import com.amitgroup.sqldatabase.dto.MaintenanceRequest;
import com.amitgroup.sqldatabase.entities.MaintenancePerson;

import lombok.Data;

@Data
public class DetailWorkDTO {
    private String userRequestName;
    private String content;
    private String description;
    private String areaName;
    private String machineName;
    private String code;
    private String level;
    private String userMainName;
    private List<String> userSupportName;
    private String status;
    private Date endDate;
    private Long idMaintenanceRequest;
    private Boolean isDone = false;
    private Boolean isConfirm = false;

    public DetailWorkDTO(MaintenancePerson request){
        this.content = request.getMaintenanceRequest().getContent();
        this.description = request.getMaintenanceRequest().getDescription();
        this.areaName = request.getMaintenanceRequest().getArea().getName();
        this.machineName = request.getMaintenanceRequest().getMachine().getName();
        this.code = request.getMaintenanceRequest().getMachine().getCode();
        this.level = request.getMaintenanceRequest().getLevel();
        this.status = request.getMaintenanceRequest().getStatus();
        this.endDate = request.getMaintenanceRequest().getEndDate();
        this.idMaintenanceRequest = request.getMaintenanceRequest().getId();
    }
}
