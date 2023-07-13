package com.example.elearningapp.courseItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    @NonNull
    Context context;
    List<LessonItem> lessonItemList;

    public ListAdapter(@NonNull Context context, List<LessonItem> lessonItemList) {
        this.context = context;
        this.lessonItemList = lessonItemList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(context).inflate(R.layout.lesson_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.nameView.setText(lessonItemList.get(position).getName());
        holder.desView.setText(lessonItemList.get(position).getDes());
        holder.numView.setText("Bai " + position + "");
        holder.picView.setImageResource(lessonItemList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return lessonItemList.size();
    }
}
