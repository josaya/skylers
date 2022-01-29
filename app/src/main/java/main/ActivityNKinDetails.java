package main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skylers.databinding.ActivityNkinDetailsBinding;

public class ActivityNKinDetails extends AppCompatActivity {

    private ActivityNkinDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNkinDetailsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        setListeners();
    }

    public void setListeners(){
        binding.txtSubmit.setOnClickListener(view -> {

            Intent intent = new Intent(ActivityNKinDetails.this, ActivityIDFront.class);
            startActivity(intent);

        });
    }
}
