package com.example.barberbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.nio.BufferUnderflowException;

public class BarbershopMainActivity extends AppCompatActivity {

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SalonFragment()).commit();

        preferences = getSharedPreferences("UserPrefs" , Context.MODE_PRIVATE);

        getInfoFromDB(); //get info of barbershop
    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_salon:
                    selectedFragment = new SalonFragment();
                    break;
                case R.id.nav_appointment:
                    selectedFragment = new AppointmentFragment();
                    break;
                case R.id.nav_more:
                    selectedFragment = new MoreFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    private void getInfoFromDB(){ //get barbershop info from DB to store it locally

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    String[] field = new String[1];
                    field[0] = "PhoneNumber";

                    String[] data = new String[1];
                    data[0] = getIntent().getExtras().getString("phonenumber"); // get phone number from login activity

                    PutData putData = new PutData("http://192.168.100.6/barbershop-php/getBarbershopInfo.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            saveInfoLocally(result);
                            Log.d("php", result);
                        }
                    }

                }
            });

    }

    private void saveInfoLocally(String info){

        String column[] = info.split("\\|");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id" , column[0]);
        editor.putString("shopName" , column[1]);
        editor.putString("phoneNumber" , getIntent().getExtras().getString("phonenumber"));
        editor.putString("email" , column[2]);
        editor.putString("address" , column[3]);
        editor.putString("hours" , column[4]);
        editor.putString("image" , column[5]);
        editor.commit();

    }


}