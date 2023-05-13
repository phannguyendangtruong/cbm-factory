package com.factory.services.notification;

import com.factory.sqldatabase.dto.response.ResponseHandler;
import com.factory.sqldatabase.dto.response.notification.NotificationDTO;

public interface NotificationService {

    ResponseHandler getAllNotification();
    ResponseHandler getOneNotification(Long id);
    ResponseHandler createNotification(NotificationDTO notificationDTO);
    ResponseHandler updateNotification(NotificationDTO notificationDTO);
    ResponseHandler deleteNotification(Long id);
    
}
