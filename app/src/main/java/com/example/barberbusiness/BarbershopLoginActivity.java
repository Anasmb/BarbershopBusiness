package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class BarbershopLoginActivity extends AppCompatActivity {

    private TextInputEditText phoneNumber, password;
    private MaterialButton loginButton;
    private TextView adminTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop_login);

        phoneNumber = findViewById(R.id.loginPhoneNumberEditText);
        password = findViewById(R.id.loginPasswordEditText);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginListener);
        adminTxt = findViewById(R.id.adminTextView);
        adminTxt.setOnClickListener(adminTextListener);
    }

    View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(phoneNumber.getText().length() > 0 && password.getText().length() > 0) {
                //Start ProgressBar first (Set visibility VISIBLE)
                loginButton.setClickable(false);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "phonenumber";
                        field[1] = "password";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = String.valueOf(phoneNumber.getText());
                        data[1] = String.valueOf(password.getText());
                        Log.d("php" , data[0] + " " + data[1]);
                        PutData putData = new PutData("http://192.168.100.6/barbershop-php/login.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Login Success")){
                                    Log.d("php", result);
                                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                    Log.d("php", result);
                                    loginButton.setClickable(true);
                                }
                            }
                        }
                        //End Write and Read data with URL
                    }
                });
            }

        }
    };

    View.OnClickListener adminTextListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
            finish();
            startActivity(intent);

        }
    };
}