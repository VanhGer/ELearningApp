package com.example.elearningapp.lessonType;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CommentDialog;
import com.example.elearningapp.item.LessonItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class textLesson extends AppCompatActivity {

    TextView title, content;
    Button nxt, pre;
    ImageButton back;
    String courseId;

    ShapeableImageView userPic;

    TextView commentCount;

    Button showComment;

    CommentDialog dialog;

    Button tickDone;
    String lessonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_text);

        title = findViewById(R.id.lesson_title2);
        content = findViewById(R.id.lesson_content);

        nxt = findViewById(R.id.btn_next_lesson);
        pre = findViewById(R.id.btn_pre_lesson);

        back = findViewById(R.id.return_btn);

        showComment = findViewById(R.id.showComment);

        userPic = findViewById(R.id.userPic);

        commentCount = findViewById(R.id.commentCount);

        tickDone = findViewById(R.id.tickDone);

        setLesson();
    }

    void setLesson() {
        List<LessonItem> lessonItem = (List<LessonItem>) getIntent().getSerializableExtra("lesson");
        int position = getIntent().getIntExtra("position", 0);
        int maxPosition = getIntent().getIntExtra("maxPosition", 0);
        courseId = getIntent().getStringExtra("courseId");
        lessonId = lessonItem.get(position).getLessonId();

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                        .collection("learn").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.getBoolean(lessonId) != null) {
                                Log.v("LearnXXX", "Not Done");
                                tickDone.setVisibility(View.GONE);
                                findViewById(R.id.checked).setVisibility(View.VISIBLE);
                            } else {
                                tickDone.setVisibility(View.VISIBLE);
                                tickDone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.v("Learn", courseId + " " + lessonId);
                                        FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                                                .collection("learn").document(courseId).update(lessonId, true);
                                        FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                                                .collection("learn").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        DocumentSnapshot documentSnapshot1 = task.getResult();
                                                        if (documentSnapshot1.exists()) {
                                                            Long cnt = documentSnapshot1.getLong("cnt");
                                                            FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                                                                    .collection("learn").document(courseId).update("cnt", cnt + 1);
                                                        }
                                                    }
                                                });
                                        tickDone.setVisibility(View.GONE);
                                        findViewById(R.id.checked).setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("users")
                .document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Picasso.get().load(value.getString("image")).into(userPic);
                    }
                });

        FirebaseFirestore.getInstance().collection("comments")
                .whereEqualTo("courseId", courseId)
                .whereEqualTo("lessonId", lessonItem.get(position).getLessonId())
                .count()
                .get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                        AggregateQuerySnapshot snapshot = task.getResult();
                        if (snapshot.getCount() != 0) {
                            commentCount.setText(snapshot.getCount() + "");
                            commentCount.setVisibility(View.VISIBLE);
                        }
                    }
                });

        showComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(lessonItem.get(position).getLessonId());
            }
        });

        title.setText(lessonItem.get(position).getName());
        content.setText(lessonItem.get(position).getContent());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textLesson.this.onBackPressed();
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
        } else {
            pre.setVisibility(View.INVISIBLE);
        }
        if (position != maxPosition - 1) {
            nxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonOnClick(position + 1, maxPosition, lessonItem);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        } else {
            nxt.setVisibility(View.INVISIBLE);
        }
    }

    private void showDialog(String lessonId) {
        final Activity activity = this;

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("courses")
                .document(courseId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String ownerID = documentSnapshot.getString("owner");
                        dialog = new CommentDialog(activity, courseId, lessonId, currentUserId, activity, ownerID);

                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_comment);

                        dialog.show();
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1500);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.getWindow().setGravity(Gravity.BOTTOM);
                    }
                });

    }

    void buttonOnClick(int position, int maxPosition, List<LessonItem> lessonItemList) {
        if (lessonItemList.get(position).getType().equals("video")) {
            Intent intent = new Intent(textLesson.this, videoLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
            finish();
        } else if (lessonItemList.get(position).getType().equals("text")) {
            Intent intent = new Intent(textLesson.this, textLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
            finish();
        } else if (lessonItemList.get(position).getType().equals("test")) {
            Intent intent = new Intent(textLesson.this, testLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
            finish();
        }
    }


}