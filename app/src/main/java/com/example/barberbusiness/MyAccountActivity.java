package com.example.barberbusiness;

import androidx.annotation.Nullable;
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

public class MyAccountActivity extends AppCompatActivity {

    private ImageView backBtn;
    private MaterialButton saveButton;
    private TextInputEditText shopName , email, phoneNumber ,password, address;
    private String latitude, longitude, barberShopAddress;
    SharedPreferences preferences;

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
        email = findViewById(R.id.myAccountEmailEditText);
        phoneNumber = findViewById(R.id.myAccountNumberEditText);
        password = findViewById(R.id.myAccountPasswordEditText);
        address = findViewById(R.id.myAccountAddressEditText);
        address.setOnClickListener(addressClickListener);

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
            String result = data.getStringExtra("address");
            String coordinates[] = result.split("/");
            address.setText(coordinates[0]);
            latitude = coordinates[1];
            longitude = coordinates[2];
            barberShopAddress = coordinates[0] + "/" + latitude + "/" + longitude;
            Log.d("debug", "onActivityResult: latitude = " + latitude + "  longitude = " + longitude);

        }
    }

    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (shopName.length() > 0 && email.length() > 0 && phoneNumber.length() > 9 && password.length() > 7) {

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
                        data[2] = barberShopAddress;
                        data[3] = String.valueOf(phoneNumber.getText());
                        data[4] = String.valueOf(password.getText());
                        data[5] = preferences.getString("id", "");
                        PutData putData = new PutData("http://192.168.100.6/barbershop-php/updateBarbershopInfo.php", "POST", field, data);
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
        editor.putString("address" , barberShopAddress);
        editor.putString("phoneNumber",phoneNumber.getText().toString());
        editor.putString("password",password.getText().toString());
        editor.apply();
    }
}