package com.amitgroup.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.amitgroup.controllers.api.AdminAPI;
import com.amitgroup.models.BasePaginationResponse;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.user.UserService;
import com.amitgroup.sqldatabase.dto.request.PageDTO;
import com.amitgroup.sqldatabase.dto.request.user.UserActiveDTO;
import com.amitgroup.sqldatabase.dto.request.user.UserDTO;
import com.amitgroup.sqldatabase.dto.request.user.UserSearchDTO;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/v1")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping(AdminAPI.GET_ALL_USER)
    public BasePaginationResponse getAllUser(@RequestBody PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize());
        return userService.getAll(pageable);
    }

    @PostMapping(AdminAPI.CREATE_USER)
    public BaseResponse register(@RequestBody @Validated UserDTO req, HttpServletRequest request, Errors validate) {
        BaseResponse response = userService.createAccount(req, request, validate);
        if (response.getMessageCode().equalsIgnoreCase("Failed")) {
            response = new BaseResponse("Invalid param");
            return response;
        }
        return response;
    }

    @PostMapping(AdminAPI.UPDATE_USER)
    public BaseResponse updateUser(@RequestBody UserDTO user, Errors validate) {
        BaseResponse resp = userService.updateAccount(user, validate);
        return resp;
    }

    @PostMapping(AdminAPI.CHANGE_ACTIVE)
    public BaseResponse changeActive(@RequestBody UserActiveDTO user) {
        BaseResponse resp = new BaseResponse(userService.changeActive(user));
        return resp;
    }

    @PostMapping(AdminAPI.SEARCH_USER)
    public BaseResponse searchUser(@RequestBody UserSearchDTO userSearchDTO) {
        BaseResponse resp = new BaseResponse(userService.searchUser(userSearchDTO));
        return resp;
    }

    @PostMapping(AdminAPI.WORKER_LIST)
    public BasePaginationResponse workerList(@RequestBody PageDTO page) {
        return userService.getListWorker(page);
    }  
}
