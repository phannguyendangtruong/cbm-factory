package com.amitgroup.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.amitgroup.controllers.api.MaintenanceAPI;
import com.amitgroup.controllers.api.ReportAPI;
import com.amitgroup.models.BasePaginationResponse;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.maintenance.MaintenancePersonService;
import com.amitgroup.services.maintenance.MaintenanceService;
import com.amitgroup.sqldatabase.dto.request.PageDTO;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;
import com.amitgroup.sqldatabase.dto.request.maintenance.SearchMaintenance;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/v1")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private MaintenancePersonService maintenancePersonService;

    @PostMapping(MaintenanceAPI.CREATE_MAINTENANCE)
    public BaseResponse createMaintenance(@RequestBody MaintenanceRequestDTO maintenanceRequestDTO,
            HttpServletRequest request, Errors validate) {
        return maintenanceService.createMaintenance(maintenanceRequestDTO, request, validate);
    }

    @PostMapping(MaintenanceAPI.UPDATE_MAINTENANCE)
    public BaseResponse updateMaintenance(@RequestBody MaintenanceRequestDTO maintenanceRequestDTO,
    HttpServletRequest request, Errors validate) {
        return maintenanceService.updateMaintenance(maintenanceRequestDTO, request, validate);
    }

    @PostMapping(MaintenanceAPI.CREATE_MAINTENANCE_PERSON)
    public BaseResponse updateMaintenancePerson(@RequestBody MaintenancePersonDTO maintenancePersonDTO, Errors validate) {
        return maintenancePersonService.createMaintenancePerson(maintenancePersonDTO, validate);
    }

    @PostMapping(MaintenanceAPI.UPDATE_MAINTENANCE_PERSON)
    public BaseResponse createMaintenancePerson(@RequestBody MaintenancePersonDTO maintenancePersonDTO, Errors validate) {
        return maintenancePersonService.updateMaintenancePerson(maintenancePersonDTO, validate);
    }

    @PostMapping(MaintenanceAPI.CHANGE_STATUS_MAINTENANCE)
    public BaseResponse changeStatusMaintenance(@RequestBody MaintenanceRequestDTO maintenanceRequestDTO) {
        return maintenanceService.changeStatusMaintenance(maintenanceRequestDTO.getId(), maintenanceRequestDTO.getStatus());
    }

    @PostMapping(MaintenanceAPI.DELETE_MAINTENANCE)
    public BaseResponse deleteMaintenance(@RequestBody MaintenanceRequestDTO maintenanceRequestDTO) {
        return maintenanceService.deleteMaintenance(maintenanceRequestDTO.getId());
    }

    @PostMapping(MaintenanceAPI.GET_ALL_MAINTENANCE)
    public BasePaginationResponse getAllMaintenance(@RequestBody SearchMaintenance maintenance) {
        Pageable pageable = PageRequest.of(maintenance.getPageIndex(), maintenance.getPageSize());
        return maintenanceService.getAllMaintenance(pageable);
    }

    @GetMapping(MaintenanceAPI.GET_MAINTENANCE_BY_ID)
    public BaseResponse getMaintenanceById(@RequestParam Long id) {
        return maintenanceService.getOneMaintenance(id);
    }

    @PostMapping(MaintenanceAPI.SEARCH_MAINTENANCE)
    public BasePaginationResponse searchMaintenance(@RequestBody SearchMaintenance search) {
        Pageable pageable = PageRequest.of(search.getPageIndex(), search.getPageSize());
        return maintenanceService.searchMaintenance(search.getSearchValue(), pageable);
    }

    @GetMapping(MaintenanceAPI.SORT_BY_CONTENT)
    public BaseResponse sortByContent(@RequestParam(defaultValue = "0") Long page,
            @RequestParam(defaultValue = "10") Long size) {
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        return maintenanceService.sortByContent(pageable);
    }
    
    @PostMapping(MaintenanceAPI.CONFIRM_WORK)
    public BaseResponse confirmWork(@RequestBody MaintenancePersonDTO maintenance, HttpServletRequest request) {
        return maintenancePersonService.confirmWork(maintenance.getIdMaintenanceRequest(), request);
    }

    @PostMapping(MaintenanceAPI.SET_DONE_WORK)
    public BaseResponse setDoneWork(@RequestBody MaintenancePersonDTO maintenance, HttpServletRequest request) {
        return maintenancePersonService.setDoneWork(maintenance.getIdMaintenanceRequest(), request);
    }

    @GetMapping(ReportAPI.GET_REPORT)
    public BaseResponse getReport() {
        return maintenanceService.requiredStatistics();
    }

    @GetMapping(MaintenanceAPI.MAINTENANCE_LEVEL)
    public BaseResponse getMaintenanceLevel() {
        return maintenanceService.maintenanceLevel();
    }

    @GetMapping(MaintenanceAPI.MAINTENANCE_STATUS)
    public BaseResponse getMaintenanceStatus() {
        return maintenanceService.maintenanceStatus();
    }

    @PostMapping(MaintenanceAPI.GET_MAINTENANCE_BY_STATUS)
    public BasePaginationResponse getListMaintenanceByStatus(@RequestBody PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize());
        return maintenanceService.getListMaintenanceByStatus(pageable);
    }

    @PostMapping(MaintenanceAPI.SEARCH_MAINTENANCE_BY_WORKER)
    public BasePaginationResponse searchMaintenanceByWorker(@RequestBody SearchMaintenance search, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(search.getPageIndex(), search.getPageSize());
        return maintenanceService.searchMaintenanceByWorker(search.getSearchValue(), pageable, request);
    }
}

