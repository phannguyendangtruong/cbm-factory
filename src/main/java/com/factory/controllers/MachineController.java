package com.factory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.factory.controllers.api.MachineAPI;
import com.factory.models.BaseResponse;
import com.factory.services.machine.MachineService;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/v1")
public class MachineController {
    
    @Autowired
    private MachineService machineService;

    @GetMapping(MachineAPI.API_MACHINE_GET_ALL)
    public BaseResponse getAllMachines() {
        return machineService.getAllMachines();
    }
}
