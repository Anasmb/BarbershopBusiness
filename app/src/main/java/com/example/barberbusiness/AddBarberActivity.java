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

public class AddBarberActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextInputEditText barberName, barberExperience, barberNationality;
    private MaterialButton saveButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barber);
        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        viewsInitialization();
    }

    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

               if (barberName.length() > 0 && barberExperience.length() > 0 && barberNationality.length() > 0) {
                   saveButton.setClickable(false);
                   Handler handler = new Handler(Looper.getMainLooper());
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           String[] field = new String[4];
                           field[0] = "Name";
                           field[1] = "Experience";
                           field[2] = "Nationality";
                           field[3] = "BarbershopID";

                           String[] data = new String[4];
                           data[0] = String.valueOf(barberName.getText());
                           data[1] = String.valueOf(barberExperience.getText().toString());
                           data[2] = String.valueOf(barberNationality.getText());
                           data[3] = preferences.getString("id", "");
                           PutData putData = new PutData("http://188.54.243.108/barbershop-php/barber/addBarber.php", "POST", field, data);
                           if (putData.startPut()) {
                               if (putData.onComplete()) {
                                   String result = putData.getResult();
                                   if (result.equals("Add Success")) {
                                       Log.d("php", result);
                                       Toast.makeText(getApplicationContext(), "Barber Added Successfully", Toast.LENGTH_SHORT).show();
                                       finish();
                                   } else { // All fields are required
                                       Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                       Log.d("php", result);
                                       saveButton.setClickable(true);
                                   }
                               }
                           }
                       }
                   });
               } else {
                   Toast.makeText(getApplicationContext(), "All fields are required !", Toast.LENGTH_SHORT).show();
               }
           }
    };

    View.OnClickListener backButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private void viewsInitialization(){
        barberName = findViewById(R.id.barberNameEditText);
        barberExperience = findViewById(R.id.barberExperienceEditText);
        barberNationality = findViewById(R.id.barberNationalityEditText);
        backButton = findViewById(R.id.addBarbersBackButton);
        backButton.setOnClickListener(backButtonClick);
        saveButton = findViewById(R.id.addBarberSaveButton);
        saveButton.setOnClickListener(saveButtonListener);
    }
}