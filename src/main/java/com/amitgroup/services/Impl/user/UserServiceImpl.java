package com.amitgroup.services.Impl.user;


import com.amitgroup.domains.TokenStore;
import com.amitgroup.models.BasePaginationResponse;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.user.UserService;
import com.amitgroup.sqldatabase.dto.request.PageDTO;
import com.amitgroup.sqldatabase.dto.request.user.UserActiveDTO;
import com.amitgroup.sqldatabase.dto.request.user.UserDTO;
import com.amitgroup.sqldatabase.dto.request.user.UserPasswordDTO;
import com.amitgroup.sqldatabase.dto.request.user.UserSearchDTO;
import com.amitgroup.sqldatabase.dto.request.user.UserUpdateDTO;
import com.amitgroup.sqldatabase.dto.response.evaluate.EvaluateReponseDTO;
import com.amitgroup.sqldatabase.dto.response.user.DashboardWorker;
import com.amitgroup.sqldatabase.dto.response.user.DetailWorkDTO;
import com.amitgroup.sqldatabase.dto.response.user.ListWorkDTO;
import com.amitgroup.sqldatabase.dto.response.user.UserReponseDTO;
import com.amitgroup.sqldatabase.entities.Evaluate;
import com.amitgroup.sqldatabase.entities.MaintenancePerson;
import com.amitgroup.sqldatabase.entities.MaintenanceRequest;
import com.amitgroup.sqldatabase.entities.Role;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.repositories.EvaluateRepository;
import com.amitgroup.sqldatabase.repositories.MaintenancePersonRepository;
import com.amitgroup.sqldatabase.repositories.MaintenanceRepository;
import com.amitgroup.sqldatabase.repositories.RoleRepository;
import com.amitgroup.sqldatabase.repositories.UserRepository;
import com.amitgroup.utils.RestUtils;
import com.amitgroup.utils.validate.UserDtoValidate;
import com.amitgroup.utils.validate.UserPasswordValidate;
import com.amitgroup.utils.validate.UserUpdateByAdminValidate;
import com.amitgroup.utils.validate.UserUpdateValidate;
import com.fasterxml.jackson.databind.JsonSerializable.Base;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserDtoValidate userDtoValidate;
    @Autowired
    private UserPasswordValidate userPasswordValidate;
    @Autowired
    private TokenStore  tokenStore;
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private MaintenancePersonRepository maintenancePersonRepository;
    @Autowired
    private EvaluateRepository evaluateRepository;
    @Autowired
    private UserUpdateValidate userUpdateValidate;
    @Autowired UserUpdateByAdminValidate userUpdateAdminValidate;

    @Override
    public BaseResponse findByEmail(String email) {
        return null;
    }


    @Override
    public BaseResponse createAccount(UserDTO user, HttpServletRequest request, Errors validate) {
        log.info("Create account");
        BaseResponse resp = new BaseResponse();
        userDtoValidate.validate(user, validate);
        if (validate.hasErrors()) {
            log.info("Validate error {}" + validate.getAllErrors().get(0).getDefaultMessage());
            resp = new BaseResponse(validate.getAllErrors().get(0).getDefaultMessage());
            return resp;
        }
        User userEntity = modelMapper.map(user, User.class);
        String token = RestUtils.getTokenFromHeader(request);
        Long userId = tokenStore.getSessionFromToken(token).get().getCurrentUserId();
        User userAdmin = userRepository.findById(userId).orElse(null);
        if (userAdmin == null) {
            log.info("User not found {}" + userId);
            resp = new BaseResponse("Invalid token");
            return resp;    
        }

        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Long id = userRepository.save(userEntity).getId();
        user.setId(id);
        resp.setData(user);
        log.info("Create account success");
        return resp;

    }

    @Override
    public BaseResponse updateAccount(UserDTO user, Errors validate) {
        log.info("Update account {}"+user);
        userUpdateAdminValidate.validate(user, validate);
        if (validate.hasErrors()) {
            log.info("Validate error {}" + validate.getAllErrors().get(0).getDefaultMessage());
            BaseResponse resp = new BaseResponse(validate.getAllErrors().get(0).getDefaultMessage().toString());
            return resp;
        }
        BaseResponse resp = new BaseResponse();
        User userAccount = userRepository.findById(user.getId()).orElse(null);
        User userEntity = modelMapper.map(user, User.class);
        userEntity.setPassword(userAccount.getPassword());
        userRepository.save(userEntity);
        resp.setData(user);
        log.info("Update account success");
        return resp;
    }

    @Override
    public BaseResponse deleteAccount(Long id) {
        log.info("Delete account");
        BaseResponse resp = new BaseResponse();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.info("User not found {}"+id);
            resp = new BaseResponse("User not found");
            return resp;
        }
        user.setIsActive(false);
        userRepository.save(user);
        resp.setData(user);
        log.info("Delete account success");
        return resp;
    }


    @Override
    public BaseResponse getOneUser(Long id) {
        log.info("Get one user");
        BaseResponse resp = new BaseResponse();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.info("User not found {}"+id);
            resp = new BaseResponse("User not found");
            return resp;
        }
        UserReponseDTO userReponseDTO = modelMapper.map(user, UserReponseDTO.class);
        resp.setData(userReponseDTO);
        log.info("Get one user success");
        return resp;
    }


    @Override
    public BasePaginationResponse getListWork(PageDTO page, HttpServletRequest request) {
        log.info("Get list work by user id");
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize());
        List<ListWorkDTO> list = new ArrayList<>();
        BasePaginationResponse resp = new BasePaginationResponse();
        Long userId = tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getCurrentUserId();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.info("User not found {}"+userId);
            resp = new BasePaginationResponse("User not found");
            return resp;
        }
        Page<MaintenancePerson> maintenancePerson = maintenancePersonRepository.findAllByUserId(userId, pageable);
        for (MaintenancePerson maintenancePerson2 : maintenancePerson) {
            MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(maintenancePerson2.getMaintenanceRequestId()).get();
            ListWorkDTO listWorkDTO = new ListWorkDTO(maintenanceRequest);
            list.add(listWorkDTO);
        }
        resp = new BasePaginationResponse(list, pageable.getPageNumber(), pageable.getPageSize(), maintenancePerson.getTotalElements());
        return resp;

    }

    @Override
    public BasePaginationResponse getAll(Pageable pageable) {
        log.info("Get all user");
        Page<UserReponseDTO> list = userRepository.findAllByFullNameDesc(pageable).map(user -> modelMapper.map(user, UserReponseDTO.class));
        BasePaginationResponse resp = new BasePaginationResponse(list.getContent(), pageable.getPageNumber(), pageable.getPageSize(), list.getTotalElements());
        return resp;
    }


    @Override
    public BaseResponse changePassword(UserPasswordDTO userPasswordDTO, HttpServletRequest request ,Errors validate) {
        log.info("Change password");
        userPasswordValidate.validate(userPasswordDTO, validate);
        if (validate.hasErrors()) {
            log.info("Validate error {}" + validate.getAllErrors().get(0).getDefaultMessage());
            BaseResponse resp = new BaseResponse(validate.getAllErrors().get(0).getDefaultMessage().toString());
            return resp;
        }
        BaseResponse resp = new BaseResponse();
        Long id = tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getCurrentUserId();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.info("User not found");
            resp = new BaseResponse("User not found");
            resp.setMessage("User not found {}"+id);
            return resp;
        }


        if(!(bCryptPasswordEncoder.matches(userPasswordDTO.getOldPassword(), user.getPassword()))){
            log.info("Old password is not correct");
            resp = new BaseResponse("Passwold old is not correct");
            resp.setMessage("Old password is not correct");
            return resp;
        }
        user.setPassword(bCryptPasswordEncoder.encode(userPasswordDTO.getNewPassword()));
        UserReponseDTO userReponseDTO = modelMapper.map(userRepository.save(user), UserReponseDTO.class);
        resp.setData(userReponseDTO);
        log.info("Change password success");
        return resp;
    }


    @Override
    public BaseResponse getProfile(HttpServletRequest request) {
        log.info("Get profile");
        BaseResponse resp = new BaseResponse();
        Long userId = tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getCurrentUserId();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.info("User not found {}"+userId);
            resp = new BaseResponse("User not found");
            return resp;
        }
        Role role = user.getRole();
        UserReponseDTO userReponseDTO = modelMapper.map(user, UserReponseDTO.class);
        userReponseDTO.setRoleName(role.getName());
        userReponseDTO.setRoleCode(role.getCode());

        resp.setData(userReponseDTO);
        return resp;
    }


    @Override
    public BaseResponse changeActive(UserActiveDTO userActiveDTO) {
        log.info("Change active");
        BaseResponse resp = new BaseResponse();
        User user = userRepository.findById(userActiveDTO.getUserId()).orElse(null);
        if (user == null) {
            log.info("User not found {}"+userActiveDTO.getUserId());
            resp = new BaseResponse("User not found");
            return resp;
        }
        user.setIsActive(userActiveDTO.getIsActive());
        userRepository.save(user);
        UserReponseDTO userDTO = modelMapper.map(user, UserReponseDTO.class);
        resp.setData(userDTO);
        return resp;
    }


    @Override
    public BasePaginationResponse searchUser(UserSearchDTO user) {
        log.info("Search user");
        Pageable pageable = PageRequest.of(user.getPageIndex(), user.getPageSize());
        Page<User> userList = userRepository.findByFullNameContainingIgnoreCase(user.getSearchValue(),pageable);
        List<UserReponseDTO> list = new ArrayList<>();
        for (User user2 : userList) {
            UserReponseDTO userDTO = modelMapper.map(user2, UserReponseDTO.class);
            list.add(userDTO);
        }
        BasePaginationResponse resp = new BasePaginationResponse<>();
        resp = new BasePaginationResponse(list, pageable.getPageNumber(), pageable.getPageSize(), userList.getTotalElements());
        return resp;
    }


    @Override
    public BaseResponse detailWork(Long maintenanceRequestId, HttpServletRequest request) {
        log.info("Get detail work");
        List<MaintenancePerson> listPersorn = maintenancePersonRepository.findByMaintenanceRequestId(maintenanceRequestId);
        List<String> userSupport = new ArrayList<>();
        String userCreateName = userRepository.findById(maintenanceRepository.findById(maintenanceRequestId).get().getCreatedBy()).get().getFullName();
        String userMainName ="";
        for (int i = 0; i < listPersorn.size(); i++) {
            if(listPersorn.get(i).getIsMainPerson()){
                userMainName = listPersorn.get(i).getUser().getFullName();
            }else{
                userSupport.add(listPersorn.get(i).getUser().getFullName());
            }
        }
        try{
            MaintenancePerson maintenancePerson = maintenancePersonRepository.findByMaintenanceRequestIdAndUserId(maintenanceRequestId, tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getCurrentUserId());
            DetailWorkDTO detail = new DetailWorkDTO(listPersorn.get(0));
            detail.setUserRequestName(userCreateName);
            detail.setUserMainName(userMainName);
            detail.setUserSupportName(userSupport);
            detail.setIsConfirm(maintenancePerson.getConfirm());
            detail.setIsDone(maintenancePerson.getIsDone());
            BaseResponse resp = new BaseResponse(detail);
            log.info("Get detail work success {}"+detail);
            return resp;
        }catch(Exception e){
            return null;
        }
        
    }


    @Override
    public BaseResponse updateAccountByUser(UserUpdateDTO user, Errors validate, HttpServletRequest request) {
        BaseResponse resp = new BaseResponse();
        userUpdateValidate.validate(user, validate);
        if (validate.hasErrors()) {
            log.info("Validate error {}" + validate.getAllErrors().get(0).getDefaultMessage());
            resp = new BaseResponse(validate.getAllErrors().get(0).getDefaultMessage().toString());
            return resp;
        }
        User userUpdate = userRepository.findById(tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getCurrentUserId()).orElse(null);
        if (userUpdate == null) {
            log.info("User not found");
            resp = new BaseResponse("User not found");
            return resp;
        }
        userUpdate.setInfo(user);
        UserReponseDTO userReponseDTO = modelMapper.map(userRepository.save(userUpdate), UserReponseDTO.class);
        resp.setData(userReponseDTO);
        return resp;
    }


    @Override
    public BaseResponse getAllUser() {
        BaseResponse resp = new BaseResponse();
        List<User> listUser = userRepository.findAll();
        List<UserReponseDTO> listUserDTO = new ArrayList<>();
        for (User user : listUser) {
            UserReponseDTO userDTO = modelMapper.map(user, UserReponseDTO.class);
            listUserDTO.add(userDTO);
        }
        resp.setData(listUserDTO);
        return resp;
    }


    @Override
    public BasePaginationResponse getListWorker(PageDTO page) {
        log.info("Get list worker");
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize());
        Page<User> userList = userRepository.findByRoleWorker(pageable);
        List<UserReponseDTO> list = new ArrayList<>();
        for (User user : userList) {
            UserReponseDTO userDTO = modelMapper.map(user, UserReponseDTO.class);
            list.add(userDTO);
        }
        BasePaginationResponse resp = new BasePaginationResponse<>();
        resp = new BasePaginationResponse(list, pageable.getPageNumber(), pageable.getPageSize(), userList.getTotalElements());
        return resp;
    }


    @Override
    public BaseResponse dashboardWorker(HttpServletRequest request) {
        log.info("Get dashboard worker");
        BaseResponse resp = new BaseResponse();
        Long userId = tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getCurrentUserId();
        DashboardWorker dashboard = new DashboardWorker();
        dashboard.setTotalNotYetRated(maintenancePersonRepository.countByUserIdAndStatus(userId, "Chưa đánh giá"));
        dashboard.setTotalIsRated(maintenancePersonRepository.countByUserIdAndStatus(userId, "Đã đánh giá"));
        dashboard.setTotalIsMaintained(maintenancePersonRepository.countByUserIdAndStatus(userId, "Đang bảo trì"));
        dashboard.setTotalMaintained(maintenancePersonRepository.countByUserIdAndStatus(userId, "Đã bảo trì"));
        resp.setData(dashboard);
        return resp;
    }


    @Override
    public BaseResponse getEvaluateByWorker(HttpServletRequest request) {
        log.info("Get evaluate by worker");
        BaseResponse resp = new BaseResponse();
        Long userId = tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getCurrentUserId();
        List<MaintenancePerson> list = maintenancePersonRepository.findAllByUserId(userId);
        List<EvaluateReponseDTO> listEvaluate = new ArrayList<>();
        for (MaintenancePerson e : list) {
            Evaluate eva = evaluateRepository.findByMaintenanceRequestIdAndIsDeletedFalse(e.getMaintenanceRequestId());
            if(eva != null){
                EvaluateReponseDTO evaluate = modelMapper.map(eva, EvaluateReponseDTO.class);
                listEvaluate.add(evaluate);
            }
        }
        resp.setData(listEvaluate);
        return resp;
    }





   

   
}
