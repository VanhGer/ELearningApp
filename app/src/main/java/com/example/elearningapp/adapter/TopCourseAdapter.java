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
import com.example.elearningapp.courseItem.LessonItem;
import com.example.elearningapp.object.CourseListItem;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopCourseAdapter extends RecyclerView.Adapter<TopCourseAdapter.TopCourseViewHolder> {

    Context context;
    List<CourseListItem> courseListItemList;

    public TopCourseAdapter(@NonNull Context context, List<CourseListItem> courseListItemList) {
        this.context = context;
        this.courseListItemList = courseListItemList;
    }

    @NonNull
    @Override
    public TopCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.top_course_item, parent, false);
        return new TopCourseAdapter.TopCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCourseViewHolder holder, int position) {
        holder.nameView.setText(courseListItemList.get(position).getName());
        holder.imageView.setImageResource(courseListItemList.get(position).getImage());
        holder.ownerView.setText(courseListItemList.get(position).getOwner());
        holder.numStarView.setText(courseListItemList.get(position).getNumberStar() + " ");
        holder.numStudentView.setText(courseListItemList.get(position).getNumberStudent() + " students");
    }

    @Override
    public int getItemCount() {
        return courseListItemList.size();
    }

    public static class TopCourseViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        ShapeableImageView imageView;
        TextView ownerView;
        TextView numStudentView;
        TextView numStarView;


        public TopCourseViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.top_course_item_name);
            imageView = itemView.findViewById(R.id.top_course_item_image);
            ownerView = itemView.findViewById(R.id.top_course_item_owner);
            numStudentView = itemView.findViewById(R.id.top_course_item_students);
            numStarView = itemView.findViewById(R.id.top_course_item_star);
        }
    }
}
