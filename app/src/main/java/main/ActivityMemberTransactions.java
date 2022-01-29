package main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skylers.R;
import com.example.skylers.databinding.ActivityMemberTransactionsBinding;
import com.example.skylers.fragments.FragmentCollections;
import com.example.skylers.fragments.FragmentMemberAccounts;
import com.example.skylers.fragments.FragmentMemberReports;
import com.example.skylers.fragments.FragmentPayment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActivityMemberTransactions extends AppCompatActivity {

    private ActivityMemberTransactionsBinding binding;
    Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMemberTransactionsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());


        fragment = new FragmentMemberAccounts();
        loadFragment(fragment, "Client accounts");

        setListeners();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        removeFragments();
        finish();
    }

    public void setListeners(){
        binding.bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected( MenuItem item) {


            switch (item.getItemId()) {
                case R.id.nav_residential:

                    removeFragments();

                    fragment = new FragmentMemberAccounts();
                    loadFragment(fragment, "MemberAccounts");

                    return true;

                case R.id.nav_office_space:

                    removeFragments();

                    fragment = new FragmentPayment();
                    loadFragment(fragment, "CollectPaymants");

                    return true;

                case R.id.nav_store:

                    removeFragments();

                    fragment = new FragmentMemberReports();
                    loadFragment(fragment, "Reports");

                    return true;



            }
            return false;
        }
    };


    private void loadFragment(Fragment fragment, String fragment_tag) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, fragment_tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void removeFragments(){
        FragmentManager fm = ActivityMemberTransactions.this.getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
}
