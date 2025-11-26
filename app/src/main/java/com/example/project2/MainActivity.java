package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("loggedIn", false);

        if(loggedIn) {
            // user already logged in → skip login
            startActivity(new Intent(MainActivity.this, LandingPageActivity.class));
        } else {
            // user not logged in → go to login screen
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        finish();
    }
}
