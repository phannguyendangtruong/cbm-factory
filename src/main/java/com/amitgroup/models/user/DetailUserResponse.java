package com.amitgroup.models.user;


import com.amitgroup.models.BaseDetailResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailUserResponse extends BaseDetailResponse {

    @Schema(description = "email address", example = "user@mail.com")
    private String email;

    @Schema(description = "first name", example = "Bui")
    private String firstName;

    @Schema(description = "last name", example = "Thanh Phuong")
    private String lastName;

    @Schema(description = "user activation status", example = "true")
    private Boolean isActivated;

    @Schema(description = "email verification status", example = "true")
    private Boolean isVerifiedEmail;

    @Schema(description = "created by", example = "user@mail.com")
    private String createdBy;

    @Schema(description = "created date (yyyy-MM-dd'T'HH:mm:ss.SSSZ)", example = "2023-02-17T04:16:30.926+0000")
    private String createdDate;

    @Schema(description = "last modified by", example = "user@mail.com")
    private String lastModifiedBy;

    @Schema(description = "last modified date (yyyy-MM-dd'T'HH:mm:ss.SSSZ)", example = "2023-02-17T04:16:30.926+0000")
    private String lastModifiedDate;

    // public static DetailUserResponse of(User user, String uid) {
    //     DetailUserResponse dto = new DetailUserResponse();
    //     dto.setId(user.getId());
    //     dto.setUid(uid);
    //     dto.setEmail(user.getEmail());
    //     dto.setFirstName(user.getFirstName());
    //     dto.setLastName(user.getLastName());
    //     dto.setIsActivated(BooleanUtils.safeUnboxing(user.getIsActivated()));
    //     dto.setIsVerifiedEmail(BooleanUtils.safeUnboxing(user.getIsVerifiedEmail()));
    //     dto.setCreatedDate(TimeUtils.dateToStringSimpleDateFormat(user.getCreatedDate().toEpochSecond()));
    //     dto.setLastModifiedDate(TimeUtils.dateToStringSimpleDateFormat(user.getLastModifiedDate().toEpochSecond()));
    //     dto.setCreatedBy(user.getCreatedBy());
    //     dto.setLastModifiedBy(user.getLastModifiedBy());
    //     return dto;
    // }
}
