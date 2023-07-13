package com.example.elearningapp.courseItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;

public class ListViewHolder extends RecyclerView.ViewHolder {

    TextView nameView, desView, numView;
    ImageView picView;

    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.lesson_title);
        desView = itemView.findViewById(R.id.lesson_description);
        numView = itemView.findViewById(R.id.lesson_num);
        picView = itemView.findViewById(R.id.lesson_pic);
    }
}
