package com.example.barberbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barberbusiness.adapters.ServiceAdapter;
import com.example.barberbusiness.items.ServiceItem;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ServiceActivity extends AppCompatActivity {

    private String SQL_URL = "http://188.54.243.108/barbershop-php/service/getService.php";
    private ImageView addButton, backButton;
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    private List<ServiceItem> serviceItemList;
    private SharedPreferences preferences;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        addButton = findViewById(R.id.addServiceButton);
        addButton.setOnClickListener(addButtonClick);
        backButton = findViewById(R.id.serviceBackButton);
        backButton.setOnClickListener(backButtonClick);
        swipeRefreshLayout = findViewById(R.id.serviceRefresh);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);

        loadServices();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback); //handle swiping the item in recycle View
        itemTouchHelper.attachToRecyclerView(recyclerView);
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

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            serviceItemList.clear();
            adapter = new ServiceAdapter(getApplicationContext(), serviceItemList);
            recyclerView.setAdapter(adapter);
            loadServices();
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    private void loadServices(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SQL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("php", response);
                try {
                    JSONArray services = new JSONArray(response);
                    for (int i = 0 ; i < services.length() ; i++){
                        JSONObject serviceObject = services.getJSONObject(i);
                        int serviceID = serviceObject.getInt("serviceID");
                        String name = serviceObject.getString("name");
                        double price = serviceObject.getDouble("price");
                        ServiceItem serviceItem = new ServiceItem(serviceID,name,price);
                        serviceItemList.add(serviceItem);
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
                Log.d("debug", "onErrorResponse: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) { //handle swiping the item in recycle View
        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.LEFT) {
                int position = viewHolder.getAdapterPosition();
                deleteServiceFromDB(serviceItemList.get(position).getServiceID());
                serviceItemList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        }

        @Override
        public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.parseColor("#BB0000"))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };;

    private void deleteServiceFromDB(int id){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "serviceID";

                String[] data = new String[1];
                data[0] = String.valueOf(id);
                PutData putData = new PutData("http://188.54.243.108/barbershop-php/service/deleteService.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result.equals("Add Success")) {
                            Log.d("php", result);
                            finish();
                        } else { // All fields are required
                            Log.d("php", result);
                        }
                    }
                }
            }
        });
    }
}