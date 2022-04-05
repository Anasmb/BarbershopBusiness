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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop_login);

        phoneNumber = findViewById(R.id.loginPhoneNumberEditText);
        password = findViewById(R.id.loginPasswordEditText);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginListener);
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
                        String[] field = new String[2];
                        field[0] = "PhoneNumber";
                        field[1] = "Password";
                        String[] data = new String[2];
                        data[0] = String.valueOf(phoneNumber.getText());
                        data[1] = String.valueOf(password.getText());
                        Log.d("php" , data[0] + " " + data[1]);
                        PutData putData = new PutData("http://192.168.100.6/barbershop-php/loginBarbershop.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Login Success")){
                                    Log.d("php", result);
                                    Intent intent = new Intent(getApplicationContext(), BarbershopMainActivity.class);
                                    intent.putExtra("phonenumber" , phoneNumber.getText().toString()); //send phone number to next activity
                                    startActivity(intent);
                                    finish();
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

}