package com.example.elearningapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.ClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.adapter.ListAdapter;
import com.example.elearningapp.courseItem.LessonDatabaseHelper;
import com.example.elearningapp.courseItem.LessonItem;
import com.example.elearningapp.lessonType.textLesson;
import com.example.elearningapp.lessonType.videoLesson;

import java.util.List;

public class CourseLessonsActivity extends AppCompatActivity implements ClickHelper {

    List<LessonItem> lessonItemList;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        LessonDatabaseHelper helper = new LessonDatabaseHelper();
        RecyclerView recyclerView = findViewById(R.id.lesson_list_view);
        lessonItemList = helper.getListLessonByCourseId(getApplicationContext(), 1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListAdapter(getApplicationContext(), lessonItemList, this));

        TextView lessonList_btn = findViewById(R.id.lesson_tongquan);
        lessonList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent overallActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
                startActivity(overallActivity);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        if (lessonItemList.get(position).getType().equals("video")) {
            Intent intent = new Intent(CourseLessonsActivity.this, videoLesson.class);
            startActivity(intent);
        } else if (lessonItemList.get(position).getType().equals("text")) {
            Intent intent = new Intent(CourseLessonsActivity.this, textLesson.class);
            startActivity(intent);
        }
    }
}