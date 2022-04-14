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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barberbusiness.adapters.FeedbackAdapter;
import com.example.barberbusiness.items.FeedbackItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.BufferUnderflowException;

public class BarbershopMainActivity extends AppCompatActivity { //TODO FIX WHEN DATABASE NOT AVAILABLE OR WIFI IS OFF

    private String SQL_URL = "http://192.168.100.6/barbershop-php/barbershop/getBarbershopInfo.php";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        preferences = getSharedPreferences("UserPrefs" , Context.MODE_PRIVATE);

        SQL_URL += "?PhoneNumber=" + getIntent().getExtras().getString("phonenumber");;
        loadBarbershopInfo(); //get info of barbershop

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

    private void loadBarbershopInfo(){ //get barbershop info from DB to store it locally
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SQL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("php", response);
                try {
                    JSONArray columns = new JSONArray(response);
                    SharedPreferences.Editor editor = preferences.edit();
                    for (int i = 0 ; i < columns.length() ; i++){
                        JSONObject barbershopObject = columns.getJSONObject(i);
                        editor.putString("id" , barbershopObject.getString("BarbershopID"));
                        editor.putString("shopName" , barbershopObject.getString("ShopName"));
                        editor.putString("phoneNumber" , getIntent().getExtras().getString("phonenumber"));
                        editor.putString("email" , barbershopObject.getString("Email"));
                        editor.putString("address" , barbershopObject.getString("Address"));
                        editor.putString("hours" , barbershopObject.getString("Hours"));
                        editor.putString("image" , barbershopObject.getString("Image"));
                        editor.putString("password" , barbershopObject.getString("Password"));
                        editor.apply();
                        Log.d("debug", "columns index = " + i);
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SalonFragment()).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // this method will execute if there is error
                Log.d("php", "onErrorResponse: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Couldn't connect to server!", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }


}