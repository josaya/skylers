package main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylers.R;
import com.example.skylers.adapters.AdapterMembers;
import com.example.skylers.databinding.ActivityMembersListBinding;
import com.example.skylers.modules.MemberModule;
import com.example.skylers.utils.AppUtils;
import com.example.skylers.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityMembersList extends AppCompatActivity {

    private ActivityMembersListBinding binding;
    String searchCriteria = "";

    AdapterMembers adapterMembers;
    List<MemberModule> lstMembers;
    SharedPreference sharedPreference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMembersListBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setListeners();

        sharedPreference = new SharedPreference(ActivityMembersList.this);

        lstMembers = new ArrayList<>();
        showMembers(members);

        binding.txtUserName.setHint("search by Name");

    }

    public void setListeners(){
        binding.cardViewMore.setOnClickListener(view -> showPopupMenu(binding.cardViewMore));

        binding.txtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adapterMembers.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.cardViewRegistration.setOnClickListener(view -> {

            //Clearing cached details
            sharedPreference.setFirstName("");
            sharedPreference.setMiddleName("");
            sharedPreference.setSurName("");
            sharedPreference.setIdNumber("");
            sharedPreference.setIDNumber("");
            sharedPreference.setKRAPIN("");
            sharedPreference.setEmail("");
            sharedPreference.setDOB("");
            sharedPreference.setGender("");
            sharedPreference.setAddress("");
            sharedPreference.setCountry("");
            sharedPreference.setOccupation("");


            Intent intent = new Intent(ActivityMembersList.this, ActivityMemberDetails.class);
            startActivity(intent);
        });
    }

    public void showMembers(String reviews) {
        try {
            JSONArray jsonArray = new JSONArray(reviews);

            lstMembers.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String clientPhoto  = jsonObject.getString("memberPhoto");
                String clientName = jsonObject.getString("memberName");
                String clientNumber   = jsonObject.getString("memberNumber");

                MemberModule memberModule = new MemberModule(clientPhoto, clientName, clientNumber);
                lstMembers.add(memberModule);

            }

            adapterMembers = new AdapterMembers(ActivityMembersList.this, lstMembers);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            binding.recyclerViewMembers.setLayoutManager(mLayoutManager);
            binding.recyclerViewMembers.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerViewMembers.setAdapter(adapterMembers);
            adapterMembers.notifyDataSetChanged();


        }catch (Exception e){
            Log.d("json_exception", "showMembers: "+e.getMessage());
        }
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(ActivityMembersList.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_filter_criteria, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_name:
                    searchCriteria = "SearchByName";

                    sharedPreference.setSearchCriteria(searchCriteria);


                    binding.txtUserName.setHint("search by Name");
                    break;
                case R.id.action_phone_number:
                    searchCriteria = "SearchByPhoneNumber";

                    sharedPreference.setSearchCriteria(searchCriteria);


                    binding.txtUserName.setHint("");
                    binding.txtUserName.setHint("search by Phone Number");
                    break;
                case R.id.action_account_number:
                    searchCriteria = "SearchByAccountNumber";

                    sharedPreference.setSearchCriteria(searchCriteria);

                    binding.txtUserName.setHint("");
                    binding.txtUserName.setHint("search by Account Number");
                    break;
                case R.id.action_id_number:
                    searchCriteria = "SearchByIDNumber";

                    sharedPreference.setSearchCriteria(searchCriteria);

                    binding.txtUserName.setHint("");
                    binding.txtUserName.setHint("search by ID Number");
                    break;

            }
            return false;
        }
    }


    public String members = "[\n" +
            "  {\n" +
            "    \"memberPhoto\":\"https://www.shareicon.net/data/128x128/2016/05/24/770137_man_512x512.png\",\n" +
            "    \"memberName\":\"JOSAYA MURIUKI\",\n" +
            "    \"memberNumber\":\"SKL200300\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"memberPhoto\":\"https://www.shareicon.net/data/128x128/2016/09/15/829452_user_512x512.png\",\n" +
            "    \"memberName\":\"SAMUEL KAMUNYA\",\n" +
            "    \"memberNumber\":\"SKL200301\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"memberPhoto\":\"https://i.dlpng.com/static/png/4296950-avatar-business-face-people-icon-people-icon-png-512_512_preview.webp\",\n" +
            "    \"memberName\":\"JOHN NJERU\",\n" +
            "    \"memberNumber\":\"SKL200302\"\n" +
            "  }\n" +
            "]";
}
