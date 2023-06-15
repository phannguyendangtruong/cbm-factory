package com.amitgroup.sqldatabase.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.Evaluate;
import com.amitgroup.sqldatabase.entities.MaintenancePerson;

@Repository
public interface MaintenancePersonRepository extends JpaRepository<MaintenancePerson, Long>{
    
    void deleteByMaintenanceRequestId(Long id);

    List<MaintenancePerson> findAllByUserId(Long id);
    
    @Query("SELECT m FROM MaintenancePerson m WHERE m.userId = ?1 ORDER BY m.maintenanceRequestId DESC")
    Page<MaintenancePerson> findAllByUserId(Long id, Pageable pageable);

    List<MaintenancePerson> findByMaintenanceRequestId(Long id);
    
    MaintenancePerson findByMaintenanceRequestIdAndUserId(Long maintenanceRequestId, Long userId);

    //count maintenance request by user id and status
    @Query("SELECT COUNT(m) FROM MaintenancePerson m WHERE m.userId = ?1 AND m.maintenanceRequest.status = ?2")
    Integer countByUserIdAndStatus(Long id, String status);

    //search by content maintenance and userId of maintenancePerson
    @Query("SELECT m FROM MaintenancePerson m WHERE m.maintenanceRequest.content LIKE %?1% AND m.userId = ?2")
    Page<MaintenancePerson> findByContentAndUserId(String content, Long userId, Pageable pageable);

}
