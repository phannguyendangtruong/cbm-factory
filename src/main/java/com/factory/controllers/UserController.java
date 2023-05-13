package com.factory.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.factory.controllers.api.*;
import com.factory.models.BasePaginationResponse;
import com.factory.models.BaseResponse;
import com.factory.services.user.UserService;
import com.factory.sqldatabase.dto.request.PageDTO;
import com.factory.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;
import com.factory.sqldatabase.dto.request.user.UserDTO;
import com.factory.sqldatabase.dto.request.user.UserPasswordDTO;
import com.factory.sqldatabase.dto.request.user.UserUpdateDTO;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(UserAPI.DELETE_USER)
    public BaseResponse deleteUser(@RequestBody UserDTO user) {
        return userService.deleteAccount(user.getId());
    }

    @GetMapping(UserAPI.USER_PROFILE)
    public BaseResponse getUserProfile(HttpServletRequest request) {
        return userService.getProfile(request);
    }

    @GetMapping(AdminAPI.GET_USER_BY_ID)
    public BaseResponse getUserById(@RequestParam Long userId) {
        return userService.getOneUser(userId);
    }

    @PostMapping(UserAPI.GET_LIST_WORK)
    public BasePaginationResponse getListWork(@RequestBody PageDTO page ,HttpServletRequest request) {
        return userService.getListWork(page, request);
    }

    @PostMapping(UserAPI.CHANGE_PASSWORD)
    public BaseResponse changePassword(@RequestBody UserPasswordDTO userPasswordDTO, Errors validate,
            HttpServletRequest request) {
        return userService.changePassword(userPasswordDTO, request, validate);
    }

    @PostMapping(UserAPI.DETAIL_WORK)
    public BaseResponse detailWork(@RequestBody MaintenancePersonDTO dto, HttpServletRequest request) {
        return userService.detailWork(dto.getIdMaintenanceRequest(), request);
    }

    @PostMapping(UserAPI.UPDATE_USER)
    public BaseResponse updateUser(@RequestBody UserUpdateDTO userDTO, Errors validate, HttpServletRequest request) {
        return userService.updateAccountByUser(userDTO, validate, request);
    }

    @GetMapping("/all")
    public BaseResponse getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping(UserAPI.GET_LIST_WORKER)
    public BaseResponse getListWorker(HttpServletRequest request) {
        return userService.dashboardWorker(request);
    }

    @GetMapping(UserAPI.GET_EVALUATE_BY_WORKER)
    public BaseResponse getEvaluateByWorker(HttpServletRequest request) {
        return userService.getEvaluateByWorker(request);
    }

}
