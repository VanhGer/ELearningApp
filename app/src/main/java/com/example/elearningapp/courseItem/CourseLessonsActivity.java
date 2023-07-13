package com.example.elearningapp.courseItem;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;

import java.util.ArrayList;
import java.util.List;

public class CourseLessonsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        RecyclerView recyclerView = findViewById(R.id.lesson_list_view);
        List<LessonItem> lessonItemList = new ArrayList<LessonItem>();

        for (int i = 1; i <= 20; i++) {
            lessonItemList.add(new LessonItem("bai hoc hom nay se la ve cc nhe cac e",
                    "Cristiano Ronaldo dos Santos Aveiro GOIH ComM is a Portuguese professional footballer",
                    R.drawable.course1));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListAdapter(getApplicationContext(), lessonItemList));

        TextView lessonList_btn = findViewById(R.id.lesson_tongquan);
        lessonList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent overallActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
                startActivity(overallActivity);
            }
        });
    }
}
