package com.example.skylers.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skylers.apiInterfaces.ApiHelper;
import com.example.skylers.modules.ServiceResponseModel;
import com.example.skylers.requestModules.SkylersRequest;
import com.example.skylers.requestModules.SkylersResponse;
import com.example.skylers.utils.Api;
import com.example.skylers.utils.AppConstants;
import com.example.skylers.utils.AppUtils;
import com.example.skylers.utils.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import main.ActivityLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    @Inject
    ApiHelper apiHelper;

    @Inject
    SharedPreference sharedPreference;

    public MutableLiveData<ServiceResponseModel> loginResponseMutableLiveData;

    //Constructor injection
    @Inject
    public LoginViewModel(){
    }

    public MutableLiveData<ServiceResponseModel> getLoginResponse(){

        if (loginResponseMutableLiveData == null){
            loginResponseMutableLiveData = new MutableLiveData<>();
        }

        return loginResponseMutableLiveData;

    }


    public void loginRequest(String userName, String password){

        JSONObject jsonObject = new JSONObject();
        try {

            try {
                jsonObject.put("RequestID", "ValidateUser");

            } catch (Exception e){
                AppUtils.Log("json_exception1", e.getMessage());
            }

            JSONArray jsonArray = new JSONArray();
            try {
                JSONObject dataJson = new JSONObject();
                dataJson.put("username", userName);
                dataJson.put("password", password);

                jsonArray.put(dataJson);

            } catch (Exception e){
                AppUtils.Log("json_exception2", e.getMessage());
            }

            jsonObject.put("Data", jsonArray);


        } catch (Exception e){
            AppUtils.Log("json_exception3", e.getMessage());
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(jsonObject);

        AppUtils.Log("request_login", stringBuilder.toString());

        String encryptedString = AppUtils.Base64Encrypt(stringBuilder.toString()).replace("\n", "");

        agentLogin(encryptedString);


        AppUtils.Log("request_login_encrypt", encryptedString);
        AppUtils.Log("request_login_decrypt1", AppUtils.Base64Decrypt(encryptedString));


    }

    public void agentLogin(String requestString){

        SkylersRequest skylersRequest = new SkylersRequest(requestString);

        Call<String> call = apiHelper.endpointCall(skylersRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String callResponse = "", statusCode = "", successResponse = "",
                        errorMessage = "", userId = "", userName = "", userPic = "";

                AppUtils.Log("response_login1", response.body());

                if (response.isSuccessful()){

                    //{"Status":200,
                    // "SuccessResponse":[{"UserId":"73a5810f-dcfd-4006-8575-56abbe4ff6b1","UserName":"josaya01@gmail.com","UserPicPath":"Files\\Images\\ProfilePictures\\73a5810f-dcfd-4006-8575-56abbe4ff6b1-889a5c69-3784-4a56-bdea-724784f52151.JPG"}],"ErrorMessage":null}

                    callResponse = AppUtils.Base64Decrypt(response.body());
                    AppUtils.Log("response_login2", callResponse);

                    try {
                        JSONObject jsonObject = new JSONObject(callResponse);

                        statusCode      = jsonObject.getString("Status");
                        successResponse = jsonObject.getString("SuccessResponse");
                        errorMessage    = jsonObject.getString("ErrorMessage");

                        JSONArray jsonArray = new JSONArray(successResponse);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            userId   =   object.getString("UserId");
                            userName =   object.getString("UserName");
                            userPic  = object.getString("UserPicPath");

                        }

                        sharedPreference.setIsAgentLoggedIn(true);
                        sharedPreference.setAgentId(userId);
                        sharedPreference.setUserPic(userPic);


                    } catch (Exception e){
                        AppUtils.Log("json_exception", e.getMessage());
                    }


                    ServiceResponseModel serviceResponseModel = new ServiceResponseModel(statusCode, successResponse, errorMessage);
                    loginResponseMutableLiveData.setValue(serviceResponseModel);


                } else {
                    ServiceResponseModel serviceResponseModel = new ServiceResponseModel("091", AppConstants.Error_Message, "");
                    loginResponseMutableLiveData.setValue(serviceResponseModel);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                AppUtils.Log("json_login", t.getMessage());

                ServiceResponseModel serviceResponseModel = new ServiceResponseModel("091", AppConstants.Error_Message, "");
                loginResponseMutableLiveData.setValue(serviceResponseModel);
            }
        });

    }


}
