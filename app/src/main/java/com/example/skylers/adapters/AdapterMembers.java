package com.example.skylers.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skylers.R;
import com.example.skylers.modules.MemberModule;
import com.example.skylers.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import main.ActivityMemberTransactions;

public class AdapterMembers extends RecyclerView.Adapter<AdapterMembers.MyViewHolder> implements Filterable {

    Activity context;
    List<MemberModule> lstMembers;
    List<MemberModule> lstAllMembers;
    SharedPreference sharedPreference;

    public AdapterMembers(Activity context, List<MemberModule> lstMembers){
        this.context        =   context;
        this.lstMembers     =   lstMembers;
        this.lstAllMembers  =   lstMembers;

        sharedPreference    = new SharedPreference(context);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_members, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MemberModule memberModule = lstMembers.get(position);

        Glide.with(context).load(memberModule.getMemberPhoto()).into(holder.imgUser);

        holder.txtCustomerName.setText(memberModule.getMemberName());
        holder.txtCustomerNumber.setText(memberModule.getMemberNumber());

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ActivityMemberTransactions.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return lstMembers.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    lstMembers = lstAllMembers;
                } else {
                    List<MemberModule> filteredList = new ArrayList<>();
                    for (MemberModule prd : lstAllMembers) {

                        // name match condition. this might differ depending on your requirement
                        // here we are searching for a member

                        if (sharedPreference.getSearchCriteria().equals("SearchByName")){

                            if (prd.getMemberName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(prd);
                            }
                        }

                        if (sharedPreference.getSearchCriteria().equals("SearchByAccountNumber")){

                            if (prd.getMemberNumber().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(prd);
                            }
                        }

                    }

                    lstMembers = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = lstMembers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lstMembers = (ArrayList<MemberModule>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgUser;
        public TextView txtCustomerName, txtCustomerNumber;
        CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            imgUser             =   view.findViewById(R.id.imgUser);
            txtCustomerName     =   view.findViewById(R.id.txtCustomerName);
            txtCustomerNumber   =   view.findViewById(R.id.txtCustomerNumber);
            cardView            =   view.findViewById(R.id.cardView);

        }
    }

}
