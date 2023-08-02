package com.example.elearningapp.lessonType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CommentDialog;
import com.example.elearningapp.item.LessonItem;
import com.example.elearningapp.object.CommentObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class videoLesson extends AppCompatActivity {

    TextView title, script;
    Button nxt, pre;
    VideoView video;
    ImageButton back;
    String courseId;

    Button showComment;

    SwipeListener swipeListener;

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

        showComment = findViewById(R.id.showComment);

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
                                            if (view.getHeight() == 1200) {
                                                dialog.hide();
                                            } else {
//                                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1500);
                                                ValueAnimator anim = ValueAnimator.ofInt(view.getHeight(), 1200);
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

        title.setText(lessonItem.get(position).getName());
        script.setText(lessonItem.get(position).getScript());
        video.setVideoURI(Uri.parse(lessonItem.get(position).getVideo()));
        video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();

        showComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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

    private void showDialog() {
        List<CommentObject> commentObjects = new ArrayList<>();
        commentObjects.add(new CommentObject("Haha"));
        commentObjects.add(new CommentObject("Ban that biet dua"));
        commentObjects.add(new CommentObject("In this step, we are going to Get the employeelist by calling the Constants getEmployeeData() method and pass the employeelist to the DialogList class and display the employee list. Comments are added inside the code for a better understanding of the Code."));
        commentObjects.add(new CommentObject("Here we are going to Apply OnClickListener to our RecylerView Adapter by Implementing OnClickListener Interface. Navigate to app"));
        commentObjects.add(new CommentObject("Nếu bạn ở trong tình huống mà mọi người chưa từng thực hiện kiểm tra chịu tải trước đó và cần tìm hiểu một công cụ mới, thì một công cụ điều khiển bằng GUI như JMeter đơn giản là lựa chọn dễ dàng nhất. Tuy nhiên, màn hình kế hoạch kiểm tra (Test Plan) chào đón bạn khi bạn khởi động JMeter lần đầu tiên không cung cấp bất kỳ hướng dẫn nào về cách tạo trình lấy mẫu HTTP. UI là chủ quan ở một mức độ nhất định. Tuy nhiên, chúng tôi cho rằng việc khám phá giao diện người dùng dễ dàng hơn đối với những người không phải là nhà phát triển hơn là một chút sử dụng mã lệnh như k6."));
        final CommentDialog dialog = new CommentDialog(this, commentObjects);
//        dialog.findViewById(R.id.commentLayout).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_comment);
        swipeListener = new SwipeListener(dialog.findViewById(R.id.commentLayout), dialog);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1200);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
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