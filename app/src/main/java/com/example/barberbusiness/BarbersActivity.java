package com.example.barberbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BarbersActivity extends AppCompatActivity {

    ImageView addButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbers);

        addButton = findViewById(R.id.addBarberButton);
        addButton.setOnClickListener(addButtonClick);
        backButton = findViewById(R.id.barbersBackButton);
        backButton.setOnClickListener(backButtonClick);
    }

    private View.OnClickListener addButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AddBarberActivity.class);
            startActivity(intent);
        }
    };

   private View.OnClickListener backButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

}