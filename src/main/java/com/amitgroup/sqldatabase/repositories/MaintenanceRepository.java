package com.amitgroup.sqldatabase.repositories;

import org.jboss.jandex.Main;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.MaintenanceRequest;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceRequest, Long> {
    // find All by isDeleted = false by id asc
    @Query("SELECT m FROM MaintenanceRequest m WHERE m.isDeleted = false ORDER BY m.id DESC")
    Page<MaintenanceRequest> findAllByIsDeletedFalseOrderByCreatedAtAsc(Pageable pageable);

    Page<MaintenanceRequest> findByIsDeletedFalse(Pageable pageable);

    Page<MaintenanceRequest> findAll(Pageable pageable);

    //find all by created_by 
    @Query("SELECT m FROM MaintenanceRequest m WHERE m.createdBy = ?1 and m.isDeleted = false ORDER BY m.id DESC")
    Page<MaintenanceRequest> findAllByCreatedBy(Long createdBy, Pageable pageable);

    // find by content and isDeleted = false
    Page<MaintenanceRequest> findByIgnoreCaseContentContainingAndIsDeletedFalse(String content, Pageable pageable);

    Page<MaintenanceRequest> findAllByOrderByContent(Pageable pageable);

    Integer countByIsDeletedFalse();

    Integer countByStatusAndIsDeletedFalse(String status);

    // find by id with page
    Page<MaintenanceRequest> findById(Long id, Pageable pageable);

    @Query("SELECT m FROM MaintenanceRequest m WHERE m.status = ?1 and m.isDeleted = false ORDER BY m.id DESC")
    Page<MaintenanceRequest> findByStatusAndIsDeletedFalse(String status, Pageable pageable);

    // search by content maintenance and userId of maintenancePerson
    @Query("SELECT m FROM MaintenancePerson m WHERE m.maintenanceRequest.content LIKE %?1% AND m.userId = ?2")
    Page<MaintenanceRequest> findByContentAndUserId(String content, Long userId, Pageable pageable);
}
