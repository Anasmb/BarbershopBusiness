package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeActivity extends AppCompatActivity implements View.OnClickListener {

    private TimePickerDialog timePickerDialog;
    private TextInputEditText sundayFrom,sundayTo, mondayFrom, mondayTo, tuesdayFrom, tuesdayTo,
            wednesdayFrom, wednesdayTo, thursdayFrom, thursdayTo, fridayFrom, fridayTo, saturdayFrom, saturdayTo;

    private MaterialCheckBox sundayCheckBox, mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox,
            thursdayCheckBox, fridayCheckBox, saturdayCheckBox;

    private String openingHours;

    private ImageView backButton;
    private ImageView doneButton;

    private SharedPreferences preferences;
    int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        backButton = findViewById(R.id.timeBackButton);
        backButton.setOnClickListener(backButtonClick);
        doneButton = findViewById(R.id.timeActivityDoneButton);
        doneButton.setOnClickListener(doneButtonClick);

        initializeEditTexts();
        initializeCheckBoxes();
        loadHours();

    }

    @Override
    public void onClick(View view) { // clickListener for all days editText
        timePickerDialog = new TimePickerDialog(TimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String AM_PM ;
                hour = hourOfDay;
                if(hour > 12) {
                    hour -= 12;
                    AM_PM = "PM";
                } else if(hour == 0) {
                    hour += 12;
                    AM_PM = "AM";
                }
                else if(hour == 12){
                    AM_PM = "PM";
                }
                else {
                    AM_PM = "AM";
                }

               timePickerToEditText(view,  hour, minute , AM_PM);

            }
        },12,0,false);

        timePickerDialog.show();
    }


    private View.OnClickListener backButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener doneButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            organizeEditText();

            openingHours = sundayFrom.getText().toString() + "," + sundayTo.getText().toString() + "," +
                            mondayFrom.getText().toString() + "," + mondayTo.getText().toString() + "," +
                            tuesdayFrom.getText().toString() + "," + tuesdayTo.getText().toString() + "," +
                            wednesdayFrom.getText().toString() + "," + wednesdayTo.getText().toString() + "," +
                            thursdayFrom.getText().toString() + "," + thursdayTo.getText().toString() + "," +
                            fridayFrom.getText().toString() + "," + fridayTo.getText().toString() + "," +
                            saturdayFrom.getText().toString() + "," + saturdayTo.getText().toString() ;

            saveOpeningHoursToDB(openingHours);
        }
    };

    private void saveOpeningHoursToDB(String openingHours){

        if (checkAllFields()) {

            doneButton.setClickable(false);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    String[] field = new String[2];
                    field[0] = "BarbershopID";
                    field[1] = "Hours";
                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = preferences.getString("id","");
                    data[1] = openingHours;
                    PutData putData = new PutData("http://192.168.100.6/barbershop-php/hoursAPI.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Add Success")) {
                                Log.d("php", result);
                                Toast.makeText(getApplicationContext(), "Hours Added Successfully", Toast.LENGTH_SHORT).show();
                                preferences.edit().putString("hours", openingHours).apply();
                                finish();
                            } else { // All fields are required
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                Log.d("php", result);
                                doneButton.setClickable(true);
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

    private void timePickerToEditText(View view, int hour, int minute, String AM_PM){
        switch (view.getId()){
            case R.id.sundayFromEditText:
                sundayFrom.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.sundayToEditText:
                sundayTo.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.mondayFromEditText:
                mondayFrom.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.mondayToEditText:
                mondayTo.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.tuesdayFromEditText:
                tuesdayFrom.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.tuesdayToEditText:
                tuesdayTo.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.wednesdayFromEditText:
                wednesdayFrom.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.wednesdayToEditText:
                wednesdayTo.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.thursdayFromEditText:
                thursdayFrom.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.thursdayToEditText:
                thursdayTo.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.fridayFromEditText:
                fridayFrom.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.fridayToEditText:
                fridayTo.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.saturdayFromEditText:
               saturdayFrom.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;
            case R.id.saturdayToEditText:
                saturdayTo.setText(String.format("%02d:%02d" , hour , minute) + " " + AM_PM);
                break;

        }
    }

    private void initializeEditTexts(){
        sundayFrom = findViewById(R.id.sundayFromEditText);
        sundayFrom.setOnClickListener(this);
        sundayTo = findViewById(R.id.sundayToEditText);
        sundayTo.setOnClickListener(this);
        mondayFrom = findViewById(R.id.mondayFromEditText);
        mondayFrom.setOnClickListener(this);
        mondayTo = findViewById(R.id.mondayToEditText);
        mondayTo.setOnClickListener(this);
        tuesdayFrom = findViewById(R.id.tuesdayFromEditText);
        tuesdayFrom.setOnClickListener(this);
        tuesdayTo = findViewById(R.id.tuesdayToEditText);
        tuesdayTo.setOnClickListener(this);
        wednesdayFrom = findViewById(R.id.wednesdayFromEditText);
        wednesdayFrom.setOnClickListener(this);
        wednesdayTo = findViewById(R.id.wednesdayToEditText);
        wednesdayTo.setOnClickListener(this);
        thursdayFrom = findViewById(R.id.thursdayFromEditText);
        thursdayFrom.setOnClickListener(this);
        thursdayTo = findViewById(R.id.thursdayToEditText);
        thursdayTo.setOnClickListener(this);
        fridayFrom = findViewById(R.id.fridayFromEditText);
        fridayFrom.setOnClickListener(this);
        fridayTo = findViewById(R.id.fridayToEditText);
        fridayTo.setOnClickListener(this);
        saturdayFrom = findViewById(R.id.saturdayFromEditText);
        saturdayFrom.setOnClickListener(this);
        saturdayTo = findViewById(R.id.saturdayToEditText);
        saturdayTo.setOnClickListener(this);
    }

    private void initializeCheckBoxes(){
        sundayCheckBox = findViewById(R.id.sundayCheckBox);
        mondayCheckBox = findViewById(R.id.mondayCheckBox);
        tuesdayCheckBox = findViewById(R.id.tuesdayCheckBox);
        wednesdayCheckBox = findViewById(R.id.wednesdayCheckBox);
        thursdayCheckBox = findViewById(R.id.thursdayCheckBox);
        fridayCheckBox = findViewById(R.id.fridayCheckBox);
        saturdayCheckBox = findViewById(R.id.saturdayCheckBox);
    }

    private void organizeEditText(){
        if(sundayCheckBox.isChecked()){
            sundayFrom.setText("Closed"); sundayTo.setText("Closed");
        }
        if(mondayCheckBox.isChecked()){
            mondayFrom.setText("Closed"); mondayTo.setText("Closed");
        }
        if(tuesdayCheckBox.isChecked()){
            tuesdayFrom.setText("Closed"); tuesdayTo.setText("Closed");
        }
        if(wednesdayCheckBox.isChecked()){
            wednesdayFrom.setText("Closed"); wednesdayTo.setText("Closed");
        }
        if(thursdayCheckBox.isChecked()){
            thursdayFrom.setText("Closed"); thursdayTo.setText("Closed");
        }
        if(fridayCheckBox.isChecked()){
            fridayFrom.setText("Closed"); fridayTo.setText("Closed");
        }
        if(saturdayCheckBox.isChecked()){
            saturdayFrom.setText("Closed"); saturdayTo.setText("Closed");
        }
    }

    private boolean checkAllFields(){
        if(sundayFrom.length() > 2 && sundayTo.length() > 2 && mondayFrom.length() > 2 && mondayTo.length() > 2 && tuesdayFrom.length() > 2 && tuesdayTo.length() > 2 && wednesdayFrom.length() > 2
                && wednesdayTo.length() > 2 && thursdayFrom.length() > 2 && thursdayTo.length() > 2 && fridayFrom.length() > 2 && fridayTo.length() > 2 && saturdayFrom.length() > 2 && saturdayTo.length() > 2){
            return true;
        }
        return false;
    }

    private void loadHours(){
       String hours[] = preferences.getString("hours","").split(",");
       sundayFrom.setText(hours[0]); sundayTo.setText(hours[1]);
       mondayFrom.setText(hours[2]); mondayTo.setText(hours[3]);
       tuesdayFrom.setText(hours[4]); tuesdayTo.setText(hours[5]);
       wednesdayFrom.setText(hours[6]); wednesdayTo.setText(hours[7]);
       thursdayFrom.setText(hours[8]); thursdayTo.setText(hours[9]);
       fridayFrom.setText(hours[10]); fridayTo.setText(hours[11]);
       saturdayFrom.setText(hours[12]); saturdayTo.setText(hours[13]);
    }


}