package com.example.elearningapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.activity.SearchActivity;
import com.example.elearningapp.interfaces.CourseClickHelper;
import com.example.elearningapp.interfaces.LessonClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.object.ContinueCourseObject;
import com.example.elearningapp.object.CourseListItem;
import com.example.elearningapp.object.CourseObject;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContinueCourseAdapter extends RecyclerView.Adapter<ContinueCourseAdapter.ContinueCourseViewHolder> {
    private final CourseClickHelper clickHelper;
    Context context;
    List<ContinueCourseObject> courseListItemList;

    public ContinueCourseAdapter(@NonNull Context context, List<ContinueCourseObject> courseListItemList,
                            CourseClickHelper clickHelper) {
        this.context = context;
        this.courseListItemList = courseListItemList;
        this.clickHelper = clickHelper;
    }

    @NonNull
    @Override
    public ContinueCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.continue_course_item, parent, false);
        return new ContinueCourseAdapter.ContinueCourseViewHolder(view, clickHelper, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ContinueCourseViewHolder holder, int position) {
        ContinueCourseObject courseObject = courseListItemList.get(position);
        holder.id = courseObject.getCourseId();
        holder.nameView.setText(courseObject.getName() + " (" + courseObject.getProgess() + "%)");
        Picasso.get().load(courseObject.getImage()).into(holder.imageView);
        holder.progressBar.setProgress(courseObject.getProgess());
        Log.v("Continue", courseObject.getProgess() + "");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return courseListItemList.size();
    }

    public static class ContinueCourseViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        ShapeableImageView imageView;

        ProgressBar progressBar;

        String id;


        public ContinueCourseViewHolder(@NonNull View itemView, CourseClickHelper clickHelper, Context context) {
            super(itemView);
            nameView = itemView.findViewById(R.id.top_course_item_name);
            imageView = itemView.findViewById(R.id.top_course_item_image);
            progressBar = itemView.findViewById(R.id.progressBar12);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickHelper != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            clickHelper.onItemClick(id);
                        }
                    }
                }
            });

        }
    }
}
