package com.example.project2;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.WishlistItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WishlistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WishlistAdapter adapter;
    private List<WishlistItem> wishlistItems;
    private AppDatabase database;
    private ExecutorService executorService;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        // Initialize views
        recyclerView = findViewById(R.id.wishlistRecyclerView);
        emptyTextView = findViewById(R.id.emptyWishlistText);

        // Initialize database and executor
        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        // Setup RecyclerView
        wishlistItems = new ArrayList<>();
        adapter = new WishlistAdapter(wishlistItems, this::onDeleteClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Load wishlist items
        loadWishlist();
    }

    private void loadWishlist() {
        executorService.execute(() -> {
            List<WishlistItem> items = database.wishlistDao().getAll();
            runOnUiThread(() -> {
                wishlistItems.clear();
                wishlistItems.addAll(items);
                adapter.notifyDataSetChanged();

                // Show/hide empty message
                if (wishlistItems.isEmpty()) {
                    emptyTextView.setVisibility(TextView.VISIBLE);
                    recyclerView.setVisibility(RecyclerView.GONE);
                } else {
                    emptyTextView.setVisibility(TextView.GONE);
                    recyclerView.setVisibility(RecyclerView.VISIBLE);
                }
            });
        });
    }

    private void onDeleteClick(WishlistItem item) {
        executorService.execute(() -> {
            database.wishlistDao().delete(item);
            runOnUiThread(() -> {
                Toast.makeText(this, item.getName() + " removed from wishlist", Toast.LENGTH_SHORT).show();
                loadWishlist();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}