package com.factory.sqldatabase.dto.request.evaluate;

import lombok.Data;

@Data
public class EvaluateReqDTO {
    private Long id;
    private String content;
    private Integer quality;
    private Long maintenanceRequestId;
    private Long areaId;

}
