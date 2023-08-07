package com.example.elearningapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    ProgressBar progressBar;
    Button btnReceiveCertificate;
    String description;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        courseId = getIntent().getStringExtra("courseId");
        btnReceiveCertificate=findViewById(R.id.btnReceiveCertificate);
        recyclerView = findViewById(R.id.course_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        listAdapter = new ListAdapter(this, lessonItemList, this, userId);
        recyclerView.setAdapter(listAdapter);
        ImageButton lessonList_btn = findViewById(R.id.imageButton9);
        progressBar = findViewById(R.id.progressBar4);

        CollectionReference colRef = db.collection("courses").document(courseId).collection("lessons");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    description = document.getString("description");

                    lessonItemList.add(new LessonItem(document.getId(), courseId, document.getString("name"), document.getString("description"),
                            document.getString("type"), document.getString("image"), document.getString("script"),
                            document.getString("content"), document.getString("video"), document.getLong("time")));
                }
                FirebaseFirestore.getInstance().collection("users").document(userId)
                        .collection("learn").document(courseId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value.exists()) {
                                    progressBar.setMax(100);
                                    Long num = value.getLong("cnt");
                                    if (lessonItemList.size() > 0) {
                                        num = num * 100 / lessonItemList.size();
                                    } else {
                                        num = 0L;
                                    }
                                    Log.v("Course", Math.toIntExact(num) + "");
                                    TextView cc = findViewById(R.id.textView96);
                                    if (num < 100) {
                                        cc.setText("Quá trình học hiện tại (" + num + " %)");
                                    } else {
                                        cc.setText("Wow, giỏi quá! Chúc mừng bạn đã hoàn thành xong khóa học! Mau đi nhận chứng chỉ thôi nào!");
                                        btnReceiveCertificate.setVisibility(View.VISIBLE);
                                        btnReceiveCertificate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // Gửi dữ liệu tới hoạt động CertificateActivity
                                                Intent intent = new Intent(CourseLessonsActivity.this, CertificateActivity.class);
                                                intent.putExtra("courseName", description); // Thay bằng tên khóa học thích hợp
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                    progressBar.setProgress(Math.toIntExact(num));
                                }
                            }
                        });
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