package com.example.elearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.elearningapp.courseItem.CourseOverallActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        CreateDatabase tmp = new CreateDatabase();
        tmp.create(MainActivity.this);
        ImageButton btn = findViewById(R.id.imageButton5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent overallActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
                startActivity(overallActivity);
            }
        });
    }
}