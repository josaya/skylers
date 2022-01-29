package com.example.skylers.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylers.R;
import com.example.skylers.modules.MemberAccounts;

import java.util.List;

public class AdapterMemberAccounts extends RecyclerView.Adapter<AdapterMemberAccounts.MyViewHolder>{

    Activity context;
    List<MemberAccounts> lstMemberAccounts;

    public AdapterMemberAccounts(Activity context, List<MemberAccounts> lstMemberAccounts){
        this.context            =   context;
        this.lstMemberAccounts  =   lstMemberAccounts;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_member_account, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MemberAccounts memberAccounts = lstMemberAccounts.get(position);

        holder.txtAccountName.setText(memberAccounts.getAccountName());
        holder.txtAccountNumber.setText(memberAccounts.getAccountNumber());
        holder.txtAmount.setText(memberAccounts.getAccountAmount());

        if (memberAccounts.getAccountName().equals("Loans")){
            holder.imgCategory.setImageResource(R.drawable.icon_loan_account);
        }

        if (memberAccounts.getAccountName().equals("Savings")){
            holder.imgCategory.setImageResource(R.drawable.icon_savings);
        }

        if (memberAccounts.getAccountName().equals("Nominal Shares")){
            holder.imgCategory.setImageResource(R.drawable.icon_shares);
        }

        if (memberAccounts.getAccountName().equals("Education Fund")){
            holder.imgCategory.setImageResource(R.drawable.icon_education);
        }


    }

    @Override
    public int getItemCount() {
        return lstMemberAccounts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgCategory;
        public TextView txtAccountName, txtAccountNumber, txtAmount;
        CardView cdAccount;


        public MyViewHolder(View view) {
            super(view);
            imgCategory         =   view.findViewById(R.id.imgCategory);
            txtAccountName      =   view.findViewById(R.id.txtAccountName);
            txtAccountNumber    =   view.findViewById(R.id.txtAccountNumber);
            txtAmount           =   view.findViewById(R.id.txtAmount);
            cdAccount           =   view.findViewById(R.id.cdAccount);

        }
    }
}
