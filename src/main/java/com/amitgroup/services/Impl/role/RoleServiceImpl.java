package com.amitgroup.services.Impl.role;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.role.RoleService;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.dto.response.role.RoleDTO;
import com.amitgroup.sqldatabase.repositories.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse getAllRole() {
        log.info("Getting all roles");
        BaseResponse resp = new BaseResponse();
        List<RoleDTO> roleDTO = new ArrayList<>();
        roleRepository.findAll().forEach(role -> {
            roleDTO.add(modelMapper.map(role, RoleDTO.class));
        });
        resp.setData(roleDTO);
        return resp;
    }
    
}
