package com.example.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.db.User;

import java.util.ArrayList;
import java.util.List;

public class FriendComparisonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_comparison);

        recyclerView = findViewById(R.id.recyclerFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TEMP USER LIST until database is merged
        List<User> users = new ArrayList<>();
        users.add(new User("Fernando", "password", false));
        users.add(new User("Kanishka", "password", false));

        userAdapter = new UserAdapter(users, user -> {
            Intent intent = new Intent(FriendComparisonActivity.this, CompareDetailActivity.class);
            intent.putExtra("username", user.getUsername());
            startActivity(intent);
        });

        recyclerView.setAdapter(userAdapter);
    }
}
