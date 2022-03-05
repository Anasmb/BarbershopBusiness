package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
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

public class BarbersActivity extends AppCompatActivity {


    private String BARBER_URL = "http://192.168.100.6/barbershop-php/getBarbers.php";
    private ImageView addButton;
    private ImageView backButton;
    private RecyclerView recyclerView;
    private BarberAdapter adapter;
    List<String> barberItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbers);

        barberItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.barbersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButton = findViewById(R.id.addBarberButton);
        addButton.setOnClickListener(addButtonClick);
        backButton = findViewById(R.id.barbersBackButton);
        backButton.setOnClickListener(backButtonClick);

        BARBER_URL += "?BarbershopID=1";

        loadBarbers();

    }

    private View.OnClickListener addButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AddBarberActivity.class);
            startActivity(intent);
        }
    };

   private View.OnClickListener backButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

   private void loadBarbers(){
       StringRequest stringRequest = new StringRequest(Request.Method.POST, BARBER_URL, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONArray barbers = new JSONArray(response);
                   for (int i = 0 ; i < barbers.length() ; i++){
                       JSONObject barberObject = barbers.getJSONObject(i);
                       String name = barberObject.getString("name");
                       barberItemList.add(name);
                       Log.d("debug", "onResponse: Barber Name = " + name);
                   }

                   adapter = new BarberAdapter(getApplicationContext(), barberItemList);
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