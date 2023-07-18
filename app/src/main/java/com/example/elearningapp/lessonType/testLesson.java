package com.example.elearningapp.lessonType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.AnswerAdapter;
import com.example.elearningapp.adapter.ListAdapter;
import com.example.elearningapp.adapter.TestAdapter;
import com.example.elearningapp.courseItem.LessonDatabaseHelper;
import com.example.elearningapp.courseItem.LessonItem;
import com.example.elearningapp.courseItem.QuestionItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class testLesson extends AppCompatActivity {
    TextView title, result;
    Button submit, nxt, pre;

    RecyclerView recyclerView;

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
        result = findViewById(R.id.result);
        recyclerView = findViewById(R.id.questionList);
        setLesson();
    }

    void setLesson() {
        List<LessonItem> lessonItem = (List<LessonItem>) getIntent().getSerializableExtra("lesson");
        int position = getIntent().getIntExtra("position", 0);
        int maxPosition = getIntent().getIntExtra("maxPosition", 0);

        LessonDatabaseHelper helper = new LessonDatabaseHelper();
        questionItemList = helper.getQuestionbyLessonId(getApplicationContext(), lessonItem.get(position).getLessonId());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TestAdapter(getApplicationContext(), questionItemList));
        title.setText(lessonItem.get(position).getName());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Boolean> ansList = getResult();

                result.setVisibility(View.VISIBLE);
                pre.setVisibility(View.VISIBLE);
                nxt.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new AnswerAdapter(getApplicationContext(), questionItemList, ansList));
            }
        });
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

    List<Boolean> getResult() {
        int total = questionItemList.size();
        int correct = 0;
        List<Boolean> ansList = new ArrayList<Boolean>();
        for(int i =0; i < recyclerView.getAdapter().getItemCount();i++){
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if(holder == null){
                TestAdapter tmp = (TestAdapter) recyclerView.getAdapter();
                holder = tmp.getHolder(i);
            }
            if (holder == null) {
                ansList.add(false);
                continue;
            }
            TestAdapter.TestViewHolder testHolder = (TestAdapter.TestViewHolder) holder;
            int selected = testHolder.radioGroup.getCheckedRadioButtonId();
            if (selected == testHolder.choice1.getId()) selected = 1;
            else if (selected == testHolder.choice2.getId()) selected = 2;
            else if (selected == testHolder.choice3.getId()) selected = 3;
            else if (selected == testHolder.choice4.getId()) selected = 4;

            int ans = questionItemList.get(i).getAnswerId();
            if (ans == selected) {
                correct++;
                ansList.add(true);
            } else {
                ansList.add(false);
            }
        }

        String res = "Kết quả: " + correct + "/" + total;
        result.setText(res);
        return ansList;
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