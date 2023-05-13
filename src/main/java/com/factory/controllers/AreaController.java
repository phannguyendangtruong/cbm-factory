package com.factory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factory.controllers.api.AreaAPI;
import com.factory.models.BaseResponse;
import com.factory.services.area.AreaService;

@RestController
@RequestMapping("/v1")
public class AreaController {
    
    @Autowired
    private AreaService areaService;

    @GetMapping(AreaAPI.GET_ALL_AREA)
    public BaseResponse getAllAreas() {
        return areaService.getAllAreas();
    }
}
