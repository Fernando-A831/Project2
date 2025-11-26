package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingPageActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnAdminArea, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnAdminArea = findViewById(R.id.btnAdminArea);
        btnLogout = findViewById(R.id.btnLogout);

        // TODO Fernando: load extra user info from DB later if needed
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);

        String username = prefs.getString("username", "User");
        boolean isAdmin = prefs.getBoolean("isAdmin", false);

        tvWelcome.setText("Welcome, " + username);

        if(isAdmin) {
            btnAdminArea.setVisibility(View.VISIBLE);
        }

        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            startActivity(new Intent(LandingPageActivity.this, LoginActivity.class));
            finish();
        });
    }
}
