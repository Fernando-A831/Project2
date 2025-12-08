package com.example.project2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.data.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WishlistAdapter wishlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        recyclerView = findViewById(R.id.recyclerWishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TEMP POKEMON LIST until DB merge
        List<Pokemon> wishlist = new ArrayList<>();
        wishlist.add(new Pokemon(25, "Pikachu", 4, 60, 1));
        wishlist.add(new Pokemon(4, "Charmander", 6, 85, 1));
        wishlist.add(new Pokemon(7, "Squirtle", 5, 90, 1));

        wishlistAdapter = new WishlistAdapter(wishlist);
        recyclerView.setAdapter(wishlistAdapter);
    }
}
