package com.amitgroup.sqldatabase.dto.response.maintenance;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import com.amitgroup.sqldatabase.dto.response.evaluate.EvaluateReponseDTO;
import com.amitgroup.sqldatabase.entities.MaintenanceRequest;
import lombok.Data;

@Data
public class MaintenanceReponseDTO {
    private Long id;
    private String content;
    private String status;
    private String level;
    private String description;
    private Long areaId;
    private String areaName;
    private String machineName;
    private OffsetDateTime createdDate;
    private Date endDate;
    private String mainChineCode;
    private Long machineId;
    private Boolean isEvaluated;
    private EvaluateReponseDTO evaluate;
    private List<ListPerson> listPerson;
    
    public MaintenanceReponseDTO(MaintenanceRequest maintenanceRequest) {
        this.id = maintenanceRequest.getId();
        this.content = maintenanceRequest.getContent();
        this.status = maintenanceRequest.getStatus();
        this.level = maintenanceRequest.getLevel();
        this.description = maintenanceRequest.getDescription();
        this.areaId = maintenanceRequest.getAreaId();
        this.machineId = maintenanceRequest.getMachineId();
        this.isEvaluated = maintenanceRequest.getIsEvaluated();
        this.createdDate = maintenanceRequest.getCreatedAt();
        this.areaName = maintenanceRequest.getArea().getName();
        this.machineName = maintenanceRequest.getMachine().getName();
        this.mainChineCode = maintenanceRequest.getMachine().getCode();
        this.endDate = maintenanceRequest.getEndDate();
    }
}
