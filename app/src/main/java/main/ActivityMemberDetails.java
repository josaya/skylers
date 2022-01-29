package main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.skylers.R;
import com.example.skylers.databinding.ActivityMemberDetailsBinding;
import com.example.skylers.databinding.DialogDateOfBirthBinding;
import com.example.skylers.modules.GenderModule;
import com.example.skylers.utils.AppUtils;
import com.example.skylers.utils.SharedPreference;
import com.example.skylers.viewModel.SystemCodesViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActivityMemberDetails extends AppCompatActivity {

    private ActivityMemberDetailsBinding binding;
    Dialog DOBDialog;
    String DateOfBirth = "";
    String genderId = "", genderName = "";
    ArrayList<String> genderArrayList;
    List<GenderModule> lstGender;
    ArrayAdapter genderArrayAdapter;

    SystemCodesViewModel systemCodesViewModel;

    @Inject
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMemberDetailsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        systemCodesViewModel = new ViewModelProvider(ActivityMemberDetails.this).get(SystemCodesViewModel.class);

        init();
        setListeners();
        //getGender();

        //getMemberGender();

    }

    public void init(){
        genderArrayList = new ArrayList();
        lstGender       = new ArrayList<>();
    }

    public void setListeners(){
        binding.tvDateOfBirth.setOnClickListener(view -> {
            alert(ActivityMemberDetails.this);
        });

        binding.txtSubmit.setOnClickListener(view -> {

            sharedPreference.setFirstName(binding.txtFirstName.getText().toString().trim());
            sharedPreference.setMiddleName(binding.txtMiddleName.getText().toString().trim());
            sharedPreference.setSurName(binding.txtSurName.getText().toString().trim());
            sharedPreference.setIdNumber(binding.txtIdNumber.getText().toString().trim());
            sharedPreference.setIDNumber(binding.txtPhoneNumber.getText().toString().trim());
            sharedPreference.setKRAPIN(binding.txtKraPin.getText().toString().trim());
            sharedPreference.setEmail(binding.txtEmail.getText().toString().trim());
            sharedPreference.setDOB(binding.tvDateOfBirth.getText().toString().trim());
            sharedPreference.setGender(binding.spnGender.getText().toString());



            Intent intent = new Intent(ActivityMemberDetails.this, ActivityAddressInformation.class);
            startActivity(intent);

        });

        binding.spnGender.setOnItemClickListener((parent, view, position, id) -> {

            String genId = lstGender.get(position).getGenderId();

            AppUtils.Log("genderId", genId);

        });


    }


    public  void alert(Context mContext) {
        if (DOBDialog != null) {
            if (DOBDialog.isShowing()) {
                return;
            }
        }
        DOBDialog = new Dialog(mContext);
        try{
            DOBDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }catch (Exception e){

        }

        DOBDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DialogDateOfBirthBinding dobBinding = DialogDateOfBirthBinding.inflate(LayoutInflater.from(mContext));
        DOBDialog.setContentView(dobBinding.getRoot());
        DOBDialog.setCancelable(false);

        dobBinding.datePicker1.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            StringBuilder date = new StringBuilder().append(dayOfMonth)
                    .append("-").append(monthOfYear + 1).append("-").append(year)
                    .append(" ");

            AppUtils.Log("selected_date1", date.toString());

            try {
                // Note, MM is months, not mm
                DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

                String inputText = date.toString();
                Date format_date = inputFormat.parse(inputText);
                String outputText = outputFormat.format(format_date);

                //AppLog.Log("selected_date", outputText);
                binding.tvDateOfBirth.setText(outputText);

            }catch (Exception e){
                AppUtils.Log("selected_date_error", e.getMessage());
            }

        });

        dobBinding.txtCancel.setOnClickListener(v -> DOBDialog.dismiss());
        DOBDialog.show();
    }

    public void getMemberGender(){

        systemCodesViewModel.getGenderMutableLiveData().observe(ActivityMemberDetails.this, new Observer<List<GenderModule>>() {
            @Override
            public void onChanged(List<GenderModule> genderModules) {

                AppUtils.dismissProgressDialog();

                //Passing the gender list from ViewModel to a local list variable
                lstGender = genderModules;

                for (int i = 0; i < genderModules.size(); i++) {

                    genderArrayList.add(lstGender.get(i).getGenderName());
                }

                genderArrayAdapter = new ArrayAdapter<>(ActivityMemberDetails.this, android.R.layout.simple_spinner_dropdown_item, genderArrayList);
                binding.spnGender.setText(lstGender.get(0).getGenderName());
                binding.spnGender.setAdapter(genderArrayAdapter);

                //Getting the id of the first gender displayed
                String genId = lstGender.get(0).getGenderId();
                AppUtils.Log("genderId", genId);
            }
        });

        AppUtils.loadProgressDialog(ActivityMemberDetails.this, "Gender", "Loading. Please wait...", false);
        systemCodesViewModel.systemCodesRequest("GEN");
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.txtFirstName:
                    validateFirstName();
                    break;

                case R.id.txtMiddleName:
                    //validateMiddleName();
                    break;



                case R.id.txtKraPin:
                    //validateKRAPin();
                    break;

                case R.id.txtSurName:
                    //checkEmailFormat();
                    break;

            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateFirstName() {
        if (binding.txtFirstName.getText().toString().trim().isEmpty()) {
            binding.inputLayoutFirstName.setError("Please enter your first name");
            requestFocus(binding.txtFirstName);
            return false;
        } else {
            binding.inputLayoutFirstName.setErrorEnabled(false);
        }

        return true;
    }
}
