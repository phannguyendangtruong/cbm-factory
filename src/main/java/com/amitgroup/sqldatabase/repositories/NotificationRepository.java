package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.amitgroup.sqldatabase.entities.Notification;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

    
}
