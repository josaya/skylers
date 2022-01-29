package main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.skylers.databinding.ActivityLoginBinding;
import com.example.skylers.modules.ServiceResponseModel;
import com.example.skylers.modules.SuccessResponse;
import com.example.skylers.requestModules.LoginRequestModule;

import com.example.skylers.requestModules.SkylersRequest;
import com.example.skylers.requestModules.SkylersResponse;
import com.example.skylers.responseModules.LoginResponseModel;
import com.example.skylers.utils.Api;
import com.example.skylers.utils.AppUtils;
import com.example.skylers.utils.SharedPreference;
import com.example.skylers.viewModel.LoginViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AndroidEntryPoint
public class ActivityLogin extends AppCompatActivity {

    private ActivityLoginBinding binding;
    List<SuccessResponse> lstAgentDetails;
    String userId = "", userName = "", userPic = "";

    @Inject
    SharedPreference sharedPreference;

    LoginViewModel loginViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        //Instantiating a viewModel
        loginViewModel = new ViewModelProvider(ActivityLogin.this).get(LoginViewModel.class);

        init();

        if (sharedPreference.getIsAgentLoggedIn()){

            Intent intent = new Intent(ActivityLogin.this, ActivityHomeScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();


        }

        setListeners();

    }

    public void init(){
        lstAgentDetails = new ArrayList<>();
    }

    public void setListeners(){
        binding.txtLogin.setOnClickListener(view -> {

            if (binding.txtUserName.getText().toString().trim().isEmpty()){
                Toast.makeText(ActivityLogin.this, "Please enter user name", Toast.LENGTH_SHORT).show();

            } else if (binding.txtPassword.getText().toString().trim().isEmpty()){
                Toast.makeText(ActivityLogin.this, "Please enter Password", Toast.LENGTH_SHORT).show();

            } else {

                loginViewModel.getLoginResponse().observe(ActivityLogin.this, new Observer<ServiceResponseModel>() {
                    @Override
                    public void onChanged(ServiceResponseModel serviceResponseModel) {

                        AppUtils.dismissProgressDialog();

                        if (serviceResponseModel.getResponseCode().equals("200")){
                            //AppUtils.ToastMessage(serviceResponseModel.getResponseMessage(), ActivityLogin.this);

                            //AppUtils.SuccessDialog(ActivityLogin.this, "Login successful");

                            Intent intent = new Intent(ActivityLogin.this, ActivityHomeScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // Add new Flag to start new Activity
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else {

                            AppUtils.ToastMessage(serviceResponseModel.getResponseMessage(), ActivityLogin.this);
                        }

                    }
                });

                AppUtils.loadProgressDialog(ActivityLogin.this, "Login", "Loading. Please wait...", false);
                loginViewModel.loginRequest(binding.txtUserName.getText().toString().trim(), binding.txtPassword.getText().toString().trim());


            }
        });


    }


}
