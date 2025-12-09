package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LandingPageActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private Button adminButton, logoutButton, randomPokemonButton, searchPokemonButton;
    private Button pokemonByTypeButton, pokemonByRegionButton, generationSearchButton;
    private EditText searchPokemonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        adminButton = findViewById(R.id.adminButton);
        logoutButton = findViewById(R.id.logoutButton);
        randomPokemonButton = findViewById(R.id.randomPokemonButton);
        searchPokemonEditText = findViewById(R.id.searchPokemonEditText);
        searchPokemonButton = findViewById(R.id.searchPokemonButton);
        pokemonByTypeButton = findViewById(R.id.pokemonByTypeButton);
        pokemonByRegionButton = findViewById(R.id.pokemonByRegionButton);
        generationSearchButton = findViewById(R.id.generationSearchButton);

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = prefs.getString("username", "User");
        boolean isAdmin = prefs.getBoolean("isAdmin", false);

        welcomeTextView.setText("Welcome, " + username);

        if (isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
        }

        // Random Pokémon
        randomPokemonButton.setOnClickListener(v -> {
            startActivity(new Intent(LandingPageActivity.this, RandomPokemonActivity.class));
        });

        searchPokemonButton.setOnClickListener(v -> {
            String name = searchPokemonEditText.getText().toString().trim().toLowerCase();

            if (name.isEmpty()) {
                searchPokemonEditText.setError("Please enter a Pokémon name");
                return;
            }

            Intent intent = new Intent(LandingPageActivity.this, SearchPokemonActivity.class);
            intent.putExtra("pokemon_name", name);
            startActivity(intent);
        });

        searchPokemonEditText.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE) {
                searchPokemonButton.performClick();
                return true;
            }
            return false;
        });

        pokemonByTypeButton.setOnClickListener(v -> {
            startActivity(new Intent(LandingPageActivity.this, PokemonByTypeActivity.class));
        });

        pokemonByRegionButton.setOnClickListener(v -> {
            startActivity(new Intent(LandingPageActivity.this, PokemonByRegionActivity.class));
        });

        generationSearchButton.setOnClickListener(v -> {
            startActivity(new Intent(LandingPageActivity.this, GenerationSearchActivity.class));
        });

        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(LandingPageActivity.this, MainActivity.class));
            finish();
        });
    }
}
