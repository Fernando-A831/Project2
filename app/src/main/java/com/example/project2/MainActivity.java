package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.User;
import com.example.project2.db.UserDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UserDao userDao = AppDatabase.getInstance(getApplicationContext()).userDao();
            if (userDao.getUserByUsername("testuser1") == null) {
                userDao.insert(new User("testuser1", "testuser1", false));
            }
            if (userDao.getUserByUsername("admin2") == null) {
                userDao.insert(new User("admin2", "admin2", true));
            }
        });

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("loggedIn", false);
        int userId = prefs.getInt("userId", -1);

        if(loggedIn && userId != -1) {
            startActivity(new Intent(MainActivity.this, LandingPageActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);

            Button loginButton = findViewById(R.id.loginButton);
            loginButton.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            });


        }
    }
}
