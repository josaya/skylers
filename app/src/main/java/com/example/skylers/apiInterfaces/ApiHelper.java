package com.example.skylers.apiInterfaces;

import com.example.skylers.requestModules.SkylersRequest;
import com.example.skylers.requestModules.SkylersResponse;

import retrofit2.Call;

public interface ApiHelper {

    Call<String> endpointCall(SkylersRequest skylersRequest);

    Call<String> systemCodes(SkylersRequest skylersRequest);
}
