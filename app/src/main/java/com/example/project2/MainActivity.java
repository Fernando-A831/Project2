package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button adminButton, randomPokemonButton, searchPokemonButton,
            wishlistButton, teamBuilderButton, logoutButton;

    EditText searchPokemonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adminButton = findViewById(R.id.adminButton);
        randomPokemonButton = findViewById(R.id.randomPokemonButton);
        searchPokemonEditText = findViewById(R.id.searchPokemonEditText);
        searchPokemonButton = findViewById(R.id.searchPokemonButton);
        wishlistButton = findViewById(R.id.wishlistButton);
        teamBuilderButton = findViewById(R.id.teamBuilderButton);
        logoutButton = findViewById(R.id.logoutButton);

        randomPokemonButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RandomPokemonActivity.class)));

        searchPokemonButton.setOnClickListener(v -> {
            String name = searchPokemonEditText.getText().toString().trim();
            if (!name.isEmpty()) {
                Intent i = new Intent(MainActivity.this, SearchPokemonActivity.class);
                i.putExtra("pokemonName", name);
                startActivity(i);
            }
        });

        wishlistButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, WishlistActivity.class)));

        teamBuilderButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TeamBuilderActivity.class)));

        logoutButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });

        adminButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AdminLandingPageActivity.class)));
    }
}
