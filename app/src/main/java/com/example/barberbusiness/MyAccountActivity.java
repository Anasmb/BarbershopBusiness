package com.example.barberbusiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MyAccountActivity extends AppCompatActivity {

    private ImageView backBtn;
    private MaterialButton saveButton;
    private TextInputEditText shopName , email, phoneNumber ,password, address;
    private String latitude, longitude;
    private SharedPreferences preferences;
    private boolean isNameValid, isPhoneNumberValid , isEmailValid, isPasswordValid = false;
    private TextView phoneNumberWarning, emailWarning, passwordWarning, nameWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        backBtn = findViewById(R.id.myAccount_back_button);
        backBtn.setOnClickListener(backBtnListener);
        saveButton = findViewById(R.id.myAccountSaveButton);
        saveButton.setOnClickListener(saveButtonListener);

        shopName = findViewById(R.id.myAccountShopNameEditText);
        shopName.addTextChangedListener(nameInputCheck);
        email = findViewById(R.id.myAccountEmailEditText);
        email.addTextChangedListener(emailInputCheck);
        phoneNumber = findViewById(R.id.myAccountNumberEditText);
        phoneNumber.addTextChangedListener(phoneNumberInputCheck);
        password = findViewById(R.id.myAccountPasswordEditText);
        password.addTextChangedListener(passwordInputCheck);
        address = findViewById(R.id.myAccountAddressEditText);
        address.setOnClickListener(addressClickListener);

        phoneNumberWarning = findViewById(R.id.mobileNumberWarning);
        emailWarning = findViewById(R.id.emailWarning);
        passwordWarning = findViewById(R.id.passwordWarning);
        nameWarning = findViewById(R.id.nameWarning);

        loadAccountInformation();

    }

    private View.OnClickListener backBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener addressClickListener = new View.OnClickListener() { //open google map to select location
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivityForResult(intent,1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) { // get data from google map activity
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if(data != null){
                String result = data.getStringExtra("address");
                String coordinates[] = result.split("/");
                latitude = coordinates[1];
                longitude = coordinates[2];
                String barberShopAddress = coordinates[0] + "/" + latitude + "/" + longitude;
                address.setText(barberShopAddress);
                Log.d("debug", "onActivityResult: latitude = " + latitude + "  longitude = " + longitude);
            }
        }
    }

    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (isNameValid && isEmailValid && isPhoneNumberValid && isPasswordValid) {

                saveButton.setClickable(false);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] field = new String[6];
                        field[0] = "shopName";
                        field[1] = "email";
                        field[2] = "address";
                        field[3] = "phoneNumber";
                        field[4] = "password";
                        field[5] = "BarbershopID";
                        //Creating array for data
                        String[] data = new String[6];
                        data[0] = String.valueOf(shopName.getText());
                        data[1] = String.valueOf(email.getText());
                        data[2] = String.valueOf(address.getText());
                        data[3] = String.valueOf(phoneNumber.getText());
                        data[4] = String.valueOf(password.getText());
                        data[5] = String.valueOf(preferences.getString("id", ""));
                        PutData putData = new PutData("http://188.54.243.108/barbershop-php/barbershop/updateBarbershopInfo.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Update Success")) {
                                    Log.d("php", result);
                                    Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                                    saveUpdatedInfoLocally();
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

    private void loadAccountInformation(){
            shopName.setText(preferences.getString("shopName" , ""));
            email.setText(preferences.getString("email",""));
            phoneNumber.setText(preferences.getString("phoneNumber" , ""));
            address.setText(preferences.getString("address",""));
            password.setText(preferences.getString("password", ""));
    }

    private void saveUpdatedInfoLocally(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("shopName",shopName.getText().toString());
        editor.putString("email",email.getText().toString());
        editor.putString("address" , address.getText().toString());
        editor.putString("phoneNumber",phoneNumber.getText().toString());
        editor.putString("password",password.getText().toString());
        editor.apply();
    }

    private TextWatcher nameInputCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() < 1){
                nameWarning.setVisibility(View.VISIBLE);
                isNameValid = false;
            }
            else{
                isNameValid = true;
                nameWarning.setVisibility(View.GONE);
            }
        }
    };

    private TextWatcher emailInputCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            if(!Patterns.EMAIL_ADDRESS.matcher(editable.toString()).matches()){ // check the email format, is it valid or not
                emailWarning.setVisibility(View.VISIBLE);
                isEmailValid = false;
            }
            else {
                emailWarning.setVisibility(View.GONE);
                isEmailValid = true;
            }
        }
    };

    private TextWatcher phoneNumberInputCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() != 10){
                phoneNumberWarning.setVisibility(View.VISIBLE);
                isPhoneNumberValid = false;
            }
            else {
                phoneNumberWarning.setVisibility(View.GONE);
                isPhoneNumberValid = true;
            }
        }
    };

    private TextWatcher passwordInputCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.toString().length() < 8){
                passwordWarning.setVisibility(View.VISIBLE);
                isPasswordValid = false;
            }
            else {
                passwordWarning.setVisibility(View.GONE);
                isPasswordValid = true;
            }
        }
    };

}