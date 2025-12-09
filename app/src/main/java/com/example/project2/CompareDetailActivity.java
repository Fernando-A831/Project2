package com.example.project2;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CompareDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_detail);

        TextView txtInfo = findViewById(R.id.txtInfo);

        String username = getIntent().getStringExtra("username");
        txtInfo.setText("Selected user: " + username);
    }
}
