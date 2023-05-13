package com.factory.sqldatabase.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.factory.sqldatabase.entities.Evaluate;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Long>{
    Page<Evaluate> findAllByIsDeletedFalse(Pageable pageable);

    Evaluate findByMaintenanceRequestIdAndIsDeletedFalse(Long maintenanceRequestId);
}
