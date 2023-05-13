package com.factory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factory.controllers.api.RoleAPI;
import com.factory.models.BaseResponse;
import com.factory.services.role.RoleService;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/v1")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @GetMapping(RoleAPI.GET_ALL_ROLE)
    public BaseResponse getAllRole(){
        return roleService.getAllRole();
    }
}
