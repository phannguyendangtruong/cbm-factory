package com.factory.services.role;

import org.springframework.stereotype.Service;

import com.factory.models.BaseResponse;
import com.factory.sqldatabase.dto.response.ResponseHandler;

public interface RoleService {
    BaseResponse getAllRole();
}
