package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                // TODO Fernando: later check from DB
                // TODO Fernando: use userDao here
                // TODO Fernando: remove hardcoded users
                if(user.equals("testuser1") && pass.equals("testuser1")) {
                    loginSuccess(user, false);
                }
                else if(user.equals("admin2") && pass.equals("admin2")) {
                    loginSuccess(user, true);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginSuccess(String username, boolean isAdmin) {

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        prefs.edit()
                .putBoolean("loggedIn", true)
                .putString("username", username)
                .putBoolean("isAdmin", isAdmin)
                .apply();

        startActivity(new Intent(LoginActivity.this, LandingPageActivity.class));
        finish();
    }
}
