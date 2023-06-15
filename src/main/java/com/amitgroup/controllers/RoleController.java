package com.amitgroup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgroup.controllers.api.RoleAPI;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.role.RoleService;

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
