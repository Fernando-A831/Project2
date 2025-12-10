package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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

public class CompareDetailActivity extends AppCompatActivity {

    private static final String EXTRA_FRIEND_ID = "extra_friend_id";
    private static final String EXTRA_FRIEND_NAME = "extra_friend_name";

    public static Intent newIntent(Context context, int friendUserId, String friendName) {
        Intent intent = new Intent(context, CompareDetailActivity.class);
        intent.putExtra(EXTRA_FRIEND_ID, friendUserId);
        intent.putExtra(EXTRA_FRIEND_NAME, friendName);
        return intent;
    }

    private TextView txtInfo;
    private RecyclerView recyclerView;
    private WishlistAdapter wishlistAdapter;
    private final List<Pokemon> friendWishlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_detail);

        txtInfo = findViewById(R.id.txtInfo);
        recyclerView = findViewById(R.id.recyclerFriendWishlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wishlistAdapter = new WishlistAdapter(friendWishlist);
        recyclerView.setAdapter(wishlistAdapter);

        int friendUserId = getIntent().getIntExtra(EXTRA_FRIEND_ID, -1);
        String friendName = getIntent().getStringExtra(EXTRA_FRIEND_NAME);

        if (friendUserId == -1) {
            txtInfo.setText("No friend selected.");
            return;
        }

        if (friendName == null) {
            friendName = "Friend";
        }

        txtInfo.setText(friendName + "'s wishlist");

        loadFriendWishlist(friendUserId);
    }

    private void loadFriendWishlist(int friendUserId) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            PokemonDao dao = db.pokemonDao();

            List<Pokemon> wishlist = dao.getWishlistForUser(friendUserId);

            runOnUiThread(() -> {
                friendWishlist.clear();
                friendWishlist.addAll(wishlist);
                wishlistAdapter.notifyDataSetChanged();
            });
        });
    }
}
