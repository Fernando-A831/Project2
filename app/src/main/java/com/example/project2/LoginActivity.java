package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.User;
import com.example.project2.db.UserDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private UserDao userDao;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        userDao = AppDatabase.getInstance(this).userDao();

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                User user = userDao.getUserByUsername(username);
                runOnUiThread(() -> {
                    if (user != null && user.getPassword().equals(password)) {
                        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("loggedIn", true);
                        editor.putString("username", user.getUsername());
                        editor.putBoolean("isAdmin", user.isAdmin());
                        editor.putInt("userId", user.getId());
                        editor.apply();

                        startActivity(LandingPageActivity.newIntent(LoginActivity.this));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }
}
