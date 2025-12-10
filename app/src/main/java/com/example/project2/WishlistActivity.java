package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.pokemon.Pokemon;
import com.example.project2.db.pokemon.PokemonDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WishlistAdapter wishlistAdapter;
    private final List<Pokemon> wishlist = new ArrayList<>();

    public static Intent newIntent(Context context) {
        return new Intent(context, WishlistActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        recyclerView = findViewById(R.id.recyclerWishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wishlistAdapter = new WishlistAdapter(wishlist);
        recyclerView.setAdapter(wishlistAdapter);

        loadWishlistForCurrentUser();
    }

    private void loadWishlistForCurrentUser() {
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        if (userId == -1) {
            return;
        }

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            PokemonDao dao = db.pokemonDao();

            List<Pokemon> favorites = dao.getWishlistForUser(userId);

            runOnUiThread(() -> {
                wishlist.clear();
                wishlist.addAll(favorites);
                wishlistAdapter.notifyDataSetChanged();
            });
        });
    }
}

