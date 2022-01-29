package com.example.skylers.di;

import com.example.skylers.apiInterfaces.ApiHelper;
import com.example.skylers.apiInterfaces.ApiImplementation;
import com.example.skylers.apiInterfaces.ApiService;
import com.example.skylers.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class SkylersAppModule {

    @Provides
    public static OkHttpClient providesOkHttpClient(){
        return new OkHttpClient
                .Builder()
                .connectTimeout(6000, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    public static Gson providesGson(){
        return new GsonBuilder()
                .create();
    }

    @Provides
    public static GsonConverterFactory providesGsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public static Retrofit providesRetrofit(OkHttpClient okHttpClient, GsonConverterFactory converterFactory){
        return new Retrofit
                .Builder()
                .baseUrl(AppUtils.baseUrl)
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    public static ApiService providesApiInterface(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    public static ApiHelper providesApiHelper(ApiImplementation apiImplementation){
        return apiImplementation;
    }


}
