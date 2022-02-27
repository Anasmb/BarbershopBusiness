package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TimeActivity extends AppCompatActivity implements View.OnClickListener {

    TimePickerDialog timePickerDialog;
    TextInputEditText sundayFrom,sundayTo, mondayFrom, mondayTo, tuesdayFrom, tuesdayTo,
            wednesdayFrom, wednesdayTo, thursdayFrom, thursdayTo, fridayFrom, fridayTo, saturdayFrom, saturdayTo;

    ImageView backButton;
    ImageView doneButton;

    int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        backButton = findViewById(R.id.timeBackButton);
        backButton.setOnClickListener(backButtonClick);
        doneButton = findViewById(R.id.timeActivityDoneButton);

        initializeEditTexts();

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

    private void timePickerToEditText(View view, int hour, int minute, String AM_PM){
        switch (view.getId()){
            case R.id.sundayFromEditText:
                sundayFrom.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.sundayToEditText:
                sundayTo.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.mondayFromEditText:
                mondayFrom.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.mondayToEditText:
                mondayTo.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.tuesdayFromEditText:
                tuesdayFrom.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.tuesdayToEditText:
                tuesdayTo.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.wednesdayFromEditText:
                wednesdayFrom.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.wednesdayToEditText:
                wednesdayTo.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.thursdayFromEditText:
                thursdayFrom.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.thursdayToEditText:
                thursdayTo.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.fridayFromEditText:
                fridayFrom.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.fridayToEditText:
                fridayTo.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.saturdayFromEditText:
               saturdayFrom.setText(hour + ":" + minute + AM_PM);
                break;
            case R.id.saturdayToEditText:
                saturdayTo.setText(hour + ":" + minute + AM_PM);
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


}