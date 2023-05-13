package com.amitgroup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BasePaginationResponse<T>{

    @Schema(defaultValue = "1" , description = "Result Code")
    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(defaultValue = "Message" , description = "Result message")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(defaultValue = "MESSAGE_CODE" , description = "Message code")
    private String messageCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Trace ID")
    private String traceId;

    private List<T> data;

    @Schema(description = "Page size (number of items per page)")
    public int pageSize;

    @JsonIgnore
    @Schema(description = "Page index (begin = 0)")
    public int pageIndex;

    @Schema(description = "Page number (begin = 1)")
    public int pageNumber;

    @Schema(description = "Number of total items")
    public long totalItems;

    public BasePaginationResponse(List<T> data, int pageIndex, int pageSize, long totalItems){
        this.code = 200;
        this.data = data;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.pageNumber = pageIndex + 1;
    }

    public BasePaginationResponse(String message){
        this.code = 400;
        this.message = message;
        this.messageCode = "FAILED";
    }
}
