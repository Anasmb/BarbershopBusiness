package com.example.barberbusiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddBarbershopActivity extends AppCompatActivity{

    private ImageView backBtn;
    private TextInputEditText shopName, email, password, phoneNumber, address;
    private MaterialButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barbershop);

        backBtn = findViewById(R.id.addBarbershopBackButton);
        backBtn.setOnClickListener(backBtnListener);
        addButton = findViewById(R.id.addBarbershopAddBtn);
        addButton.setOnClickListener(addClickListener);

        shopName = findViewById(R.id.addBarbershopNameEditText);
        email = findViewById(R.id.addBarbershopEmailEditText);
        password = findViewById(R.id.addBarbershopPasswordEditText);
        phoneNumber = findViewById(R.id.addBarbershopPhoneNumberEditText);
        address = findViewById(R.id.addBarbershopAddressEditText);
        address.setOnClickListener(addressClickListener);
    }



    private View.OnClickListener addressClickListener = new View.OnClickListener() { //open google map to select location
        @Override
        public void onClick(View view) {

        }
    };



    private View.OnClickListener backBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener addClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };


}