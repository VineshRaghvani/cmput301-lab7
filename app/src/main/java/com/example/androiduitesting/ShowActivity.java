package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {
    public static final String EXTRA_CITY_NAME = "CITY_NAME";
    
    private TextView cityNameTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        cityNameTextView = findViewById(R.id.textView_cityName);
        backButton = findViewById(R.id.button_back);

        // Get the city name from the intent
        Intent intent = getIntent();
        String cityName = intent.getStringExtra(EXTRA_CITY_NAME);
        
        if (cityName != null) {
            cityNameTextView.setText(cityName);
        }

        // Set up the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to MainActivity
            }
        });
    }
}
