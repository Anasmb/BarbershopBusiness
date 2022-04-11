package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barberbusiness.adapters.FeedbackAdapter;
import com.example.barberbusiness.items.FeedbackItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    private String SQL_URL = "http://192.168.100.6/barbershop-php/getFeedback.php";
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    private List<FeedbackItem> feedbackItemList;
    private String shopID;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        shopID = preferences.getString("id","");
        SQL_URL += "?barbershopID=" + shopID;

        feedbackItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.feedbackRecyclerView);
        recyclerView.setHasFixedSize(true);

        backBtn = findViewById(R.id.feedback_backButton);
        backBtn.setOnClickListener(backBtnListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        loadFeedback();
    }

    private View.OnClickListener backBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private void loadFeedback(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SQL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("php", response);
                try {
                    JSONArray feedbacks = new JSONArray(response);
                    for (int i = 0 ; i < feedbacks.length() ; i++){
                        JSONObject feedbackObject = feedbacks.getJSONObject(i);
                        String comment = feedbackObject.getString("comment");
                        int stars = feedbackObject.getInt("stars");
                        FeedbackItem feedbackItem = new FeedbackItem(comment,stars);
                        feedbackItemList.add(feedbackItem);
                    }

                    adapter = new FeedbackAdapter(getApplicationContext(), feedbackItemList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // this method will execute if there is error
                Log.d("php", "onErrorResponse: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}