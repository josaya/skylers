package com.example.skylers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylers.R;

public class FragmentCollections extends Fragment {

    Context context;
    View view;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = inflater.getContext();
        view =  inflater.inflate(R.layout.fragment_collect_payments, container, false);

        getViews(view);

        return view;
    }

    public void getViews(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}
