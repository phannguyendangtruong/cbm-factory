package com.factory.services.maintenance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.factory.models.BaseResponse;
import com.factory.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;
import com.factory.sqldatabase.dto.response.ResponseHandler;

public interface MaintenancePersonService {
    BaseResponse createMaintenancePerson(MaintenancePersonDTO user, Errors validate);
    BaseResponse updateMaintenancePerson(MaintenancePersonDTO user, Errors validate);
    BaseResponse confirmWork(Long maintenanceId, HttpServletRequest request);
    BaseResponse setDoneWork(Long maintenanceId, HttpServletRequest request);
}
