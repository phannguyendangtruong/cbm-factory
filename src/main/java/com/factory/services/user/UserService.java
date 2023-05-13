package com.factory.services.user;


import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.factory.models.BasePaginationResponse;
import com.factory.models.BaseResponse;
import com.factory.sqldatabase.dto.request.PageDTO;
import com.factory.sqldatabase.dto.request.user.UserActiveDTO;
import com.factory.sqldatabase.dto.request.user.UserDTO;
import com.factory.sqldatabase.dto.request.user.UserPasswordDTO;
import com.factory.sqldatabase.dto.request.user.UserSearchDTO;
import com.factory.sqldatabase.dto.request.user.UserUpdateDTO;

@Service
public interface UserService {

    BaseResponse findByEmail(String email);
    BaseResponse createAccount(UserDTO user, HttpServletRequest request, Errors validate);
    BaseResponse updateAccount(UserDTO user, Errors validate);
    BaseResponse updateAccountByUser(UserUpdateDTO user, Errors validate, HttpServletRequest request);
    BaseResponse deleteAccount(Long id);
    BaseResponse getOneUser(Long id);
    BasePaginationResponse getListWork(PageDTO page,HttpServletRequest reqeust);
    BasePaginationResponse getAll(Pageable pageable);
    BaseResponse changePassword(UserPasswordDTO userPasswordDTO, HttpServletRequest request ,Errors validate);
    BaseResponse getProfile(HttpServletRequest request);
    BaseResponse changeActive(UserActiveDTO userActiveDTO);
    BasePaginationResponse searchUser(UserSearchDTO user);
    BaseResponse detailWork(Long maintenanceRequestId, HttpServletRequest request);
    BaseResponse getAllUser();
    BasePaginationResponse getListWorker(PageDTO page);
    BaseResponse dashboardWorker(HttpServletRequest request);
    BaseResponse getEvaluateByWorker(HttpServletRequest request);
}
