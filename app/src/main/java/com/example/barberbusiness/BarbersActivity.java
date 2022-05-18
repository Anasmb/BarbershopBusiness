package com.example.barberbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.hardware.camera2.params.ColorSpaceTransform;
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
import com.example.barberbusiness.adapters.BarberAdapter;
import com.example.barberbusiness.adapters.ServiceAdapter;
import com.example.barberbusiness.items.BarberItem;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BarbersActivity extends AppCompatActivity {

    private String SQL_URL = "http://188.54.243.108/barbershop-php/barber/getBarbers.php";
    private ImageView addButton , backButton;
    private RecyclerView recyclerView;
    private BarberAdapter adapter;
    private List<BarberItem> barberItemList;
    private SharedPreferences preferences;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbers);

        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        barberItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.barbersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        addButton = findViewById(R.id.addBarberButton);
        addButton.setOnClickListener(addButtonClick);
        backButton = findViewById(R.id.barbersBackButton);
        backButton.setOnClickListener(backButtonClick);
        swipeRefreshLayout = findViewById(R.id.barberRefresh);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);

        SQL_URL += "?BarbershopID=" + preferences.getString("id","");

        loadBarbers();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback); //handle swiping the item in recycle View
        itemTouchHelper.attachToRecyclerView(recyclerView);

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

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            barberItemList.clear();
            adapter = new BarberAdapter(getApplicationContext(), barberItemList);
            recyclerView.setAdapter(adapter);
            loadBarbers();
            swipeRefreshLayout.setRefreshing(false);
        }
    };

   private void loadBarbers(){
       StringRequest stringRequest = new StringRequest(Request.Method.POST, SQL_URL, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONArray barbers = new JSONArray(response);
                   for (int i = 0 ; i < barbers.length() ; i++){
                       JSONObject barberObject = barbers.getJSONObject(i);
                       int barberID = barberObject.getInt("barberID");
                       String name = barberObject.getString("name");
                       String experience = barberObject.getString("experience");
                       String nationality = barberObject.getString("nationality");
                       barberItemList.add(new BarberItem(barberID,name,experience,nationality));
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
              deleteBarberFromDB(barberItemList.get(position).getBarberID());
              barberItemList.remove(position);
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
   };

   private void deleteBarberFromDB(int barberID){
       Handler handler = new Handler(Looper.getMainLooper());
       handler.post(new Runnable() {
           @Override
           public void run() {

               String[] field = new String[1];
               field[0] = "barberID";

               String[] data = new String[1];
               data[0] = String.valueOf(barberID);
               PutData putData = new PutData("http://188.54.243.108/barbershop-php/barber/deleteBarber.php", "POST", field, data);
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