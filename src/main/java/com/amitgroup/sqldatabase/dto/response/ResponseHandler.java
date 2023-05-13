package com.amitgroup.sqldatabase.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseHandler {
    public Boolean success;
    @JsonInclude(Include.NON_NULL)
    public String message;
    public List<String> messages;
    public String code;
    @JsonInclude(Include.NON_NULL)
    public String url;
    public Object data;
    @JsonInclude(Include.NON_NULL)
    public Integer totalItems;

    public ResponseHandler() {
        this.setSuccess(true);
        this.setCode("SUCCESS");
        setMessages(new ArrayList<String>());
    }

    public ResponseHandler(String error) {
        this.setSuccess(false);
        this.setCode("FAILED");
        this.setMessage(error);
        setMessages(new ArrayList<String>());
    }

    public ResponseHandler(List<String> error) {
        this.setSuccess(false);
        this.setCode("FAILED");
        setMessages(error);
    }

}