package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.User;
import com.example.project2.db.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FriendComparisonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private final List<User> users = new ArrayList<>();

    public static Intent newIntent(Context context) {
        return new Intent(context, FriendComparisonActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_comparison);

        recyclerView = findViewById(R.id.recyclerFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(users, user -> {
            Intent intent = CompareDetailActivity.newIntent(
                    FriendComparisonActivity.this,
                    user.getId(),
                    user.getUsername()
            );
            startActivity(intent);
        });
        recyclerView.setAdapter(userAdapter);

        loadFriends();
    }

    private void loadFriends() {
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        int currentUserId = prefs.getInt("userId", -1);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            UserDao userDao = db.userDao();

            List<User> allUsers = userDao.getAllUsers();
            List<User> otherUsers = new ArrayList<>();
            for (User u : allUsers) {
                if (u.getId() != currentUserId) {
                    otherUsers.add(u);
                }
            }

            runOnUiThread(() -> {
                users.clear();
                users.addAll(otherUsers);
                userAdapter.notifyDataSetChanged();
            });
        });
    }
}
