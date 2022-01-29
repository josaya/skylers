package com.example.skylers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylers.R;
import com.example.skylers.adapters.AdapterMemberAccounts;
import com.example.skylers.modules.MemberAccounts;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class FragmentMemberAccounts extends Fragment {

    Context context;
    View view;
    List<MemberAccounts> lstAccounts;
    AdapterMemberAccounts adapterMemberAccounts;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = inflater.getContext();
        view =  inflater.inflate(R.layout.fragment_member_accounts, container, false);

        recyclerView    =   view.findViewById(R.id.recyclerView);

        lstAccounts = new ArrayList<>();

        populateAccounts(accounts);

        return view;
    }

    public void populateAccounts(String accounts){
        try {

            JSONArray jsonArray = new JSONArray(accounts);

            lstAccounts.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String acc_name     = jsonObject.getString("accountName");
                String acc_number   = jsonObject.getString("accountNumber");
                String acc_amount   = jsonObject.getString("accountAmount");

                MemberAccounts memberAccounts = new MemberAccounts(acc_name, acc_number, acc_amount);
                lstAccounts.add(memberAccounts);

            }

            adapterMemberAccounts = new AdapterMemberAccounts(getActivity(), lstAccounts);
            adapterMemberAccounts.notifyDataSetChanged();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapterMemberAccounts);

        } catch (Exception e){
            Log.d("json_exception", e.getMessage());
        }
    }

    public String accounts = "[\n" +
            "  {\n" +
            "    \"accountName\": \"Loans\",\n" +
            "    \"accountNumber\": \"SKLN805900\",\n" +
            "    \"accountAmount\": \"Ksh. 60, 000\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"accountName\": \"Savings\",\n" +
            "    \"accountNumber\": \"SKSV800900\",\n" +
            "    \"accountAmount\": \"Ksh. 40, 000\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"accountName\": \"Nominal Shares\",\n" +
            "    \"accountNumber\": \"SH205911\",\n" +
            "    \"accountAmount\": \"Ksh. 50, 000\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"accountName\": \"Education Fund\",\n" +
            "    \"accountNumber\": \"SKEF300500\",\n" +
            "    \"accountAmount\": \"Ksh. 50, 000\"\n" +
            "  }\n" +
            "]";
}
