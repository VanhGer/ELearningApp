package com.example.elearningapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.object.CommentObject;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

//    private final LessonClickHelper clickHelper;

    @NonNull
    Context context;
    List<CommentObject> courseObjectList;


    public CommentAdapter(@NonNull Context context, List<CommentObject> courseObjectList) {
        this.context = context;
        this.courseObjectList = courseObjectList;

//        this.clickHelper = clickHelper;
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comment_v1_item, parent, false);
        return new CommentViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.contentView.setText(courseObjectList.get(position).getContent());

        holder.replyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replyText = holder.replyView.getText().toString();
                if (replyText.contains("Reply")) {
                    Log.v("Comment1", replyText);
                    holder.replyView.setText("Hide");
                    holder.commentReplyLayout.removeAllViews();
                    LayoutInflater factory = LayoutInflater.from(context);
                    List<CommentObject> commentv2Objects = new ArrayList<>();
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(courseObjectList.get(holder.getAdapterPosition()).getId()).collection("replies").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                                        View newView = factory.inflate(R.layout.comment_v2_item, null);
                                        TextView text = newView.findViewById(R.id.v2content);
                                        text.setText(documentSnapshot.getString("content"));
                                        holder.commentReplyLayout.addView(newView);

                                        Log.v("Comment", documentSnapshot.getId());
                                    }
                                }
                            });
                    holder.commentReplyLayout.setVisibility(View.VISIBLE);
                } else {
//                        Log.v("Comment2", replyView.getText().toString());
                    holder.replyView.setText("Reply");
//                        ((ViewGroup) commentReplyLayout.getParent()).removeView(commentReplyLayout);
                    holder.commentReplyLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return courseObjectList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView contentView;
        LinearLayout commentReplyLayout;

        TextView replyView;

        public CommentViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            contentView = itemView.findViewById(R.id.commentv1Content);
            commentReplyLayout = itemView.findViewById(R.id.commentv2layout);
            replyView = itemView.findViewById(R.id.commentReply);
        }
    }


}