package com.example.skylers.apiInterfaces;

import com.example.skylers.requestModules.SkylersRequest;
import com.example.skylers.requestModules.SkylersResponse;

import javax.inject.Inject;

import retrofit2.Call;

public class ApiImplementation implements ApiHelper{

    @Inject
    ApiService apiService;

    @Inject
    public ApiImplementation(){

    }

    @Override
    public Call<String> endpointCall(SkylersRequest skylersRequest) {
        return apiService.endpointCall(skylersRequest);
    }

    @Override
    public Call<String> systemCodes(SkylersRequest skylersRequest) {
        return apiService.systemCodes(skylersRequest);
    }
}
