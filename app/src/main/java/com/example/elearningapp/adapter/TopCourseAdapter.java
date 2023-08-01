package com.example.elearningapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.activity.SearchActivity;
import com.example.elearningapp.interfaces.CourseClickHelper;
import com.example.elearningapp.interfaces.LessonClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.object.CourseListItem;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopCourseAdapter extends RecyclerView.Adapter<TopCourseAdapter.TopCourseViewHolder> {


    private final CourseClickHelper clickHelper;
    Context context;
    List<CourseListItem> courseListItemList;

    public TopCourseAdapter(@NonNull Context context, List<CourseListItem> courseListItemList,
                            CourseClickHelper clickHelper) {
        this.context = context;
        this.courseListItemList = courseListItemList;
        this.clickHelper = clickHelper;
    }

    @NonNull
    @Override
    public TopCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.top_course_item, parent, false);
        return new TopCourseAdapter.TopCourseViewHolder(view, clickHelper, context);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCourseViewHolder holder, int position) {
        holder.nameView.setText(courseListItemList.get(position).getName());
        holder.ownerView.setText(courseListItemList.get(position).getOwner());
        Picasso.get().load(courseListItemList.get(position).getImage()).into(holder.imageView);
        holder.numStarView.setText(courseListItemList.get(position).getNumberStar() + " ");
        holder.numStudentView.setText(courseListItemList.get(position).getNumberStudent() + " người học");
        holder.id = courseListItemList.get(position).getId();
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

        ImageView iconStar;

        ImageView iconUser;

        String id;


        public TopCourseViewHolder(@NonNull View itemView, CourseClickHelper clickHelper, Context context) {
            super(itemView);
            nameView = itemView.findViewById(R.id.top_course_item_name);
            imageView = itemView.findViewById(R.id.top_course_item_image);
            ownerView = itemView.findViewById(R.id.top_course_item_owner);
            numStudentView = itemView.findViewById(R.id.top_course_item_students);
            numStarView = itemView.findViewById(R.id.top_course_item_star);
            iconStar = itemView.findViewById(R.id.starImage);
            iconUser = itemView.findViewById(R.id.userImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickHelper != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            if (context instanceof SearchActivity) {
                                Log.v("Search", context.toString());
                                ((SearchActivity) context).updateSearchCount();
                            }
                            clickHelper.onItemClick(id);
                        }
                    }
                }
            });

        }
    }
}
