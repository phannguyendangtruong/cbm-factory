package com.amitgroup.services.notification;

import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.dto.response.notification.NotificationDTO;

public interface NotificationService {

    ResponseHandler getAllNotification();
    ResponseHandler getOneNotification(Long id);
    ResponseHandler createNotification(NotificationDTO notificationDTO);
    ResponseHandler updateNotification(NotificationDTO notificationDTO);
    ResponseHandler deleteNotification(Long id);
    
}
