package com.example.project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.db.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminLandingPageActivity extends AppCompatActivity {
    private Button viewAllUsersButton;
    private Button deleteAllWishlistsButton;
    private Button deleteAllTeamsButton;
    private Button viewDatabaseStatsButton;
    private Button logoutButton;
    private TextView welcomeText;
    private TextView statsText;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing_page);

        welcomeText = findViewById(R.id.adminWelcomeText);
        statsText = findViewById(R.id.adminStatsText);
        viewAllUsersButton = findViewById(R.id.viewAllUsersButton);
        deleteAllWishlistsButton = findViewById(R.id.deleteAllWishlistsButton);
        deleteAllTeamsButton = findViewById(R.id.deleteAllTeamsButton);
        viewDatabaseStatsButton = findViewById(R.id.viewDatabaseStatsButton);
        logoutButton = findViewById(R.id.adminLogoutButton);

        executorService = Executors.newSingleThreadExecutor();

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = prefs.getString("username", "Admin");
        welcomeText.setText("Admin Panel - Welcome, " + username + "!");

        viewDatabaseStatsButton.setOnClickListener(v -> loadDatabaseStats());

        deleteAllWishlistsButton.setOnClickListener(v -> deleteAllWishlists());

        deleteAllTeamsButton.setOnClickListener(v -> deleteAllTeams());

        viewAllUsersButton.setOnClickListener(v -> {
            Toast.makeText(this, "View All Users - Feature coming soon", Toast.LENGTH_SHORT).show();
        });

        logoutButton.setOnClickListener(v -> logout());

        loadDatabaseStats();
    }

    private void loadDatabaseStats() {
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            int userCount = db.userDao().getAllUsers().size();
            int wishlistCount = db.wishlistDao().getAll().size();
            int teamCount = db.teamDao().getAll().size();

            runOnUiThread(() -> {
                String stats = "Database Statistics:\n\n" +
                        "Total Users: " + userCount + "\n" +
                        "Total Wishlist Items: " + wishlistCount + "\n" +
                        "Total Team Members: " + teamCount;
                statsText.setText(stats);
            });
        });
    }

    private void deleteAllWishlists() {
        executorService.execute(() -> {
            AppDatabase.getInstance(this).wishlistDao().deleteAll();
            runOnUiThread(() -> {
                Toast.makeText(this, "All wishlists deleted", Toast.LENGTH_SHORT).show();
                loadDatabaseStats();
            });
        });
    }

    private void deleteAllTeams() {
        executorService.execute(() -> {
            AppDatabase.getInstance(this).teamDao().deleteAll();
            runOnUiThread(() -> {
                Toast.makeText(this, "All teams deleted", Toast.LENGTH_SHORT).show();
                loadDatabaseStats();
            });
        });
    }

    private void logout() {
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AdminLandingPageActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}