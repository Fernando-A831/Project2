package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingPageActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private Button adminButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        adminButton = findViewById(R.id.adminButton);
        logoutButton = findViewById(R.id.logoutButton);

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);

        String username = prefs.getString("username", "User");
        boolean isAdmin = prefs.getBoolean("isAdmin", false);

        welcomeTextView.setText("Welcome, " + username);

        if(isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
        }

        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(LandingPageActivity.this, MainActivity.class));
            finish();
        });
    }
}
