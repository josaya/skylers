package com.example.skylers.viewModel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skylers.apiInterfaces.ApiHelper;
import com.example.skylers.modules.CountryCodesModel;
import com.example.skylers.modules.GenderModule;
import com.example.skylers.modules.OccupationsModule;
import com.example.skylers.requestModules.SkylersRequest;
import com.example.skylers.utils.AppUtils;
import com.example.skylers.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class SystemCodesViewModel extends ViewModel {

    @Inject
    ApiHelper apiHelper;

    @Inject
    SharedPreference sharedPreference;

    public MutableLiveData<List<GenderModule>> genderMutableLiveData;
    List<GenderModule> lstGender;

    public MutableLiveData<List<CountryCodesModel>> countryCodesMutableLiveData;
    List<CountryCodesModel> lstCountryCodes;

    public MutableLiveData<List<OccupationsModule>> occupationsMutableLiveData;
    List<OccupationsModule> lstOccupations;

    @Inject
    public SystemCodesViewModel(){
    }

    //Gender MutableLiveData
    public MutableLiveData<List<GenderModule>> getGenderMutableLiveData() {

        if (genderMutableLiveData == null){
            genderMutableLiveData = new MutableLiveData<>();
        }

        return genderMutableLiveData;
    }

    //Countries MutableLiveData
    public MutableLiveData<List<CountryCodesModel>> getCountryCodesMutableLiveData() {

        if (countryCodesMutableLiveData == null){
            countryCodesMutableLiveData = new MutableLiveData<>();
        }

        return countryCodesMutableLiveData;
    }

    //Occupations MutableLiveData
    public MutableLiveData<List<OccupationsModule>> getOccupationsMutableLiveData() {

        if (occupationsMutableLiveData == null){
            occupationsMutableLiveData = new MutableLiveData<>();
        }

        return occupationsMutableLiveData;
    }

    public void systemCodesRequest(String id){
        JSONObject jsonObject = new JSONObject();

        try {

            try {
                jsonObject.put("RequestID", "SysCodeDetails");

            }catch (Exception e){
                AppUtils.Log("json_exception", e.getMessage());
            }

            JSONArray jsonArray = new JSONArray();

            try {
                JSONObject dataObject = new JSONObject();
                dataObject.put("Id", id);

                jsonArray.put(dataObject);

            }catch (Exception e){
                AppUtils.Log("json_exception2", e.getMessage());
            }

            jsonObject.put("Data", jsonArray);

        }catch (Exception e){
            AppUtils.Log("json_exception", e.getMessage());
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(jsonObject);

        String encryptedString = AppUtils.Base64Encrypt(stringBuilder.toString());

        AppUtils.Log("request_sysCodes", stringBuilder.toString());
        AppUtils.Log("request_sysCodes_encrypt", encryptedString);


        //
        if (id.equals("GEN")){
            systemCodes(encryptedString);

        } else if (id.equals("NAT")){
            countryCodes(encryptedString);

        } else if (id.equals("OCC")){
            occupationsCodes(encryptedString);
        }


    }

    public void systemCodes(String requestString){

        SkylersRequest skylersRequest = new SkylersRequest(requestString);

        Call<String> call = apiHelper.systemCodes(skylersRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String callResponse = "", successResponse = "", id = "", name = "";

                lstGender = new ArrayList<>();

                //{"Status":200,"SuccessResponse":[{"ID":"1","Name":"Male"},{"ID":"2","Name":"Female"},{"ID":"3","Name":"Others"}],"ErrorMessage":null}

                if (response.isSuccessful()){

                    callResponse = AppUtils.Base64Decrypt(response.body());
                    AppUtils.Log("response_sysCodes", callResponse);

                    try {
                        JSONObject jsonObject = new JSONObject(callResponse);

                        Integer status = jsonObject.getInt("Status");

                        if (status == 200){
                            successResponse = jsonObject.getString("SuccessResponse");

                            JSONArray jsonArray = new JSONArray(successResponse);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject genderObject = jsonArray.getJSONObject(i);

                                id      =   genderObject.getString("ID");
                                name    =   genderObject.getString("Name");

                                GenderModule genderModule = new GenderModule(id, name);
                                lstGender.add(genderModule);

                                AppUtils.Log("genderNames1", lstGender.get(i).getGenderName());

                            }

                            genderMutableLiveData.setValue(lstGender);

                        }


                    }catch (Exception e){
                        AppUtils.Log("json_exception", e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                AppUtils.Log("json_exception", t.getMessage());

                /*SystemCodesModel systemCodesModel = new SystemCodesModel("091", AppConstants.Error_Message, "");
                systemCodesModelMutableLiveData.setValue(systemCodesModel);*/

            }
        });


    }


    public void countryCodes(String requestString){

        SkylersRequest skylersRequest = new SkylersRequest(requestString);

        Call<String> call = apiHelper.systemCodes(skylersRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String callResponse = "", successResponse = "", id = "", name = "";

                lstCountryCodes = new ArrayList<>();

                //{"Status":200,"SuccessResponse":[{"ID":"1","Name":"Male"},{"ID":"2","Name":"Female"},{"ID":"3","Name":"Others"}],"ErrorMessage":null}

                if (response.isSuccessful()){

                    callResponse = AppUtils.Base64Decrypt(response.body());
                    AppUtils.Log("response_system_codes", callResponse);

                    try {
                        JSONObject jsonObject = new JSONObject(callResponse);

                        Integer status = jsonObject.getInt("Status");

                        if (status == 200){
                            successResponse = jsonObject.getString("SuccessResponse");

                            JSONArray jsonArray = new JSONArray(successResponse);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject genderObject = jsonArray.getJSONObject(i);

                                id      =   genderObject.getString("ID");
                                name    =   genderObject.getString("Name");

                                CountryCodesModel countryCodesModel = new CountryCodesModel(id, name);
                                lstCountryCodes.add(countryCodesModel);

                                AppUtils.Log("countryNames", lstCountryCodes.get(i).getName());

                            }

                            countryCodesMutableLiveData.setValue(lstCountryCodes);

                        }


                    }catch (Exception e){
                        AppUtils.Log("json_exception", e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                AppUtils.Log("json_exception", t.getMessage());

                /*SystemCodesModel systemCodesModel = new SystemCodesModel("091", AppConstants.Error_Message, "");
                systemCodesModelMutableLiveData.setValue(systemCodesModel);*/

            }
        });


    }


    public void occupationsCodes(String requestString){

        SkylersRequest skylersRequest = new SkylersRequest(requestString);

        Call<String> call = apiHelper.systemCodes(skylersRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String callResponse = "", successResponse = "", id = "", name = "";

                lstOccupations = new ArrayList<>();

                //{"Status":200,"SuccessResponse":[{"ID":"1","Name":"Male"},{"ID":"2","Name":"Female"},{"ID":"3","Name":"Others"}],"ErrorMessage":null}

                if (response.isSuccessful()){

                    callResponse = AppUtils.Base64Decrypt(response.body());
                    AppUtils.Log("response_system_codes", callResponse);

                    try {
                        JSONObject jsonObject = new JSONObject(callResponse);

                        Integer status = jsonObject.getInt("Status");

                        if (status == 200){
                            successResponse = jsonObject.getString("SuccessResponse");

                            JSONArray jsonArray = new JSONArray(successResponse);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject genderObject = jsonArray.getJSONObject(i);

                                id      =   genderObject.getString("ID");
                                name    =   genderObject.getString("Name");

                                OccupationsModule occupationsModule = new OccupationsModule(id, name);
                                lstOccupations.add(occupationsModule);

                                AppUtils.Log("occupationNames", lstOccupations.get(i).getName());

                            }

                            occupationsMutableLiveData.setValue(lstOccupations);

                        }


                    }catch (Exception e){
                        AppUtils.Log("json_exception", e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                AppUtils.Log("json_exception", t.getMessage());

                /*SystemCodesModel systemCodesModel = new SystemCodesModel("091", AppConstants.Error_Message, "");
                systemCodesModelMutableLiveData.setValue(systemCodesModel);*/

            }
        });


    }




}
