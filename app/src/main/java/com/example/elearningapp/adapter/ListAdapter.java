package com.example.elearningapp.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.interfaces.LessonClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.item.LessonItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private final LessonClickHelper clickHelper;

    @NonNull
    Context context;
    List<LessonItem> lessonItemList;
    String userId;

    public ListAdapter(@NonNull Context context, List<LessonItem> lessonItemList,
                       LessonClickHelper clickHelper,
                       String userId) {
        this.context = context;
        this.lessonItemList = lessonItemList;
        this.clickHelper = clickHelper;
        this.userId = userId;
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
        String lessonId = lessonItemList.get(position).getLessonId();
        if (lessonItemList.get(position).getType().equals("video")) {
            holder.typeCourse.setImageResource(R.drawable.ic_video_lesson);
        } else if (lessonItemList.get(position).getType().equals("test")) {
            holder.typeCourse.setImageResource(R.drawable.ic_test_lesson);
        } else {
            holder.typeCourse.setImageResource(R.drawable.ic_text_lesson);
        }
        String str = lessonItemList.get(position).getType();
        if (str.equals("text")) str = "Văn bản";
        if (str.equals("test")) str = "Kiểm tra";
        if (str.equals("video"))  str = "Video";

        str += " - " + lessonItemList.get(position).getTime() + " phút";
        holder.numView.setText(str);
        Picasso.get().load(lessonItemList.get(position).getImage()).into(holder.picView);
        //holder.picView.setImageResource(lessonItemList.get(position).getImage());
        FirebaseFirestore.getInstance().collection("users").document(userId)
                .collection("learn").document(lessonItemList.get(position)
                        .getCourseId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.exists()) {
                            if (value.getBoolean(lessonId) != null) {
                                holder.lessonListChecked.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return lessonItemList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, desView, numView;
        ImageView picView;
        ImageView typeCourse;
        ImageView lessonListChecked;

        public ListViewHolder(@NonNull View itemView, LessonClickHelper clickHelper) {
            super(itemView);
            nameView = itemView.findViewById(R.id.lesson_title);
            desView = itemView.findViewById(R.id.lesson_description);
            numView = itemView.findViewById(R.id.lesson_num);
            picView = itemView.findViewById(R.id.lesson_pic);
            typeCourse = itemView.findViewById(R.id.typeCourse);
            lessonListChecked = itemView.findViewById(R.id.lessonListChecked);
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