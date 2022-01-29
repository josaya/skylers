package main;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skylers.databinding.ActivityRegistrationDetailsBinding;
import com.example.skylers.utils.SharedPreference;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ActivityRegistrationDetails extends AppCompatActivity {

    ActivityRegistrationDetailsBinding binding;

    @Inject
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrationDetailsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        setFields();
    }

    public void setFields(){
        binding.txtName.setText(sharedPreference.getFirstName()+" "+sharedPreference.getSurName());
        binding.txtIDNumber.setText(sharedPreference.getIDNumber());
        binding.txtKraPin.setText(sharedPreference.getKRAPIN());
        binding.txtDOB.setText(sharedPreference.getDOB());
        binding.txtAddress.setText(sharedPreference.getAddress());
        binding.txtCountry.setText(sharedPreference.getCountry());
        binding.txtOccupation.setText(sharedPreference.getOccupation());
    }
}
