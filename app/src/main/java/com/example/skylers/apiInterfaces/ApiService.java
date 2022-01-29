package com.example.skylers.apiInterfaces;

import com.example.skylers.requestModules.GenderRequestModule;
import com.example.skylers.requestModules.LoginRequestModule;
import com.example.skylers.requestModules.NationalityRequestModule;
import com.example.skylers.requestModules.OccupationRequestModule;
import com.example.skylers.requestModules.SkylersRequest;
import com.example.skylers.requestModules.SkylersResponse;
import com.example.skylers.responseModules.GenderResponseModule;
import com.example.skylers.responseModules.LoginResponseModel;
import com.example.skylers.responseModules.NationalityResponseModel;
import com.example.skylers.responseModules.OccupationResponseModule;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface ApiService {

    //Getting countries
    @PUT("/Api/v1/GenericService/SysCodeDetail")
    Call<NationalityResponseModel> sendId(@Body NationalityRequestModule module);

    //Getting genders
    @PUT("/Api/v1/GenericService/SysCodeDetail")
    Call<GenderResponseModule> genderGender(@Body GenderRequestModule genderModule);

    @PUT("/Api/v1/GenericService/SysCodeDetail")
    Call<OccupationResponseModule> getOccupations(@Body OccupationRequestModule occupationModule);

    //Login
    @PUT("/Api/v1/GenericService/ValidateUser")
    Call<LoginResponseModel> login(@Body LoginRequestModule loginRequestModule);


    @PUT("GenericService/GenService")
    Call<String> endpointCall(@Body SkylersRequest skylersRequest);

    @PUT("GenericService/GenService")
    Call<String> systemCodes(@Body SkylersRequest skylersRequest);


}
