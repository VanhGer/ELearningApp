package com.example.elearningapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.CourseCategoryAdapter;
import com.example.elearningapp.adapter.TopCourseAdapter;
import com.example.elearningapp.interfaces.CourseClickHelper;
import com.example.elearningapp.object.CourseListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CourseClickHelper  {

    ImageButton backBtn;
    String categoryID;

    ShapeableImageView titlePic;
    ShapeableImageView featurePic;
    TextView titleText;
    TextView featureTitle;
    TextView featureDes;
    TextView featureOwnerName;
    ShapeableImageView ownerPic;

    RecyclerView topCourseRecyclerView;
    CourseCategoryAdapter topCourseAdapter;

    List<CourseListItem> courseListItemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        init();

        categoryID = getIntent().getStringExtra("CategoryID");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadDataFromFirestore();

        setFeature();

        setCourseRecycler();

    }

    private void init() {
        backBtn = findViewById(R.id.imageButton6);
        titleText = findViewById(R.id.textView85);
        titlePic = findViewById(R.id.categoryPic);
        topCourseRecyclerView = findViewById(R.id.popularCourseCategoryRecycler);
        featurePic = findViewById(R.id.featurePic);
        featureTitle = findViewById(R.id.textView89);
        featureDes = findViewById(R.id.textView90);
        featureOwnerName = findViewById(R.id.textView91);
        ownerPic = findViewById(R.id.ownerPic);
    }

    private void loadDataFromFirestore() {
        FirebaseFirestore.getInstance()
                .collection("categories")
                .document(categoryID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        titleText.setText(documentSnapshot.getString("name"));
                        Picasso.get().load(documentSnapshot.getString("image")).into(titlePic);
                    }
                });

    }

    private void setFeature() {
        FirebaseFirestore.getInstance()
                .collection("categories")
                .document(categoryID)
                .collection("courses")
                .orderBy("pin", Query.Direction.DESCENDING)
                .limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
                            final String courseID = documentSnapshot.getId();
                            FirebaseFirestore.getInstance()
                                    .collection("courses")
                                    .document(documentSnapshot.getId())
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String des = documentSnapshot.getString("description");
                                            des = des.substring(0, Math.min(des.length(), 200));
                                            des += "...";
                                            featureDes.setText(des);
                                            featureTitle.setText(documentSnapshot.getString("name"));
                                            Picasso.get().load(documentSnapshot.getString("image")).into(featurePic);
                                            FirebaseFirestore.getInstance().collection("users")
                                                    .document(documentSnapshot.getString("owner"))
                                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            featureOwnerName.setText(documentSnapshot.getString("name"));
                                                            Picasso.get().load(documentSnapshot.getString("image")).into(ownerPic);
                                                            Handler handler = new Handler();
                                                            Runnable runnable = new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    findViewById(R.id.constraintLayout20).setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            Intent overallActivity = new Intent(getApplicationContext(), CourseOverallActivity.class);
                                                                            overallActivity.putExtra("courseId", courseID);
                                                                            startActivity(overallActivity);
                                                                        }
                                                                    });
                                                                    findViewById(R.id.progressBar3).setVisibility(View.GONE);
                                                                    findViewById(R.id.constraintLayout20).setVisibility(View.VISIBLE);   ;
                                                                }
                                                            };
                                                            handler.postDelayed(runnable, 1000);
                                                        }
                                                    });

                                        }
                                    });

                        }
                    }
                });
    }

    private void setCourseRecycler() {
        topCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topCourseAdapter = new CourseCategoryAdapter(this, courseListItemList, this);
        topCourseRecyclerView.setAdapter(topCourseAdapter);

        FirebaseFirestore.getInstance()
                .collection("categories")
                    .document(categoryID)
                        .collection("courses").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        courseListItemList.clear();
                        topCourseRecyclerView.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < value.getDocuments().size(); i++) {
                            DocumentSnapshot documentSnapshot = value.getDocuments().get(i);
                            final int position = i;
                            FirebaseFirestore.getInstance()
                                    .collection("courses")
                                    .document(documentSnapshot.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot document) {
                                            courseListItemList.add(
                                                    new CourseListItem(
                                                            document.getId(),
                                                            document.getString("image"),
                                                            document.getString("name"),
                                                            document.getString("owner"),
                                                            document.getString("description"),
                                                            document.getDouble("students").intValue(),
                                                            document.getDouble("star"))
                                            );
                                            topCourseAdapter.notifyDataSetChanged();
                                            if (position == value.getDocuments().size() - 1) {
                                                Handler handler = new Handler();
                                                Runnable runnable = new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        findViewById(R.id.progressBar2).setVisibility(View.GONE);
                                                        topCourseRecyclerView.setVisibility(View.VISIBLE);
                                                    }
                                                };
                                                handler.postDelayed(runnable, 1000);
                                            }
                                        }
                                    });

                        }
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