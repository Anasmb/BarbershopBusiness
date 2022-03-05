package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AdminMainActivity extends AppCompatActivity {

    private LinearLayout addBarbershopLayout, manageBarbershopLayout, manageAppointmentLayout, manageOffersLayout, logoutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        addBarbershopLayout = findViewById(R.id.addBarbershopLayout);
        addBarbershopLayout.setOnClickListener(addBarbershopLayoutListener);
        manageBarbershopLayout = findViewById(R.id.manageBarbershopLayout);
        manageAppointmentLayout = findViewById(R.id.manageAppointmentLayout);
        manageOffersLayout = findViewById(R.id.manageOffersLayout);
        logoutLayout = findViewById(R.id.adminLogoutLayout);
        logoutLayout.setOnClickListener(logoutLayoutListener);
    }

    private View.OnClickListener addBarbershopLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext() , AddBarbershopActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener logoutLayoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
            startActivity(intent);
            finish();
        }
    };
}