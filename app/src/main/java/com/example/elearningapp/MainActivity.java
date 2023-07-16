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

import com.example.elearningapp.courseItem.CourseLessonsActivity;
import com.example.elearningapp.courseItem.CourseOverallActivity;
import com.example.elearningapp.courseItem.LessonDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
//        LessonDatabaseHelper ldb = new LessonDatabaseHelper();
//        ldb.createNewLesson(MainActivity.this, 1, "cach de dep trai",
//                "day la bai hoc huong dan cac ban cach de dep trai vjppro ultra mega luxury",
//                "video", R.drawable.lingz, "vjp lao", "",
//                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
//                0);

        Intent lessonActivity = new Intent(getApplicationContext(), CourseLessonsActivity.class);
        startActivity(lessonActivity);
    }
}