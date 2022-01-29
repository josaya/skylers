package com.example.skylers.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylers.R;
import com.example.skylers.modules.DashBoardModule;

import java.util.List;

public class AdapterDashBoard extends RecyclerView.Adapter<AdapterDashBoard.MyViewHolder>{

    Activity context;
    List<DashBoardModule> lstDashboard;

    public AdapterDashBoard(Activity context, List<DashBoardModule> lstDashboard){
        this.context        = context;
        this.lstDashboard   = lstDashboard;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_dashboard, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DashBoardModule dashBoardModule = lstDashboard.get(position);

        holder.txtProduct.setText(dashBoardModule.getProduct());

        if (dashBoardModule.getProduct().equals("Loans")){
            holder.txtProductPerformance.setText(dashBoardModule.getProductPerformance()+ " Loans issued");
        }
        if (dashBoardModule.getProduct().equals("New Members")){
            holder.imgProduct.setImageResource(R.drawable.icons_new_member);
            holder.txtProductPerformance.setText(dashBoardModule.getProductPerformance()+ " registered members");
        }

        if (dashBoardModule.getProduct().equals("Nominal Shares")){
            holder.imgProduct.setImageResource(R.drawable.icon_certificate);
            holder.txtProductPerformance.setText(dashBoardModule.getProductPerformance()+ " Shares issued");

        }

        if (Integer.parseInt(dashBoardModule.getPercentageIncrease()) < 0){
            holder.imgArrow.setImageResource(R.drawable.icons_down_arrow);
            holder.txtPercentage.setText(dashBoardModule.getPercentageIncrease() +"% decrease");
        } else {
            holder.txtPercentage.setText(dashBoardModule.getPercentageIncrease() +"% increase");
        }


    }

    @Override
    public int getItemCount() {
        return lstDashboard.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProduct, imgArrow;
        public TextView txtProduct, txtProductPerformance, txtPercentage;
        LinearLayout llMenu;


        public MyViewHolder(View view) {
            super(view);
            imgProduct              =   view.findViewById(R.id.imgProduct);
            txtProduct              =   view.findViewById(R.id.txtProduct);
            txtProductPerformance   =   view.findViewById(R.id.txtProductPerformance);
            imgArrow                =   view.findViewById(R.id.imgArrow);
            txtPercentage           =   view.findViewById(R.id.txtPercentage);

        }
    }
}
