package com.factory.sqldatabase.dto.response.user;

import lombok.Data;

@Data
public class DashboardWorker {
    private Integer totalNotYetRated;
    private Integer totalIsMaintained;
    private Integer totalMaintained;
    private Integer totalIsRated;
}
