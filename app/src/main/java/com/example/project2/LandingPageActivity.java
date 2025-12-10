package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    private Button adminButton, logoutButton, randomPokemonButton, searchPokemonButton, wishlistButton, friendComparisonButton;
    private EditText searchPokemonEditText;

    public static Intent newIntent(Context context) {
        return new Intent(context, LandingPageActivity.class);
    }

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
        wishlistButton = findViewById(R.id.wishlistButton);
        friendComparisonButton = findViewById(R.id.friendComparisonButton);

        friendComparisonButton.setOnClickListener(v -> {
            startActivity(FriendComparisonActivity.newIntent(LandingPageActivity.this));
        });



        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = prefs.getString("username", "User");
        boolean isAdmin = prefs.getBoolean("isAdmin", false);

        welcomeTextView.setText("Welcome, " + username);

        if (isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
            adminButton.setOnClickListener(v -> {
                startActivity(AdminActivity.newIntent(LandingPageActivity.this));
            });
        } else {
            // Just in case, make sure it's hidden for non-admins
            adminButton.setVisibility(View.INVISIBLE);
        }

        // Random Pokémon
        randomPokemonButton.setOnClickListener(v -> {
            startActivity(RandomPokemonActivity.newIntent(LandingPageActivity.this));
        });

        searchPokemonButton.setOnClickListener(v -> {
            String name = searchPokemonEditText.getText().toString().trim().toLowerCase();

            if (name.isEmpty()) {
                searchPokemonEditText.setError("Please enter a Pokémon name");
                return;
            }

            startActivity(SearchPokemonActivity.newIntent(LandingPageActivity.this, name));
        });

        searchPokemonEditText.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE) {
                searchPokemonButton.performClick();
                return true;
            }
            return false;
        });

        wishlistButton.setOnClickListener(v -> {
            startActivity(WishlistActivity.newIntent(LandingPageActivity.this));
        });




        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivity(MainActivity.newIntent(LandingPageActivity.this));
            finish();
        });
    }
}
