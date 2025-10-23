package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem; // <-- Add this import
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull; // <-- Add this import
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Extract the string data from the Intent
        String cityName = intent.getStringExtra("CITY_NAME");

        // Find the TextView and set its text to the city name
        TextView cityNameTextView = findViewById(R.id.textView_cityName);
        cityNameTextView.setText(cityName);


        // Find the button by its ID
        Button backButton = findViewById(R.id.button_back);

        // Set an OnClickListener
        backButton.setOnClickListener(v -> {
            // Call finish() to close the current activity and go back
            finish();
        });
    }
}
