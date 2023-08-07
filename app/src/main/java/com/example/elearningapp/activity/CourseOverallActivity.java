package com.example.elearningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.example.elearningapp.interfaces.CourseCallBack;
import com.example.elearningapp.object.CourseListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class CourseOverallActivity extends AppCompatActivity {

    private String courseId = "";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button report;

    TextView authorName, courseName, students, star, courseIntro;
    ShapeableImageView courseImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course2);

        TextView lessonList_btn = findViewById(R.id.lesson_baihoc);
        courseId = getIntent().getStringExtra("courseId");
        authorName = findViewById(R.id.authorName);
        courseName = findViewById(R.id.courseName2);
        students = findViewById(R.id.seeMore1);
        star = findViewById(R.id.mayLikeCourse);
        courseIntro = findViewById(R.id.courseIntro);
        courseImg = findViewById(R.id.continueCourse1);
        report = findViewById(R.id.report);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ReportActivity.class);
                startActivity(intent);
                finish();
            }
        });


        readData(new CourseCallBack() {
            @Override
            public void onCourseCallback(CourseListItem courseListItem) {
                settingLayout(courseListItem);
            }
        });



//        Picasso.get().load(courseListItem.getImage()).into(courseImg);
//        authorName.setText(courseListItem.getOwner());
//        courseName.setText(courseListItem.getName());
//        students.setText(courseListItem.getNumberStudent() + "");
//        star.setText(courseListItem.getNumberStar() + "");
//        courseIntro.setText(courseListItem.getDescription());



        lessonList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lessonActivity = new Intent(getApplicationContext(), CourseLessonsActivity.class);
                lessonActivity.putExtra("courseId", courseId);
                startActivity(lessonActivity);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    public void settingLayout(CourseListItem courseListItem) {
        Picasso.get().load(courseListItem.getImage()).into(courseImg);
        authorName.setText(courseListItem.getOwner());
        courseName.setText(courseListItem.getName());
        students.setText(courseListItem.getNumberStudent() + "");
        star.setText(courseListItem.getNumberStar() + "");
        courseIntro.setText(courseListItem.getDescription());
    }

    public void readData(CourseCallBack myCallBack) {
        DocumentReference docRef = db.collection("courses").document(courseId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot document = task.getResult();
                CourseListItem courseListItem = new CourseListItem(document.getId(), document.getString("image"), document.getString("name"),
                        "Bùi Tuấn Dũng", document.getString("description"),
                        document.getDouble("students").intValue(), document.getDouble("star"));
                myCallBack.onCourseCallback(courseListItem);
            }
        });
    }


}
