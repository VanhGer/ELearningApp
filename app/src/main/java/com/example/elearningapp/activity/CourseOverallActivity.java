package com.example.elearningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;
import com.example.elearningapp.interfaces.CourseCallBack;
import com.example.elearningapp.object.CourseListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CourseOverallActivity extends AppCompatActivity {

    private String courseId = "";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button report, vote;

    TextView authorName, courseName, students, star, courseIntro;
    ShapeableImageView courseImg;
    ImageButton backBtn;
    ImageView verified;

    ShapeableImageView avatarImage;
    TextView lessonList_btn;

    Button enrollButton;

    private void sendToReportActivity(String courseId) {
        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
        intent.putExtra("courseId", courseId); // Gửi courseId qua Intent
        startActivity(intent);
    }

    private void sendToVoteActivity(String courseId) {
        Intent intent = new Intent(getApplicationContext(), VoteActivity.class);
        intent.putExtra("courseId", courseId); // Gửi courseId qua Intent
        startActivity(intent);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course2);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        avatarImage = findViewById(R.id.avatar);
        lessonList_btn = findViewById(R.id.lesson_baihoc);
        courseId = getIntent().getStringExtra("courseId");
        authorName = findViewById(R.id.authorName);
        courseName = findViewById(R.id.courseName2);
        students = findViewById(R.id.seeMore1);
        star = findViewById(R.id.mayLikeCourse);
        courseIntro = findViewById(R.id.courseIntro);
        courseImg = findViewById(R.id.continueCourse1);
        report = findViewById(R.id.report);
        vote = findViewById(R.id.vote);
        backBtn = findViewById(R.id.imageButton8);
        enrollButton = findViewById(R.id.enroll);
        verified = findViewById(R.id.imageView27);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToReportActivity(courseId); // Gọi phương thức để gửi courseId khi nhấn vào nút "report"
            }
        });

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("users").document(userId)
                        .collection("learn").document(courseId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value.exists()) {
                                    Long num = value.getLong("cnt");
                                    FirebaseFirestore.getInstance().collection("courses")
                                            .document(courseId).collection("lessons")
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    QuerySnapshot documentSnapshot = task.getResult();
                                                    int numLesson = documentSnapshot.getDocuments().size();
                                                    if (5 * num >= numLesson) {
                                                        sendToVoteActivity(courseId); // Gọi phương thức để gửi courseId khi nhấn vào nút "vote"
                                                    } else {
                                                        Toast.makeText(CourseOverallActivity.this, "Bạn phải hoàn thành ít nhất 20% để tiến hành đánh giá khóa học.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(CourseOverallActivity.this, "Tham gia khóa học và hoàn thành ít nhất 20% để tiến hành đánh giá khóa học.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });


        readData(new CourseCallBack() {
            @Override
            public void onCourseCallback(CourseListItem courseListItem) {
                settingLayout(courseListItem);
            }
        });


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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        String currentUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(currentUId).collection("learn").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            lessonList_btn.setVisibility(View.VISIBLE);
                        } else {
                            enrollButton.setVisibility(View.VISIBLE);
                            enrollButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HashMap<String, Object> data = new HashMap<>();
                                    enrollButton.setVisibility(View.GONE);
                                    data.put("timestamp", System.currentTimeMillis());
                                    data.put("cnt", 0);
                                    FirebaseFirestore.getInstance().collection("users")
                                            .document(currentUId).collection("learn").document(courseId).set(data);
                                    FirebaseFirestore.getInstance().collection("courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            DocumentSnapshot documentSnapshot1 = task.getResult();
                                            if (documentSnapshot1 != null) {
                                                Long students = documentSnapshot1.getLong("students");
                                                FirebaseFirestore.getInstance().collection("courses")
                                                        .document(courseId).update("students", students + 1);
                                            }
                                        }
                                    });

                                    Toast.makeText(CourseOverallActivity.this, "Tham gia khóa học thành công", Toast.LENGTH_SHORT).show();
                                    Intent lessonActivity = new Intent(getBaseContext(), CourseLessonsActivity.class);
                                    lessonActivity.putExtra("courseId", courseId);
                                    startActivity(lessonActivity);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finish();
                                }
                            });
                        }
                    }
                });
        DocumentReference docRef = db.collection("courses").document(courseId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                FirebaseFirestore.getInstance().collection("users")
                        .document(document.getString("owner")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                CourseListItem courseListItem = new CourseListItem(document.getId(), document.getString("image"), document.getString("name"),
                                        documentSnapshot.getString("name"), document.getString("description"),
                                        document.getDouble("students").intValue(), document.getDouble("star"));
                                if (documentSnapshot.getBoolean("verified") != null) {
                                    verified.setVisibility(View.VISIBLE);
                                }
                                Picasso.get().load(documentSnapshot.getString("image")).into(avatarImage);
                                avatarImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getBaseContext(), Check_another_profile.class);
                                        intent.putExtra("userId", documentSnapshot.getId());
                                        startActivity(intent);
                                    }
                                });
                                myCallBack.onCourseCallback(courseListItem);
                            }
                        });

            }
        });
    }


}
