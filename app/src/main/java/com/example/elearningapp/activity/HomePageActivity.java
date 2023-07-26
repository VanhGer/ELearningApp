package com.example.elearningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CourseOverallActivity;
import com.example.elearningapp.fragment.ProfileFragment;

public class HomePageActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        TextView greeting1 = findViewById(R.id.greeting1);

        ImageView profilePic = findViewById(R.id.profilePic);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewProfile = new Intent(getApplicationContext(), ProfileFragment.class);
                startActivity(viewProfile);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView mostViewedCourse1 = findViewById(R.id.mostViewedCourse1);
        mostViewedCourse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lessonActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
                startActivity(lessonActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView mostViewedCourse2 = findViewById(R.id.mostViewedCourse2);
    }
}
