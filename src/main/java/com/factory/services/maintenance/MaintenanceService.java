package com.factory.services.maintenance;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;

import com.factory.models.BasePaginationResponse;
import com.factory.models.BaseResponse;
import com.factory.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;

public interface MaintenanceService {
    
    BasePaginationResponse getAllMaintenance(Pageable pageable);
    BaseResponse getOneMaintenance(Long id);
    BaseResponse createMaintenance(MaintenanceRequestDTO maintenanceRequestDTO, HttpServletRequest request, Errors errors);
    BaseResponse updateMaintenance(MaintenanceRequestDTO maintenanceRequestDTO, HttpServletRequest request, Errors errors);
    BaseResponse deleteMaintenance(Long id);
    BaseResponse changeStatusMaintenance(Long id, String status);
    BasePaginationResponse searchMaintenance(String search, Pageable pageable);
    BaseResponse sortByContent(Pageable pageable);
    BaseResponse requiredStatistics();
    BaseResponse maintenanceLevel();
    BaseResponse maintenanceStatus();
    BasePaginationResponse getListMaintenanceByStatus(Pageable pageable);
    BasePaginationResponse searchMaintenanceByWorker(String search, Pageable pageable, HttpServletRequest request);

}
