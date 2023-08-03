package com.example.elearningapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.object.CommentObject;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

//    private final LessonClickHelper clickHelper;

    @NonNull
    Context context;
    List<CommentObject> courseObjectList = new ArrayList<>();


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
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        holder.contentView.setText(courseObjectList.get(position).getContent());

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(courseObjectList.get(position).getUserId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        holder.v1username.setText(value.getString("name"));
                        Picasso.get().load(value.getString("image")).into(holder.v1userpic);
                    }
                });

        List<String> likeListV1 = courseObjectList.get(position).getLikeList();

        boolean currentLikeV1 = false;
        for (String userid: likeListV1) {
            Log.v("Comment", userid );
            if (userid.equals(currentUserId)) {
                currentLikeV1 = true;
                break;
            }
        }



        if (currentLikeV1) {
            holder.likev1Button.setImageResource(R.drawable.ic_heart_filled);
            holder.likev1Button.setTag(R.drawable.ic_heart_filled);
        } else {
            holder.likev1Button.setImageResource(R.drawable.ic_heart);
            holder.likev1Button.setTag(R.drawable.ic_heart);
        }

        holder.likev1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.likev1Button.getTag().equals(R.drawable.ic_heart_filled)) {
                    Log.v("Comment1", currentUserId);
                    likeListV1.remove(currentUserId);
                    holder.likeCntv1.setText(likeListV1.size() + "");
                    holder.likev1Button.setImageResource(R.drawable.ic_heart);
                    holder.likev1Button.setTag(R.drawable.ic_heart);
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(courseObjectList.get(holder.getAdapterPosition()).getId())
                            .update("like", likeListV1);
                } else {
                    Log.v("Comment2", currentUserId);
                    likeListV1.add(currentUserId);
                    holder.likeCntv1.setText(likeListV1.size() + "");
                    holder.likev1Button.setImageResource(R.drawable.ic_heart_filled);
                    holder.likev1Button.setTag(R.drawable.ic_heart_filled);
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(courseObjectList.get(holder.getAdapterPosition()).getId())
                            .update("like", likeListV1);
                }
            }
        });

        holder.likeCntv1.setText(likeListV1.size() + "");


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
                            .document(courseObjectList.get(holder.getAdapterPosition()).getId()).collection("replies")
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if (value.getMetadata().hasPendingWrites()) {
                                        return;
                                    }
//                                    holder.commentReplyLayout.removeAllViews();
                                    for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                                        FirebaseFirestore.getInstance()
                                                .collection("users")
                                                .document(documentSnapshot.getString("userId")).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                        View newView = factory.inflate(R.layout.comment_v2_item, null);
                                                        TextView text = newView.findViewById(R.id.v2content);
                                                        ShapeableImageView userpic = newView.findViewById(R.id.v2userpic);
                                                        TextView username = newView.findViewById(R.id.v2username);
                                                        TextView time = newView.findViewById(R.id.v2time);
                                                        TextView cntLike = newView.findViewById(R.id.likeCnt);
                                                        ImageButton likeButton = newView.findViewById(R.id.likev2Button);

                                                        List<String> likeList = (List<String>)documentSnapshot.get("like");

                                                        boolean currentLike = false;
                                                        for (String userid: likeList) {
                                                            if (userid.equals(currentUserId)) {
                                                                currentLike = true;
                                                                break;
                                                            }
                                                        }

                                                        if (currentLike) {
                                                            likeButton.setImageResource(R.drawable.ic_heart_filled);
                                                            likeButton.setTag(R.drawable.ic_heart_filled);
                                                        } else {
                                                            likeButton.setImageResource(R.drawable.ic_heart);
                                                            likeButton.setTag(R.drawable.ic_heart);
                                                        }

                                                        likeButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                ImageButton likeButton = newView.findViewById(R.id.likev2Button);
                                                                if (likeButton.getTag().equals(R.drawable.ic_heart_filled)) {
                                                                    likeList.remove(currentUserId);
                                                                    cntLike.setText(likeList.size() + "");
                                                                    likeButton.setImageResource(R.drawable.ic_heart);
                                                                    likeButton.setTag(R.drawable.ic_heart);
                                                                    FirebaseFirestore.getInstance().collection("comments")
                                                                            .document(courseObjectList.get(holder.getAdapterPosition()).getId())
                                                                            .collection("replies")
                                                                            .document(documentSnapshot.getId())
                                                                            .update("like", likeList);
                                                                    for (String user: likeList) {
                                                                        Log.v("Comment", user);
                                                                    }
                                                                } else {
                                                                    likeList.add(currentUserId);
                                                                    cntLike.setText(likeList.size() + "");
                                                                    likeButton.setImageResource(R.drawable.ic_heart_filled);
                                                                    likeButton.setTag(R.drawable.ic_heart_filled);
                                                                    FirebaseFirestore.getInstance().collection("comments")
                                                                            .document(courseObjectList.get(holder.getAdapterPosition()).getId())
                                                                            .collection("replies")
                                                                            .document(documentSnapshot.getId())
                                                                            .update("like", likeList);
                                                                    for (String user: likeList) {
                                                                        Log.v("Comment", user);
                                                                    }

                                                                }
                                                            }
                                                        });

                                                        cntLike.setText(likeList.size() + "");



                                                        text.setText(documentSnapshot.getString("content"));
                                                        username.setText(value.getString("name"));
                                                        Picasso.get().load(value.getString("image")).into(userpic);
                                                        String date = changetoDate(documentSnapshot.getLong("timestamp"));
                                                        time.setText(date);

//                                                        Log.v("Comment", documentSnapshot.getLong("timestamp").toString());

                                                        holder.commentReplyLayout.addView(newView);
                                                    }
                                                });

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

    private String changetoDate(Long timeStamp) {
        Date dateNow = new Date(System.currentTimeMillis());
        Date dateThen = new Date(timeStamp);
        long timeChange =  dateNow.getTime() - dateThen.getTime();
        timeChange /= 1000;
        if (timeChange > 0) {
            if (timeChange < 60) {
                return timeChange + " giây trước";
            } else if (timeChange < 3600) {
                timeChange /= 60;
                return timeChange + " phút trước";
            } else if (timeChange < 36400) {
                timeChange /= 3600;
                return timeChange + " giờ trước";
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String dateFormat = simpleDateFormat.format(dateThen);
                return dateFormat;
            }
        }
        return "Vừa xong";
    }

    @Override
    public int getItemCount() {
        return courseObjectList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView contentView;
        LinearLayout commentReplyLayout;

        TextView replyView;

        ImageView v1userpic;

        TextView v1username;

        TextView v1time;

        TextView likeCntv1;

        ImageButton likev1Button;

        public CommentViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            contentView = itemView.findViewById(R.id.v1content);
            commentReplyLayout = itemView.findViewById(R.id.commentv2layout);
            replyView = itemView.findViewById(R.id.commentReply);
            v1userpic = itemView.findViewById(R.id.v1userpic);
            v1username = itemView.findViewById(R.id.v1username);
            v1time = itemView.findViewById(R.id.v1time);
            likeCntv1 = itemView.findViewById(R.id.likeCntv1);
            likev1Button = itemView.findViewById(R.id.likev1Button);
        }
    }


}