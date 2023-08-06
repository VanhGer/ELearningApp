package com.example.elearningapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.CommentAdapter;
import com.example.elearningapp.object.CommentObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDialog extends Dialog {

    private List<CommentObject> commentObjects;

    CommentAdapter commentAdapter;

    String courseId;
    String lessonId;
    String userId;

    EditText replyCommentField;

    ImageButton replySendButton;

    RadioGroup filterComment;
    RadioButton newFilterComment;
    RadioButton popularFilterComment;
    ImageView replyuserpic;

    ProgressBar progressBar;
    RecyclerView listComment;

    View thisView;

    Toast toast;

    TextView noCommentText;
    ImageButton backComment;

    TextView commentRule;

    Activity activity;

    public CommentDialog(Context context,
                         String courseId,
                         String lessonId,
                         String userId, Activity activity) {
        super(context);
        this.courseId = courseId;
        this.lessonId = lessonId;
        this.userId = userId;
        this.activity = activity;
    }

    public EditText getCommentEditText() {
        return replyCommentField;
    }

    private void setNewComment() {
        setUpRecyclerView(thisView);
        listComment.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance().collection("comments")
                .orderBy("timestamp")
                .whereEqualTo("courseId", courseId)
                .whereEqualTo("lessonId", lessonId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange doc: value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                noCommentText.setVisibility(View.GONE);
                                QueryDocumentSnapshot documentSnapshot = doc.getDocument();
                                List <String> likeList = (List<String>) documentSnapshot.get("like");
                                commentObjects.add(new CommentObject(
                                        documentSnapshot.getString("content"),
                                        documentSnapshot.getId(),
                                        1,
                                        documentSnapshot.getString("userId"),
                                        likeList,
                                        documentSnapshot.getLong("timestamp")
                                ));
                                commentAdapter.notifyDataSetChanged();
                            }
                        }

                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                listComment.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        };
                        handler.postDelayed(runnable, 500);
                    }
                });
    }

    public void setPopularComment() {
        setUpRecyclerView(thisView);
        listComment.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        FirebaseFirestore.getInstance().collection("comments")
                .orderBy("likeCnt")
                .whereEqualTo("courseId", courseId)
                .whereEqualTo("lessonId", lessonId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange doc: value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                noCommentText.setVisibility(View.GONE);
                                QueryDocumentSnapshot documentSnapshot = doc.getDocument();
                                List <String> likeList = (List<String>) documentSnapshot.get("like");
                                commentObjects.add(new CommentObject(
                                        documentSnapshot.getString("content"),
                                        documentSnapshot.getId(),
                                        1,
                                        documentSnapshot.getString("userId"),
                                        likeList,
                                        documentSnapshot.getLong("timestamp")
                                ));
                                commentAdapter.notifyDataSetChanged();
                            }
                        }

                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                listComment.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        };
                        handler.postDelayed(runnable, 500);
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment, null);
        setContentView(view);

        init(view);

        SpannableString ss = new SpannableString("Hãy nhớ tôn trọng người dùng khác khi đăng bình luận và tuân thủ Nguyên tắc cộng đồng của chúng tôi");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                openRuleActivity();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 65, 85, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        commentRule.setText(ss);
        commentRule.setMovementMethod(LinkMovementMethod.getInstance());
        commentRule.setHighlightColor(Color.TRANSPARENT);

        backComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        newFilterComment.setChecked(true);
        setNewComment();


        FirebaseFirestore.getInstance().collection("users").document(userId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Picasso.get().load(value.getString("image")).into(replyuserpic);
                    }
                });


        newFilterComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewComment();
            }
        });

        popularFilterComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopularComment();
            }
        });




        replySendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replyCommentField.getText().length() < 5) {
                    showToast("Bình luận phải có ít nhất 5 từ", R.color.md_yellow_900, R.color.md_yellow_50);
                    return;
                }
                if (replyCommentField.getText().toString().contains("ngu")) {
                    showToast("Không được đăng bình luận khiếm nhã người khác", R.color.md_red_900, R.color.md_red_50);
                    return;
                }
                replySendButton.setClickable(false);
                if (commentAdapter.getUserReplyIdGet().equals("")) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("content", replyCommentField.getText().toString());
                    data.put("courseId", courseId);
                    data.put("like", Arrays.asList("xyz"));
                    data.put("lessonId", lessonId);
                    data.put("timestamp", System.currentTimeMillis());
                    data.put("userId", userId);
                    data.put("likeCnt", 0);
                    FirebaseFirestore.getInstance().collection("comments")
                            .add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    replyCommentField.setText("");
                                    Log.v("Comment", "Reply1");
                                    showToast("Đã thêm bình luận", R.color.md_blue_900, R.color.md_blue_50);
                                    replySendButton.setClickable(true);
                                }
                            });


                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("content", replyCommentField.getText().toString());
                    data.put("like", Arrays.asList("xyz"));
                    data.put("timestamp", System.currentTimeMillis());
                    data.put("userId", userId);
                    data.put("userReplyId", commentAdapter.getUserReplyIdGet());
                    data.put("likeCnt", 0);
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(commentAdapter.getCommentIdReply())
                            .collection("replies")
                            .add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    replyCommentField.setText("");
                                    Log.v("Comment", "Reply2");
                                    showToast("Đã thêm bình luận", R.color.md_blue_900, R.color.md_blue_200);
                                    replySendButton.setClickable(true);
                                }
                            });

                }

            }
        });

    }

    public void openRuleActivity(){
        activity.startActivity(new Intent(activity, CommentRule.class));
    }

    private void init(View view) {
        this.thisView = view;
        replySendButton = view.findViewById(R.id.replySendButton);
        replyCommentField = view.findViewById(R.id.replyCommentField);
        filterComment = view.findViewById(R.id.filterComment);
        newFilterComment = view.findViewById(R.id.newFilterComment);
        popularFilterComment = view.findViewById(R.id.popularFilterComment);
        replyuserpic = view.findViewById(R.id.replyuserpic);
        progressBar = view.findViewById(R.id.loadingComment);
        listComment = view.findViewById(R.id.listComment);
        backComment = view.findViewById(R.id.backComment);
        noCommentText = view.findViewById(R.id.noCommentText);
        commentRule = view.findViewById(R.id.commentRule);
    }

    private void showToast(String message, int color, int backgroundColor) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast_layout, null);
        TextView textView = layout.findViewById(R.id.toastMessage);
        textView.setText(message);
        textView.setTextColor(getContext().getResources().getColor(color));
        textView.setBackgroundTintList(getContext().getResources().getColorStateList(backgroundColor));
        if (toast!= null) {
            toast.cancel();
        }
        toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);


        toast.show();
    }


    public RecyclerView getRecyclerView() {
        if (thisView != null) {
            return thisView.findViewById(R.id.listComment);
        }
        return null;
    }

    private void setUpRecyclerView(View view) {
        commentObjects = new ArrayList<>();
        commentAdapter = new CommentAdapter(getContext(), commentObjects);

        commentAdapter.setDialog(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        listComment.setLayoutManager(layoutManager);
//        commentAdapter = new CommentAdapter(getContext(), commentObjects);

        listComment.setAdapter(commentAdapter);
    }
}
