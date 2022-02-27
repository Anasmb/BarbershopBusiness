package com.example.barberbusiness;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

public class SalonFragment extends Fragment {

    LinearLayout barbersLayout ;
    LinearLayout serviceLayout ;
    LinearLayout timeLayout ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_salon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barbersLayout = view.findViewById(R.id.barbersLayout);
        barbersLayout.setOnClickListener(barbersLayoutClick);
        serviceLayout = view.findViewById(R.id.serviceLayout);
        serviceLayout.setOnClickListener(serviceLayoutClick);
        timeLayout = view.findViewById(R.id.timeLayout);
        timeLayout.setOnClickListener(timeLayoutClick);
    }


    View.OnClickListener barbersLayoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(),BarbersActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener serviceLayoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(),ServiceActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener timeLayoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(),TimeActivity.class);
            startActivity(intent);
        }
    };


}