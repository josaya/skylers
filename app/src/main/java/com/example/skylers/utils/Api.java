package com.example.skylers.utils;


import com.example.skylers.apiInterfaces.ApiService;

public class Api {


    public static ApiService getAPIService() {

        //Gets the retrofit object from Retrofit client and then create an ApiInterface
        return RetrofitClient.getClient(AppUtils.baseUrl).create(ApiService.class);
    }
}
