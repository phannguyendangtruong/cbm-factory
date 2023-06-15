package com.amitgroup.services.Impl.notification;

import com.amitgroup.services.notification.NotificationService;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.dto.response.notification.NotificationDTO;
import com.amitgroup.sqldatabase.entities.MaintenanceRequest;
import com.amitgroup.sqldatabase.entities.Notification;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.repositories.MaintenanceRepository;
import com.amitgroup.sqldatabase.repositories.NotificationRepository;
import com.amitgroup.sqldatabase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Override
    public ResponseHandler getAllNotification() {
        return null;
    }

    @Override
    public ResponseHandler getOneNotification(Long id) {
        return null;
    }

    @Override
    public ResponseHandler createNotification(NotificationDTO notificationDTO) {
        ResponseHandler responseHandler = new ResponseHandler();
        Notification notification = new Notification();
        notification.setCreatedDate(new Date());
        User userCreate = userRepository.findById(notificationDTO.getUserCreateId()).get();
        User userReceive = userRepository.findById(notificationDTO.getUserReceiveId()).get();
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(notificationDTO.getMaintenanceRequestId()).get();

        notification.setUserCreate(userCreate);
        notification.setUserReceive(userReceive);
        notification.setMaintenanceRequest(maintenanceRequest);
        notification.setContent(notificationDTO.getContent());
        notificationRepository.save(notification);
        return responseHandler;
    }

    @Override
    public ResponseHandler updateNotification(NotificationDTO notificationDTO) {
        return null;
    }

    @Override
    public ResponseHandler deleteNotification(Long id) {
        return null;
    }
}
