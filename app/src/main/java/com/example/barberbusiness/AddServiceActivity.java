package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddServiceActivity extends AppCompatActivity {

    private TextInputEditText serviceName, price;
    private MaterialButton saveButton;
    private SharedPreferences preferences;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        serviceName = findViewById(R.id.serviceNameEditText);
        price = findViewById(R.id.servicePriceEditText);

        saveButton = findViewById(R.id.addServiceSaveButton);
        saveButton.setOnClickListener(saveButtonListener);
        backButton = findViewById(R.id.addServiceBackButton);
        backButton.setOnClickListener(backButtonClick);
    }

    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Log.d("debug", "Add Service to barbershop with ID = " + preferences.getString("id",""));

            if (serviceName.length() > 0 && price != null) {

                saveButton.setClickable(false);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] field = new String[3];
                        field[0] = "Name";
                        field[1] = "Price";
                        field[2] = "BarbershopID";
                        //Creating array for data
                        String[] data = new String[3];
                        data[0] = String.valueOf(serviceName.getText());
                        data[1] = String.valueOf(price.getText());
                        data[2] = preferences.getString("id", "");
                        PutData putData = new PutData("http://192.168.100.6/barbershop-php/service/addService.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Add Success")) {
                                    Log.d("php", result);
                                    Toast.makeText(getApplicationContext(), "Service Added Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else { // All fields are required
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    Log.d("php", result);
                                    saveButton.setClickable(true);
                                }
                            }
                        }
                        //End Write and Read data with URL
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "All fields are required !", Toast.LENGTH_LONG).show();
            }
        }


    };

    View.OnClickListener backButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}