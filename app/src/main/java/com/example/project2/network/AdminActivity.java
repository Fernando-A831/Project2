package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.User;
import com.example.project2.db.UserDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdminActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox adminCheckBox;
    private Button createUserButton;

    private UserDao userDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public static Intent newIntent(Context context) {
        return new Intent(context, AdminActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        adminCheckBox = findViewById(R.id.adminCheckBox);
        createUserButton = findViewById(R.id.createUserButton);

        userDao = AppDatabase.getInstance(getApplicationContext()).userDao();

        createUserButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            boolean isAdmin = adminCheckBox.isChecked();

            if (username.isEmpty()) {
                usernameEditText.setError("Username required");
                return;
            }

            if (password.isEmpty()) {
                passwordEditText.setError("Password required");
                return;
            }

            executor.execute(() -> {
                User existing = userDao.getUserByUsername(username);
                if (existing != null) {
                    runOnUiThread(() ->
                            Toast.makeText(AdminActivity.this, "User already exists", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    userDao.insert(new User(username, password, isAdmin));
                    runOnUiThread(() -> {
                        Toast.makeText(AdminActivity.this, "User created", Toast.LENGTH_SHORT).show();
                        usernameEditText.setText("");
                        passwordEditText.setText("");
                        adminCheckBox.setChecked(false);
                    });
                }
            });
        });
    }
}
