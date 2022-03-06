package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    private String SQL_URL = "http://192.168.100.6/barbershop-php/getService.php";
    private ImageView addButton;
    private ImageView backButton;
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    List<ServiceItem> serviceItemList;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SQL_URL += "?BarbershopID=" + preferences.getString("id","");

        serviceItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.serviceRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButton = findViewById(R.id.addServiceButton);
        addButton.setOnClickListener(addButtonClick);
        backButton = findViewById(R.id.serviceBackButton);
        backButton.setOnClickListener(backButtonClick);

        loadServices();
    }


    View.OnClickListener addButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AddServiceActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener backButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private void loadServices(){
        Log.d("debug", "load services from DB");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SQL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray services = new JSONArray(response);
                    for (int i = 0 ; i < services.length() ; i++){
                        JSONObject serviceObject = services.getJSONObject(i);
                        String name = serviceObject.getString("name");
                        String duration = serviceObject.getString("duration");
                        double price = serviceObject.getDouble("price");
                        ServiceItem serviceItem = new ServiceItem(name,duration,price);
                        serviceItemList.add(serviceItem);
                        Log.d("debug", "onResponse: Service Name = " + name + " Duration = " + duration + " Price = " + price);
                        Log.d("debug", "onResponse from list: Service Name = " + serviceItem.getServiceName() + " Duration = " + serviceItem.getDuration() + " Price = " + serviceItem.getPrice());
                    }

                    adapter = new ServiceAdapter(getApplicationContext(), serviceItemList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // this method will execute if there is error
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }


}