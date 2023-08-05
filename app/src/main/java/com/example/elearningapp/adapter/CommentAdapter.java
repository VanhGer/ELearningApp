package com.example.elearningapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CommentDialog;
import com.example.elearningapp.activity.LoadingDialog;
import com.example.elearningapp.object.CommentObject;
import com.example.elearningapp.object.CourseObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentChange;
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
    String userReplyIdGet = "";
    String commentIdReply = "";

    CommentDialog commentDialog;
    ProgressBar commentLoading;

    private LoadingDialog loadingDialog;


    public CommentAdapter(@NonNull Context context, List<CommentObject> courseObjectList) {
        this.context = context;
        this.courseObjectList = courseObjectList;
    }

    public String getUserReplyIdGet(){
        return userReplyIdGet;
    }

    public String getCommentIdReply() {
        return commentIdReply;
    }

    public void setDialog(CommentDialog dialog) {
        this.commentDialog = dialog;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comment_v1_item, parent, false);

//        loadingDialog = new LoadingDialog(commentDialog.getContext());
//        Log.v("CommentX", view.getContext().toString());
//        Log.v("CommentX1", commentDialog.getContext().toString());
//        Log.v("CommentX2", commentDialog.getRecyclerView().getContext().toString());
//
//        loadingDialog.show();
        commentLoading = commentDialog.findViewById(R.id.loadingComment);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReplyIdGet = "";
                commentIdReply = "";
                if (commentDialog != null) {
                    commentDialog.getCommentEditText().requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    commentDialog.getCommentEditText().setHint(
                            "Thêm bình luận..."
                    );
                }
            }
        });

        return new CommentViewHolder(view, context);
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
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        TextView contentView = holder.contentView;
        LinearLayout commentReplyLayout = holder.commentReplyLayout;

        TextView replyView = holder.replyView;

        ImageView v1userpic = holder.v1userpic;

        TextView v1username = holder.v1username;

        TextView v1time = holder.v1time;

        TextView likeCntv1 = holder.likeCntv1;

        ImageButton likev1Button = holder.likev1Button;

        TextView ReplyV1 = holder.ReplyV1;

        ProgressBar commentV2Loading;
        Log.v("CommentX", holder.commentReplyLayout.toString() + " " + holder.getAdapterPosition() + " " + holder.getLayoutPosition() + " " + holder.getLayoutPosition());
        CommentObject commentObject = courseObjectList.get(holder.getAdapterPosition());
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        contentView.setText(commentObject.getContent());
        v1time.setText(changetoDate(commentObject.getTimestamp()));

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(commentObject.getUserId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        v1username.setText(value.getString("name"));
                        Picasso.get().load(value.getString("image")).into(v1userpic);
                    }
                });

        List<String> likeListV1 = commentObject.getLikeList();

        boolean currentLikeV1 = false;
        for (String userid: likeListV1) {
            if (userid.equals(currentUserId)) {
                currentLikeV1 = true;
                break;
            }
        }



        if (currentLikeV1) {
            likev1Button.setImageResource(R.drawable.ic_heart_filled);
            likev1Button.setTag(R.drawable.ic_heart_filled);
        } else {
            likev1Button.setImageResource(R.drawable.ic_heart);
            likev1Button.setTag(R.drawable.ic_heart);
        }


        likev1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likev1Button.getTag().equals(R.drawable.ic_heart_filled)) {
                    Log.v("Comment1", currentUserId);
                    likeListV1.remove(currentUserId);
                    likeCntv1.setText(likeListV1.size() - 1 + "");
                    likev1Button.setImageResource(R.drawable.ic_heart);
                    likev1Button.setTag(R.drawable.ic_heart);
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(commentObject.getId())
                            .update("like", likeListV1);
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(commentObject.getId())
                            .update("likeCnt", likeListV1.size() - 1);
                } else {
                    Log.v("Comment2", currentUserId);
                    likeListV1.add(currentUserId);
                    likeCntv1.setText(likeListV1.size() - 1 + "");
                    likev1Button.setImageResource(R.drawable.ic_heart_filled);
                    likev1Button.setTag(R.drawable.ic_heart_filled);
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(commentObject.getId())
                            .update("like", likeListV1);
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(commentObject.getId())
                            .update("likeCnt", likeListV1.size() - 1);
                }
            }
        });

        likeCntv1.setText(likeListV1.size() - 1 + "");

        ReplyV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReplyIdGet = commentObject.getUserId();
                commentIdReply = commentObject.getId();
                if (commentDialog != null) {
                    commentDialog.getCommentEditText().requestFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);

                    replyView.performClick();
                    commentDialog.getCommentEditText().setHint(
                            "Trả lời bình luận của " + holder.v1username.getText().toString()
                    );
                }
            }
        });

        FirebaseFirestore.getInstance()
                .collection("comments")
                .document(commentObject.getId())
                        .collection("replies").count().get(AggregateSource.SERVER)
                        .addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                AggregateQuerySnapshot snapshot = task.getResult();
//                                Log.v("CommentXY", holder.getAdapterPosition() + " " + position);
                                if (!commentReplyLayout.toString().equals(holder.commentReplyLayout.toString())) {
                                    Log.v("CommentXY", "Not equals");
                                }
                                if (snapshot.getCount() == 0) {
                                    replyView.setVisibility(View.GONE);
                                } else {
                                    replyView.setText("Xem " + snapshot.getCount() + " câu trả lời");
                                }
                            }
                        });


        holder.replyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replyText = holder.replyView.getText().toString();
                if (replyText.contains("Xem")) {
                    holder.replyView.setVisibility(View.GONE);
                    holder.commentV2Loading.setVisibility(View.VISIBLE);
                    holder.commentReplyLayout.setVisibility(View.GONE);
                    holder.commentReplyLayout.removeAllViews();
                    LayoutInflater factory = LayoutInflater.from(context);
                    List<CommentObject> commentv2Objects = new ArrayList<>();
                    FirebaseFirestore.getInstance().collection("comments")
                            .document(commentObject.getId())
                            .collection("replies")
                            .orderBy("timestamp")
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    for (DocumentChange doc: value.getDocumentChanges()){
                                        if (doc.getType() != DocumentChange.Type.ADDED) {
                                            continue;
                                        }
                                        DocumentSnapshot documentSnapshot = doc.getDocument();
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
                                                        TextView userReplyName = newView.findViewById(R.id.userReplyName);
                                                        TextView ReplyV2 = newView.findViewById(R.id.ReplyV2);

                                                        ReplyV2.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                userReplyIdGet = documentSnapshot.getString("userId");
                                                                commentIdReply = commentObject.getId();

                                                                if (commentDialog != null) {
                                                                    commentDialog.getCommentEditText().requestFocus();
                                                                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);

                                                                    commentDialog.getCommentEditText().setHint(
                                                                            "Trả lời bình luận của " + username.getText().toString()
                                                                    );
                                                                }
                                                            }
                                                        });

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
                                                                    cntLike.setText(likeList.size() - 1 + "");
                                                                    likeButton.setImageResource(R.drawable.ic_heart);
                                                                    likeButton.setTag(R.drawable.ic_heart);
                                                                    FirebaseFirestore.getInstance().collection("comments")
                                                                            .document(commentObject.getId())
                                                                            .collection("replies")
                                                                            .document(documentSnapshot.getId())
                                                                            .update("like", likeList);
                                                                    FirebaseFirestore.getInstance().collection("comments")
                                                                            .document(commentObject.getId())
                                                                            .update("likeCnt", likeList.size() - 1);
                                                                } else {
                                                                    likeList.add(currentUserId);
                                                                    cntLike.setText(likeList.size() - 1 + "");
                                                                    likeButton.setImageResource(R.drawable.ic_heart_filled);
                                                                    likeButton.setTag(R.drawable.ic_heart_filled);
                                                                    FirebaseFirestore.getInstance().collection("comments")
                                                                            .document(commentObject.getId())
                                                                            .collection("replies")
                                                                            .document(documentSnapshot.getId())
                                                                            .update("like", likeList);
                                                                    FirebaseFirestore.getInstance().collection("comments")
                                                                            .document(commentObject.getId())
                                                                            .update("likeCnt", likeList.size() - 1);

                                                                }
                                                            }
                                                        });

                                                        cntLike.setText(likeList.size() - 1 + "");



                                                        text.setText(documentSnapshot.getString("content"));
                                                        username.setText(value.getString("name"));
                                                        Picasso.get().load(value.getString("image")).into(userpic);
                                                        String date = changetoDate(documentSnapshot.getLong("timestamp"));
                                                        time.setText(date);

                                                        FirebaseFirestore.getInstance()
                                                                .collection("users")
                                                                .document(documentSnapshot.getString("userReplyId"))
                                                                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                                        userReplyName.setText(value.getString("name"));
                                                                    }
                                                                });

                                                        holder.commentReplyLayout.addView(newView);
                                                    }
                                                });

                                    }
                                    Handler handler = new Handler();
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            holder.commentV2Loading.setVisibility(View.GONE);
                                            holder.commentReplyLayout.setVisibility(View.VISIBLE);
                                        }
                                    };
                                    handler.postDelayed(runnable, 500);
                                }
                            });
                } else {
                    holder.replyView.setText("Xem câu trả lời");
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

        TextView ReplyV1;

        ProgressBar commentV2Loading;

        public CommentViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            contentView = itemView.findViewById(R.id.v1content);
            commentReplyLayout = itemView.findViewById(R.id.commentv2layout);
            replyView = itemView.findViewById(R.id.commentShowReply);
            v1userpic = itemView.findViewById(R.id.v1userpic);
            v1username = itemView.findViewById(R.id.v1username);
            v1time = itemView.findViewById(R.id.v1time);
            likeCntv1 = itemView.findViewById(R.id.likeCntv1);
            likev1Button = itemView.findViewById(R.id.likev1Button);
            ReplyV1 = itemView.findViewById(R.id.ReplyV1);
            commentV2Loading = itemView.findViewById(R.id.commentV2Loading);

        }
    }


}