package com.example.elearningapp.lessonType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.ListAdapter;
import com.example.elearningapp.adapter.TestAdapter;
import com.example.elearningapp.courseItem.LessonDatabaseHelper;
import com.example.elearningapp.courseItem.LessonItem;
import com.example.elearningapp.courseItem.QuestionItem;

import java.io.Serializable;
import java.util.List;

public class testLesson extends AppCompatActivity {
    TextView title, result;
    Button submit, nxt, pre;

    List<QuestionItem> questionItemList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_test);


        title = findViewById(R.id.lesson_title2);
        submit = findViewById(R.id.submitBtn);
        nxt = findViewById(R.id.btn_next_lesson);
        pre = findViewById(R.id.btn_pre_lesson);

        setLesson();
    }

    void setLesson() {
        List<LessonItem> lessonItem = (List<LessonItem>) getIntent().getSerializableExtra("lesson");
        int position = getIntent().getIntExtra("position", 0);
        int maxPosition = getIntent().getIntExtra("maxPosition", 0);

        LessonDatabaseHelper helper = new LessonDatabaseHelper();
        RecyclerView recyclerView = findViewById(R.id.questionList);
        questionItemList = helper.getQuestionbyLessonId(getApplicationContext(), lessonItem.get(position).getLessonId());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TestAdapter(getApplicationContext(), questionItemList));


        if (position > 0) {
            pre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonOnClick(position - 1, maxPosition, lessonItem);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
        if (position != maxPosition - 1) {
            nxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonOnClick(position + 1, maxPosition, lessonItem);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }
    }


    void buttonOnClick(int position, int maxPosition, List<LessonItem> lessonItemList) {
        if (lessonItemList.get(position).getType().equals("video")) {
            Intent intent = new Intent(testLesson.this, videoLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            startActivity(intent);
        } else if (lessonItemList.get(position).getType().equals("text")) {
            Intent intent = new Intent(testLesson.this, textLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            startActivity(intent);
        } else if (lessonItemList.get(position).getType().equals("test")) {
            Intent intent = new Intent(testLesson.this, testLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            startActivity(intent);
        }
    }
}