package com.amitgroup.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class BaseDetailResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String uid;

    public void setId(Long id) {
       this.id = id;
    }

    public void setUid(String uid) {
        if (ShareConstants.ApplicationSetting.IS_ENABLE_HASHIDS){
            this.uid = uid;
        } else {
            this.uid = null;
        }
    }
}
