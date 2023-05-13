package com.amitgroup.services.Impl.area;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.area.AreaService;
import com.amitgroup.sqldatabase.dto.request.area.AreaDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.entities.Area;
import com.amitgroup.sqldatabase.repositories.AreaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse getAllAreas() {
        log.info("Getting all areas");
        BaseResponse responseHandler = new BaseResponse();
        List<AreaDTO> areaDTOs = new ArrayList<>();
        areaRepository.findAll().forEach(area -> {
            areaDTOs.add(modelMapper.map(area, AreaDTO.class));
        });
        responseHandler.setData(areaDTOs);

        return responseHandler;
    }

    @Override
    public BaseResponse getAreaById(Long id) {
        log.info("Getting area by id: {}", id);
        BaseResponse responseHandler = new BaseResponse();
        responseHandler.setData(modelMapper.map(areaRepository.findById(id).get(), AreaDTO.class));
        return responseHandler;
    }

    @Override
    public BaseResponse createArea(AreaDTO areaDTO) {
        log.info("Creating area: {}", areaDTO);
        BaseResponse responseHandler = new BaseResponse();
        responseHandler.setData(modelMapper.map(areaRepository.save(modelMapper.map(areaDTO, Area.class)), AreaDTO.class));
        return responseHandler;
    }

    @Override
    public BaseResponse updateArea(AreaDTO areaDTO) {
        log.info("Updating area: {}", areaDTO);
        BaseResponse responseHandler = new BaseResponse();
        responseHandler.setData(createArea(areaDTO));
        return responseHandler;
    }

    
}
