package com.amitgroup.sqldatabase.dto.response.notification;

import java.util.Date;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private String content;
    private boolean isViewed;
    private Date createdDate;
    private Long maintenanceRequestId;
    private Long userReceiveId;
    private Long userCreateId;
}
