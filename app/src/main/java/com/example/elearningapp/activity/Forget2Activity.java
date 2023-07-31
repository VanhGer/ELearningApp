package com.example.elearningapp.activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;


import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Forget2Activity extends AppCompatActivity {

    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget2);
        login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}