package main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylers.adapters.AdapterDashBoard;
import com.example.skylers.adapters.AdapterMenu;
import com.example.skylers.databinding.ActivityHomeScreenBinding;
import com.example.skylers.modules.DashBoardModule;
import com.example.skylers.modules.MenuModule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityHomeScreen extends AppCompatActivity {

    private ActivityHomeScreenBinding binding;
    List<MenuModule> lstMenu;
    List<DashBoardModule> lstDashboard;
    AdapterMenu adapterMenu;
    AdapterDashBoard adapterDashBoard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeScreenBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        lstMenu         = new ArrayList<>();
        lstDashboard    = new ArrayList<>();

        processMenu(menu);
        processDashboard(dashBoard);


    }


    public void processMenu(String category){

        try {
            JSONArray jsonArray = new JSONArray(category);

            lstMenu.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String menuId         = jsonObject.getString("menuId");
                String menuName       = jsonObject.getString("menuName");

               MenuModule menuModule = new MenuModule(menuId, menuName);
               lstMenu.add(menuModule);

            }

            adapterMenu = new AdapterMenu(ActivityHomeScreen.this, lstMenu);
            binding.recyclerViewMenu.setLayoutManager(new GridLayoutManager(ActivityHomeScreen.this, 3));
            binding.recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerViewMenu.setAdapter(adapterMenu);
            adapterMenu.notifyDataSetChanged();


        }catch (Exception e){
            Log.d("json_profile_menu", "processMenuCategories: "+e.getMessage());

        }

    }

    public void processDashboard(String dashBoard){
        try {

            JSONArray jsonArray = new JSONArray(dashBoard);
            lstDashboard.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String product      =   jsonObject.getString("product");
                String performance  =   jsonObject.getString("productPerformance");
                String percentage   =   jsonObject.getString("percentageIncrease");

                DashBoardModule dashBoardModule = new DashBoardModule(product, performance, percentage);
                lstDashboard.add(dashBoardModule);

            }

            adapterDashBoard = new AdapterDashBoard(ActivityHomeScreen.this, lstDashboard);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityHomeScreen.this, LinearLayoutManager.HORIZONTAL, false);
            binding.recyclerReports.setLayoutManager(mLayoutManager);
            binding.recyclerReports.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerReports.setAdapter(adapterDashBoard);
            adapterDashBoard.notifyDataSetChanged();


        } catch (Exception e){
            Log.d("json_exception", e.getMessage());
        }
    }

    public String menu = "[\n" +
            "  {\n" +
            "    \"menuId\": \"1\",\n" +
            "    \"menuName\": \"Payments\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"menuId\": \"2\",\n" +
            "    \"menuName\": \"Members\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"menuId\": \"3\",\n" +
            "    \"menuName\": \"Reports\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"menuId\": \"4\",\n" +
            "    \"menuName\": \"Contact\n Support\"\n" +
            "  }\n" +
            "]";

    public String dashBoard = "[\n" +
            "  {\n" +
            "    \"product\": \"Loans\",\n" +
            "    \"productPerformance\": \"10\",\n" +
            "    \"percentageIncrease\": \"50\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"product\": \"New Members\",\n" +
            "    \"productPerformance\": \"15\",\n" +
            "    \"percentageIncrease\": \"10\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"product\": \"Nominal Shares\",\n" +
            "    \"productPerformance\": \"30\",\n" +
            "    \"percentageIncrease\": \"-10\"\n" +
            "  }\n" +
            "]";
}
