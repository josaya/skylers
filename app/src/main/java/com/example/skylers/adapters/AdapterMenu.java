package com.example.skylers.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylers.R;
import com.example.skylers.modules.MenuModule;

import java.util.List;

import main.ActivityMembersList;
import main.ActivityPayments;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.MyViewHolder>{

    Activity context;
    List<MenuModule> lstMenuModel;

    public AdapterMenu(Activity context, List<MenuModule> lstMenuModel){
        this.context      = context;
        this.lstMenuModel = lstMenuModel;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_main_menu, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MenuModule menuModule = lstMenuModel.get(position);

        holder.txtMenuName.setText(menuModule.getMenuName());

        if (menuModule.getMenuId().equals("1")){
            holder.imgImage.setImageResource(R.drawable.icon_payment);
        }

        if (menuModule.getMenuId().equals("2")){
            holder.imgImage.setImageResource(R.drawable.icon_registration);
        }

        if (menuModule.getMenuId().equals("3")){
            holder.imgImage.setImageResource(R.drawable.icon_reports);
        }

        if (menuModule.getMenuId().equals("4")){
            holder.imgImage.setImageResource(R.drawable.icon_chat);
        }

        holder.llMenu.setOnClickListener(view -> {

            //Payments
            if (menuModule.getMenuId().equals("1")){
                Intent intent = new Intent(context, ActivityPayments.class);
                context.startActivity(intent);
            }

            //Members
            if (menuModule.getMenuId().equals("2")){
                Intent intent = new Intent(context, ActivityMembersList.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstMenuModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgImage;
        public TextView txtMenuName;
        LinearLayout llMenu;


        public MyViewHolder(View view) {
            super(view);
            imgImage    = view.findViewById(R.id.imgImage);
            txtMenuName = view.findViewById(R.id.txtMenuName);
            llMenu      = view.findViewById(R.id.llMenu);

        }
    }
}
