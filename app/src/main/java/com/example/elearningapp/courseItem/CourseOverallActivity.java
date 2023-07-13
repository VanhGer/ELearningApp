package com.example.elearningapp.courseItem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;

public class CourseOverallActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course2);

        TextView lessonList_btn = findViewById(R.id.lesson_baihoc);
        lessonList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lessonActivity = new Intent(getApplicationContext(), CourseLessonsActivity.class);
                startActivity(lessonActivity);
            }
        });
    }
}
