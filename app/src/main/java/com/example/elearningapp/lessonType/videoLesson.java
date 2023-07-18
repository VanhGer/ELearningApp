package com.example.elearningapp.lessonType;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.elearningapp.R;
import com.example.elearningapp.courseItem.LessonItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class videoLesson extends AppCompatActivity {

    TextView title, script;
    Button nxt, pre;
    VideoView video;

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_video);

        title = findViewById(R.id.lesson_title2);
        script = findViewById(R.id.lesson_content);

        nxt = findViewById(R.id.btn_next_lesson);
        pre = findViewById(R.id.btn_pre_lesson);

        video = findViewById(R.id.lessonVideo);
        back = findViewById(R.id.return_btn);
        setLesson();
    }

    void setLesson() {
        List<LessonItem> lessonItem = (List<LessonItem>) getIntent().getSerializableExtra("lesson");
        int position = getIntent().getIntExtra("position", 0);
        int maxPosition = getIntent().getIntExtra("maxPosition", 0);

        title.setText(lessonItem.get(position).getName());
        script.setText(lessonItem.get(position).getScript());
        video.setVideoURI(Uri.parse(lessonItem.get(position).getVideo()));
        video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoLesson.this.onBackPressed();
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
    void buttonOnClick(int position, int maxPosition, List<LessonItem> lessonItemList) {
        if (lessonItemList.get(position).getType().equals("video")) {
            Intent intent = new Intent(videoLesson.this, videoLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            startActivity(intent);
            finish();
        } else if (lessonItemList.get(position).getType().equals("text")) {
            Intent intent = new Intent(videoLesson.this, textLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            startActivity(intent);
            finish();
        } else if (lessonItemList.get(position).getType().equals("test")) {
            Intent intent = new Intent(videoLesson.this, testLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            startActivity(intent);
            finish();
        }
    }
}