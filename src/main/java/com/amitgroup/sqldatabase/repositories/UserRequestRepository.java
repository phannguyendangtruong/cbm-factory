package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.MaintenanceRequest;

@Repository
public interface UserRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
}
