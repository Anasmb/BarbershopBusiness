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

public class AdminLoginActivity extends AppCompatActivity {

    private TextInputEditText username, password;
    private MaterialButton loginButton;
    private TextView barbershopTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        username = findViewById(R.id.loginUserNameAdminEditText);
        password = findViewById(R.id.loginAdminPasswordEditText);

        loginButton = findViewById(R.id.loginAdminButton);
        loginButton.setOnClickListener(loginListener);
        barbershopTxt = findViewById(R.id.barbershopTextView);
        barbershopTxt.setOnClickListener(barbershopTextListener);
    }

   private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(username.getText().length() > 0 && password.getText().length() > 0) {
                //Start ProgressBar first (Set visibility VISIBLE)
                loginButton.setClickable(false);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "Username";
                        field[1] = "Password";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = String.valueOf(username.getText());
                        data[1] = String.valueOf(password.getText());
                        Log.d("php" , data[0] + " " + data[1]);
                        PutData putData = new PutData("http://192.168.100.6/barbershop-php/loginAdmin.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Login Success")){
                                    Log.d("php", result);
                                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                    startActivity(intent);
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

    private View.OnClickListener barbershopTextListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), BarbershopLoginActivity.class);
            finish();
            startActivity(intent);
        }
    };
}