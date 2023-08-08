package com.example.elearningapp.lessonType;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CommentDialog;
import com.example.elearningapp.adapter.CommentAdapter;
import com.example.elearningapp.item.LessonItem;
import com.example.elearningapp.object.CommentObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class videoLesson extends AppCompatActivity {

    TextView title, script;
    Button nxt, pre;
    VideoView video;
    ImageButton back;
    String courseId;
    String lessonId;

    Button showComment;

    SwipeListener swipeListener;

    CommentDialog dialog;

    ShapeableImageView userPic;

    TextView commentCount;

    ImageView checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_video);

        title = findViewById(R.id.lesson_title2);
        script = findViewById(R.id.lesson_content);
        checked = findViewById(R.id.checkedVideo);

        nxt = findViewById(R.id.btn_next_lesson);
        pre = findViewById(R.id.btn_pre_lesson);

        video = findViewById(R.id.lessonVideo);
        back = findViewById(R.id.return_btn);

        showComment = findViewById(R.id.showComment);

        userPic = findViewById(R.id.userPic);

        commentCount = findViewById(R.id.commentCount);


        setLesson();
    }

    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_center_bottom);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_out_bottom);

        SwipeListener(View view, Dialog dialog) {
            int threshold = 100;
            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener =
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                            float xDiff = e2.getX() - e1.getX();
                            float yDiff = e2.getY() - e1.getY();

                            try {
                                if (Math.abs(xDiff) > Math.abs(yDiff)) {
                                    if (Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold) {
                                        if (xDiff > 0) {
                                            // Right
                                            Log.v("Swipe", "Right");
                                        } else {
                                            // Left
                                            Log.v("Swipe", "Left");
                                        }
                                    }
                                    return true;
                                } else {
                                    if (Math.abs(yDiff) > threshold && Math.abs(velocityY) > velocity_threshold) {
                                        if (yDiff > 0) {
                                            // Down
                                            if (view.getHeight() == 1500) {
                                                dialog.hide();
                                            } else {
//                                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1500);
                                                ValueAnimator anim = ValueAnimator.ofInt(view.getHeight(), 1500);
                                                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                    @Override
                                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                                        int val = (Integer) valueAnimator.getAnimatedValue();
                                                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, val);
                                                    }
                                                });
                                                anim.setDuration(400);
                                                anim.start();
                                            }
                                            Log.v("Swipe", "Down" + view.getHeight());
                                        } else {
                                            // Up
//                                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                            ValueAnimator anim = ValueAnimator.ofInt(view.getHeight(), 2200);
                                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                @Override
                                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                                    int val = (Integer) valueAnimator.getAnimatedValue();
                                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, val);
                                                }
                                            });
                                            anim.setDuration(400);
                                            anim.start();
                                            Log.v("Swipe", "Up" );
                                        }
                                    }
                                    return true;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                    };
            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }

    void setLesson() {
        List<LessonItem> lessonItem = (List<LessonItem>) getIntent().getSerializableExtra("lesson");
        int position = getIntent().getIntExtra("position", 0);
        int maxPosition = getIntent().getIntExtra("maxPosition", 0);
        courseId = getIntent().getStringExtra("courseId");
        lessonId = lessonItem.get(position).getLessonId();

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

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


        FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                .collection("learn").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.getBoolean(lessonId) != null) {
                                checked.setVisibility(View.VISIBLE);
                            } else {
                                checked.setVisibility(View.GONE);
                            }
                        }
                    }
                });

        title.setText(lessonItem.get(position).getName());
        script.setText(lessonItem.get(position).getScript());
        video.setVideoURI(Uri.parse(lessonItem.get(position).getVideo()));
        video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                checked.setVisibility(View.VISIBLE);
                FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                        .collection("learn").document(courseId).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists() && documentSnapshot.getBoolean(lessonId) == null) {
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
                                        FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                                                .collection("learn").document(courseId).update(lessonId, true);

                                }
                            }
                        });

            }
        });
        video.start();

        showComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(lessonItem.get(position).getLessonId());
            }
        });

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
            Intent intent = new Intent(videoLesson.this, videoLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);

            startActivity(intent);
            finish();
        } else if (lessonItemList.get(position).getType().equals("text")) {
            Intent intent = new Intent(videoLesson.this, textLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);

            startActivity(intent);
            finish();
        } else if (lessonItemList.get(position).getType().equals("test")) {
            Intent intent = new Intent(videoLesson.this, testLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);

            startActivity(intent);
            finish();
        }
    }
}