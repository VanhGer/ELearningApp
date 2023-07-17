package com.example.elearningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CourseOverallActivity;

public class HomePageActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        ImageView profilePic = findViewById(R.id.profilePic);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ImageView mostViewedCourse1 = findViewById(R.id.mostViewedCourse2);
        mostViewedCourse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lessonActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
                startActivity(lessonActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
