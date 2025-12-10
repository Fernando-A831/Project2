package com.example.project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPageActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private Button adminButton;
    private Button randomPokemonButton;
    private Button searchPokemonButton;
    private Button pokemonByTypeButton;
    private Button pokemonByRegionButton;
    private Button generationSearchButton;
    private Button wishlistButton;
    private Button teamBuilderButton;
    private Button logoutButton;
    private EditText searchPokemonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        adminButton = findViewById(R.id.adminButton);
        randomPokemonButton = findViewById(R.id.randomPokemonButton);
        searchPokemonEditText = findViewById(R.id.searchPokemonEditText);
        searchPokemonButton = findViewById(R.id.searchPokemonButton);
        pokemonByTypeButton = findViewById(R.id.pokemonByTypeButton);
        pokemonByRegionButton = findViewById(R.id.pokemonByRegionButton);
        generationSearchButton = findViewById(R.id.generationSearchButton);
        logoutButton = findViewById(R.id.logoutButton);
        wishlistButton = findViewById(R.id.wishlistButton);
        teamBuilderButton = findViewById(R.id.teamBuilderButton);

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = prefs.getString("username", "User");
        boolean isAdmin = prefs.getBoolean("isAdmin", false);

        welcomeTextView.setText("Welcome, " + username);

        if (isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
        }

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

       /** pokemonByTypeButton.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, PokemonByTypeActivity.class)));

        pokemonByRegionButton.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, PokemonByRegionActivity.class)));

        generationSearchButton.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, GenerationSearchActivity.class)));
*/
        wishlistButton.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, WishlistActivity.class)));

        teamBuilderButton.setOnClickListener(v ->
                startActivity(new Intent(LandingPageActivity.this, TeamBuilderActivity.class)));

        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LandingPageActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
    }
}
