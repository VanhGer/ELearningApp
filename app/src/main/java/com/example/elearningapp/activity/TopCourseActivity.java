package com.example.elearningapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.elearningapp.interfaces.CourseClickHelper;
import com.example.elearningapp.interfaces.LessonClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.adapter.TopCourseAdapter;
import com.example.elearningapp.object.CourseListItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopCourseActivity extends AppCompatActivity implements CourseClickHelper {

    ImageButton backBtn;
    RecyclerView topCourseRecyclerView;

    List<CourseListItem> courseListItemList = new ArrayList<>();

    TopCourseAdapter topCourseAdapter;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_course);

        init();

        String title = getIntent().getStringExtra("title");

        TextView titleView = findViewById(R.id.yourCourse);
        titleView.setText(title);

        topCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topCourseAdapter = new TopCourseAdapter(this, courseListItemList, this);
        topCourseRecyclerView.setAdapter(topCourseAdapter);

        loadDataFromFirestore();
        backBtnClick();
    }


    private void init() {
        backBtn = findViewById(R.id.backBtnTopCourse);
        topCourseRecyclerView = findViewById(R.id.yourCourseRecycler);
    }

    private void loadDataFromFirestore() {
        CollectionReference categoryRef = FirebaseFirestore.getInstance().collection("courses");
        categoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                courseListItemList.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    courseListItemList.add(
                            new CourseListItem(
                                    document.getId(),
                                    document.getString("image"),
                                    document.getString("name"),
                                    document.getString("owner"),
                                    document.getString("description"),
                                    document.getDouble("students").intValue(),
                                    document.getDouble("star")));
                }

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        topCourseRecyclerView.setVisibility(View.VISIBLE);
                    }
                };
                handler.postDelayed(runnable, 1000);

                topCourseAdapter.notifyDataSetChanged();
            }
        });
    }

    private void backBtnClick() {
        if (backBtn == null) {
            return;
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(String id) {
        Intent overallActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
        overallActivity.putExtra("courseId", id);
        startActivity(overallActivity);
    }
}

