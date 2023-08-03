package com.example.elearningapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.CommentAdapter;
import com.example.elearningapp.object.CommentObject;

import java.util.ArrayList;
import java.util.List;

public class CommentDialog extends Dialog {

    private List<CommentObject> commentObjects;

    CommentAdapter commentAdapter;

    public CommentDialog(Context context, List<CommentObject> commentObjects, CommentAdapter commentAdapter) {
        super(context);
        this.commentObjects = commentObjects;
        this.commentAdapter = commentAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment, null);
        setContentView(view);

        setUpRecyclerView(view);

    }

    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.listComment);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        commentAdapter = new CommentAdapter(getContext(), commentObjects);

        recyclerView.setAdapter(commentAdapter);
    }
}
