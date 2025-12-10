package com.example.project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPageActivity extends AppCompatActivity {
    private Button randomPokemonButton;
    private Button searchPokemonButton;
    private Button wishlistButton;
    private Button teamBuilderButton;
    private Button logoutButton;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // Initialize views
        welcomeText = findViewById(R.id.welcomeText);
        randomPokemonButton = findViewById(R.id.randomPokemonButton);
        searchPokemonButton = findViewById(R.id.searchPokemonButton);
        wishlistButton = findViewById(R.id.wishlistButton);
        teamBuilderButton = findViewById(R.id.teamBuilderButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Get username from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("PokedexPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", "Trainer");
        welcomeText.setText("Welcome, " + username + "!");

        // Set button click listeners
        randomPokemonButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPageActivity.this, RandomPokemonActivity.class);
            startActivity(intent);
        });

        searchPokemonButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPageActivity.this, SearchPokemonActivity.class);
            startActivity(intent);
        });

        wishlistButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPageActivity.this, WishlistActivity.class);
            startActivity(intent);
        });

        teamBuilderButton.setOnClickListener(v -> {
            Intent intent = new Intent(LandingPageActivity.this, TeamBuilderActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> logout());
    }

    private void logout() {
        // Clear SharedPreferences
        SharedPreferences prefs = getSharedPreferences("PokedexPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        // Show toast
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Go back to MainActivity
        Intent intent = new Intent(LandingPageActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Disable back button on landing page
        // User must use logout to exit
    }
}