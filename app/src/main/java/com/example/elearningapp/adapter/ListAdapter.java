package com.example.elearningapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.ClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.item.LessonItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private final ClickHelper clickHelper;

    @NonNull
    Context context;
    List<LessonItem> lessonItemList;

    public ListAdapter(@NonNull Context context, List<LessonItem> lessonItemList, ClickHelper clickHelper) {
        this.context = context;
        this.lessonItemList = lessonItemList;
        this.clickHelper = clickHelper;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lesson_item, parent, false);
        return new ListViewHolder(view, clickHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.nameView.setText(lessonItemList.get(position).getName());
        holder.desView.setText(lessonItemList.get(position).getDes());
        holder.numView.setText("Bai " + (position + 1) +  "");
        Picasso.get().load(lessonItemList.get(position).getImage()).into(holder.picView);
        //holder.picView.setImageResource(lessonItemList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return lessonItemList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, desView, numView;
        ImageView picView;

        public ListViewHolder(@NonNull View itemView, ClickHelper clickHelper) {
            super(itemView);
            nameView = itemView.findViewById(R.id.lesson_title);
            desView = itemView.findViewById(R.id.lesson_description);
            numView = itemView.findViewById(R.id.lesson_num);
            picView = itemView.findViewById(R.id.lesson_pic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickHelper != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            clickHelper.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }


}