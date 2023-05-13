package com.factory.sqldatabase.dto.response.evaluate;

import com.factory.sqldatabase.entities.Evaluate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvaluateReponseDTO {
    private Long id;
    private String content;
    private Integer quality;
    private String endDate;;
    private String areaName;
    private Long maintenanceId;

    public EvaluateReponseDTO() {
    }

    public EvaluateReponseDTO(Evaluate evaluate) {
        this.id = evaluate.getId();
        this.content = evaluate.getContent();
        this.quality = evaluate.getQuality();
        this.endDate = evaluate.getEndDate().toString();
        this.areaName = evaluate.getArea().getName();
        this.maintenanceId = evaluate.getMaintenanceRequestId() ;
    }
}
