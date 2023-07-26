package com.example.elearningapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.elearningapp.ClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.adapter.TopCourseAdapter;
import com.example.elearningapp.object.CourseListItem;
import com.example.elearningapp.object.CourseObject;
import com.example.elearningapp.object.PopularCategoryItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopCourseActivity extends AppCompatActivity {

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

        TextView titleView = findViewById(R.id.topCourseTitle);
        titleView.setText(title);

        topCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topCourseAdapter = new TopCourseAdapter(getApplicationContext(), courseListItemList);
        topCourseRecyclerView.setAdapter(topCourseAdapter);

        loadDataFromFirestore();
        backBtnClick();
    }


    private void init() {
        backBtn = findViewById(R.id.backBtnTopCourse);
        topCourseRecyclerView = findViewById(R.id.topCourseRecycler);
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
                                    document.getString("image"),
                                    document.getString("name")
                                    , "Bui Tuan Dung", 1234, 4.5));
                    Log.v("Firebase", document.getString("name"));
                }
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
}

