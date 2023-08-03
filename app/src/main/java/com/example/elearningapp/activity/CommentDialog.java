package com.example.elearningapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.CommentAdapter;
import com.example.elearningapp.object.CommentObject;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDialog extends Dialog {

    private List<CommentObject> commentObjects;

    CommentAdapter commentAdapter;

    String couserId;
    String lessonId;
    String userId;

    EditText replyCommentField;

    ImageButton replySendButton;

    public CommentDialog(Context context, List<CommentObject> commentObjects,
                         CommentAdapter commentAdapter,
                         String courseId,
                         String lessonId,
                         String userId) {
        super(context);
        this.commentObjects = commentObjects;
        this.commentAdapter = commentAdapter;
        this.couserId = courseId;
        this.lessonId = lessonId;
        this.userId = userId;
    }

    public EditText getCommentEditText() {
        return replyCommentField;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment, null);
        setContentView(view);


        replySendButton = view.findViewById(R.id.replySendButton);

        replyCommentField = view.findViewById(R.id.replyCommentField);

        replySendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replyCommentField.getText().length() < 5) {
                    return;
                }
                if (commentAdapter.getUserReplyIdGet().equals("")) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("content", replyCommentField.getText().toString());
                    data.put("courseId", couserId);
                    data.put("like", Arrays.asList("xyz"));
                    data.put("lessonId", lessonId);
                    data.put("timestamp", System.currentTimeMillis());
                    data.put("userId", userId);
                    FirebaseFirestore.getInstance().collection("comments")
                            .add(data);
                    replyCommentField.setText("");
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("content", replyCommentField.getText().toString());
                    data.put("like", Arrays.asList("xyz"));
                    data.put("timestamp", System.currentTimeMillis());
                    data.put("userId", userId);
                    data.put("userReplyId", commentAdapter.getUserReplyIdGet());
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(commentAdapter.getCommentIdReply())
                            .collection("replies")
                            .add(data);
                    replyCommentField.setText("");
                }

            }
        });


        setUpRecyclerView(view);

    }

    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.listComment);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        commentAdapter = new CommentAdapter(getContext(), commentObjects);

        recyclerView.setAdapter(commentAdapter);
    }
}
