package com.example.elearningapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.interfaces.LessonClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.adapter.ListAdapter;
import com.example.elearningapp.item.LessonItem;
import com.example.elearningapp.lessonType.testLesson;
import com.example.elearningapp.lessonType.textLesson;
import com.example.elearningapp.lessonType.videoLesson;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseLessonsActivity extends AppCompatActivity implements LessonClickHelper {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String courseId;
    List<LessonItem> lessonItemList = new ArrayList<>();

    ListAdapter listAdapter;
    RecyclerView recyclerView;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        courseId = getIntent().getStringExtra("courseId");
        recyclerView = findViewById(R.id.course_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        listAdapter = new ListAdapter(this, lessonItemList, this, userId);
        recyclerView.setAdapter(listAdapter);
        TextView lessonList_btn = findViewById(R.id.lesson_tongquan);

        CollectionReference colRef = db.collection("courses").document(courseId).collection("lessons");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    lessonItemList.add(new LessonItem(document.getId(), courseId, document.getString("name"), document.getString("description"),
                            document.getString("type"), document.getString("image"), document.getString("script"),
                            document.getString("content"), document.getString("video")));
                }
                listAdapter.notifyDataSetChanged();
            }
        });
        lessonList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent overallActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
                overallActivity.putExtra("courseId", courseId);
                startActivity(overallActivity);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }



    @Override
    public void onItemClick(int position) {
        if (lessonItemList.get(position).getType().equals("video")) {
            Intent intent = new Intent(CourseLessonsActivity.this, videoLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", lessonItemList.size());
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        } else if (lessonItemList.get(position).getType().equals("text")) {
            Intent intent = new Intent(CourseLessonsActivity.this, textLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", lessonItemList.size());
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        } else if (lessonItemList.get(position).getType().equals("test")) {
            Intent intent = new Intent(CourseLessonsActivity.this, testLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", lessonItemList.size());
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }
    }
}