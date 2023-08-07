package com.example.elearningapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.elearningapp.R;
import com.example.elearningapp.item.Vote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


public class VoteActivity extends AppCompatActivity {
    private int currentVote = 0;
    ImageView star1, star2, star3, star4, star5;
    Button confirmButton, cancelButton;
    EditText voteComment;
    FirebaseFirestore db;
    private String courseId;
    ImageView voteCourseImg;
    TextView voteCourseName, voteCourseOwner;
    Double currentStar;
    Double voteNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);

        courseId = getIntent().getStringExtra("courseId");
        db = FirebaseFirestore.getInstance();
        voteCourseImg = findViewById(R.id.voteCourseImg);
        voteCourseName = findViewById(R.id.voteCourseName);
        voteCourseOwner = findViewById(R.id.voteCourseAuthor);

        db.collection("courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    voteCourseName.setText(document.getString("name"));
                    db.collection("users").document(document.getString("owner")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                voteCourseOwner.setText((document.getString("name")));
                            }
                        }
                    });
                    Picasso.get().load(document.getString("image")).into(voteCourseImg);
                } else {
                    Toast.makeText(getApplicationContext(), "Hãy kiểm tra lại kết nối của bạn", Toast.LENGTH_SHORT);
                }
            }
        });

        //DialogFragment dialog = new voteDialog();
        //dialog.show(getSupportFragmentManager(), "voteDialogTag");

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star1);
                star2.setImageResource(R.drawable.ic_star);
                star3.setImageResource(R.drawable.ic_star);
                star4.setImageResource(R.drawable.ic_star);
                star5.setImageResource(R.drawable.ic_star);
                currentVote = 1;
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star1);
                star2.setImageResource(R.drawable.star1);
                star3.setImageResource(R.drawable.ic_star);
                star4.setImageResource(R.drawable.ic_star);
                star5.setImageResource(R.drawable.ic_star);
                currentVote = 2;
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star1);
                star2.setImageResource(R.drawable.star1);
                star3.setImageResource(R.drawable.star1);
                star4.setImageResource(R.drawable.ic_star);
                star5.setImageResource(R.drawable.ic_star);
                currentVote = 3;
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star1);
                star2.setImageResource(R.drawable.star1);
                star3.setImageResource(R.drawable.star1);
                star4.setImageResource(R.drawable.star1);
                star5.setImageResource(R.drawable.ic_star);
                currentVote = 4;
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star1);
                star2.setImageResource(R.drawable.star1);
                star3.setImageResource(R.drawable.star1);
                star4.setImageResource(R.drawable.star1);
                star5.setImageResource(R.drawable.star1);
                currentVote = 5;
            }
        });

        confirmButton = findViewById(R.id.confirmButton);
        voteComment = findViewById(R.id.voteComment);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = String.valueOf(voteComment.getText());
                if (currentVote == 0) {
                    Toast.makeText(getApplicationContext(), "Hãy đánh giá từ 1 sao đến 5 sao", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Đánh giá khóa học thành công", Toast.LENGTH_SHORT).show();
                    Vote vote = new Vote(currentVote, comment, FirebaseAuth.getInstance().getCurrentUser().getUid());
                    db.collection("courses").document(courseId).collection("votes").add(vote);
                    db.collection("courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                currentStar = document.getDouble("star");
                                voteNumber = document.getDouble("vote");
                                Double newVote = voteNumber + 1;
                                db.collection("courses").document(courseId).update("vote", newVote);
                                Double newStar = (currentStar * voteNumber + currentVote) / newVote;
                                db.collection("courses").document(courseId).update("star", newStar);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Đã huỷ bỏ đánh giá", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public class voteDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Đánh giá khóa học");
            builder.setMessage("Bạn phải học ít nhất 20% khóa học mới được phép đánh giá!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // You don't have to do anything here if you just
                    // want it dismissed when clicked
                }
            });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
