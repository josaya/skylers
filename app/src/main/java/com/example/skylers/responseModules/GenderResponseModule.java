package com.example.skylers.responseModules;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GenderResponseModule {

    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("SuccessResponse")
    @Expose
    private List<SuccessResponse> successResponse = null;
    @SerializedName("ErrorMessage")
    @Expose
    private Object errorMessage;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SuccessResponse> getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(List<SuccessResponse> successResponse) {
        this.successResponse = successResponse;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

}