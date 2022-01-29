package main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylers.adapters.AdapterMembers;
import com.example.skylers.databinding.ActivityPaymentsBinding;
import com.example.skylers.modules.MemberModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityPayments extends AppCompatActivity {

    private ActivityPaymentsBinding binding;
    AdapterMembers adapterMembers;
    List<MemberModule> lstMembers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        lstMembers = new ArrayList<>();

        showMembers(members);

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

            adapterMembers = new AdapterMembers(ActivityPayments.this, lstMembers);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            binding.recyclerViewMembers.setLayoutManager(mLayoutManager);
            binding.recyclerViewMembers.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerViewMembers.setAdapter(adapterMembers);
            adapterMembers.notifyDataSetChanged();


        }catch (Exception e){
            Log.d("json_exception", "showMembers: "+e.getMessage());
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
