package com.amitgroup.services.Impl.machine;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.machine.MachineService;
import com.amitgroup.sqldatabase.dto.request.machine.MachineDTO;
import com.amitgroup.sqldatabase.entities.Machine;
import com.amitgroup.sqldatabase.repositories.MachineRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MachineServiceImpl implements MachineService{

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse getAllMachines() {
        log.info("Getting all machines");
        try{
            BaseResponse responseHandler = new BaseResponse();
            List<MachineDTO> machineDTOs = new ArrayList<>();
            machineRepository.findAll().forEach(machine -> {
                machineDTOs.add(modelMapper.map(machine, MachineDTO.class));
            });
            responseHandler.setData(machineDTOs);
            log.info("All machines: {}", machineDTOs);
            return responseHandler;
        }catch(Exception e){
            log.info("Getting all machines error");
            return new BaseResponse(e.getMessage());
        }
    }

    @Override
    public BaseResponse getMachineById(Long id) {
        log.info("Getting machine by id: {}", id);
        try{
            BaseResponse responseHandler = new BaseResponse();
            responseHandler.setData(machineRepository.findById(id).get());
            return responseHandler;
        }catch(Exception e){
            log.info("Getting machine by id error");
            return new BaseResponse(e.getMessage());
        }
    }

    @Override
    public BaseResponse createMachine(MachineDTO machineDTO) {
        log.info("Creating machine: {}", machineDTO);
        try{
            BaseResponse responseHandler = new BaseResponse();
            responseHandler.setData(machineRepository.save(modelMapper.map(machineDTO, Machine.class)));
            return responseHandler;
        }catch(Exception e){
            log.info("Creating machine error");
            return new BaseResponse(e.getMessage());
        }
    }

    @Override
    public BaseResponse updateMachine(MachineDTO machineDTO) {
        log.info("Updating machine: {}", machineDTO);
        try{
            BaseResponse responseHandler = new BaseResponse();
            responseHandler.setData(createMachine(machineDTO));
            return responseHandler;
        }catch(Exception e){
            log.info("Updating machine error");
            return new BaseResponse(e.getMessage());
        }
    }

    

    
}
