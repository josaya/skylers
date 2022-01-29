package main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.skylers.apiInterfaces.ApiService;
import com.example.skylers.databinding.ActivityAddressInformationBinding;
import com.example.skylers.modules.CountryCodesModel;
import com.example.skylers.modules.GenderModule;
import com.example.skylers.modules.OccupationsModule;
import com.example.skylers.requestModules.NationalityRequestModule;
import com.example.skylers.requestModules.OccupationRequestModule;
import com.example.skylers.responseModules.NationalityResponseModel;
import com.example.skylers.responseModules.OccupationResponseModule;
import com.example.skylers.responseModules.SuccessResponse;
import com.example.skylers.utils.Api;
import com.example.skylers.utils.AppUtils;
import com.example.skylers.utils.SharedPreference;
import com.example.skylers.viewModel.SystemCodesViewModel;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ActivityAddressInformation extends AppCompatActivity {

    private ActivityAddressInformationBinding binding;
    NationalityRequestModule nationalityResponse;
    ApiService apiInterface;
    String success_response = "", occupationId = "", occupationName = "";
    String nations = "";
    ArrayAdapter countriesAdapter;
    ArrayAdapter occArrayAdapter;

    ArrayList<String> countriesArrayList;
    ArrayList<String> occArrayList;

    List<OccupationsModule> lstOccupations;
    List<CountryCodesModel> lstCountries;

    SystemCodesViewModel systemCodesViewModel;

    @Inject
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddressInformationBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        //instantiating viewModel
        systemCodesViewModel = new ViewModelProvider(ActivityAddressInformation.this).get(SystemCodesViewModel.class);

        setListeners();
        init();

        /*getNationsList();
        getOccupations();*/
    }

    public void init(){
        countriesArrayList  = new ArrayList<>();
        occArrayList        = new ArrayList<>();
        lstOccupations      = new ArrayList<>();
        lstCountries        = new ArrayList<>();
    }

    public void setListeners(){
        binding.txtSubmit.setOnClickListener(view -> {

            sharedPreference.setAddress(binding.txtAddress.getText().toString().trim());
            sharedPreference.setCountry(binding.spnCountry.getText().toString().trim());
            sharedPreference.setOccupation(binding.spnOccupation.getText().toString().trim());

            Intent intent = new Intent(ActivityAddressInformation.this, ActivityNKinDetails.class);
            startActivity(intent);
        });

        binding.spnOccupation.setOnItemClickListener((parent, view, position, id) -> {

            //Getting the id of the selected occupation
            String occId = lstOccupations.get(position).getId();
            AppUtils.Log("occupation_id", occId);

        });

        binding.spnCountry.setOnItemClickListener((adapterView, view, position, l) -> {

            //Getting the id of the selected country
            String genId = lstCountries.get(position).getId();
            AppUtils.Log("country_id", genId);


        });
    }

    public void getNationsList() {

        systemCodesViewModel.getCountryCodesMutableLiveData().observe(this, new Observer<List<CountryCodesModel>>() {
            @Override
            public void onChanged(List<CountryCodesModel> countryCodesModels) {
                AppUtils.dismissProgressDialog();

                lstCountries = countryCodesModels;

                for (int i = 0; i < countryCodesModels.size(); i++) {

                    countriesArrayList.add(countryCodesModels.get(i).getName());

                }

                countriesAdapter = new ArrayAdapter<>(ActivityAddressInformation.this, android.R.layout.simple_spinner_dropdown_item, countriesArrayList);
                binding.spnCountry.setText(lstCountries.get(0).getName());
                binding.spnCountry.setAdapter(countriesAdapter);


                //Getting the id of the first country displayed
                String genId = lstCountries.get(0).getId();
                AppUtils.Log("country_id", genId);
            }
        });

        AppUtils.loadProgressDialog(ActivityAddressInformation.this, "", "Loading. Please wait...", false);
        systemCodesViewModel.systemCodesRequest("NAT");

    }

    public void getOccupations(){
        systemCodesViewModel.getOccupationsMutableLiveData().observe(this, new Observer<List<OccupationsModule>>() {
            @Override
            public void onChanged(List<OccupationsModule> occupationsModules) {
                AppUtils.dismissProgressDialog();

                lstOccupations = occupationsModules;

                for (int i = 0; i < occupationsModules.size(); i++) {

                    occArrayList.add(occupationsModules.get(i).getName());

                }

                occArrayAdapter = new ArrayAdapter(ActivityAddressInformation.this, android.R.layout.simple_spinner_dropdown_item, occArrayList);
                binding.spnOccupation.setText(lstOccupations.get(0).getName());
                binding.spnOccupation.setAdapter(occArrayAdapter);

                //Getting the id of the first occupation displayed
                String genId = lstOccupations.get(0).getId();
                AppUtils.Log("occupation_id", genId);

            }
        });

        AppUtils.loadProgressDialog(ActivityAddressInformation.this, "", "Loading. Please wait...", false);
        systemCodesViewModel.systemCodesRequest("OCC");
    }


}
