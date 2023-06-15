package com.amitgroup.sqldatabase.dto.response.maintenance;

import lombok.Data;

@Data
public class MaintenanceReportDTO {
    private Integer totalRequest;
    private Integer totalNotYetRated;
    private Integer totalNotAssigned;
    private Integer totalIsMaintained;
    private Integer totalMaintained;
    private Integer totalAssigned;
    private Integer totalYetRated;
}
