package com.example.elearningapp.lessonType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.AnswerAdapter;
import com.example.elearningapp.adapter.TestAdapter;
import com.example.elearningapp.item.LessonItem;
import com.example.elearningapp.item.QuestionItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class testLesson extends AppCompatActivity {
    TextView title, result;
    Button submit, nxt, pre;
    ImageButton back;
    RecyclerView recyclerView;
    TestAdapter testAdapter;
    AnswerAdapter answerAdapter;
    List<QuestionItem> questionItemList = new ArrayList<>();

    int correct = 0;
    int total = 1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String courseId;
    String lessonId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_test);


        title = findViewById(R.id.lesson_title2);
        submit = findViewById(R.id.submitBtn);
        nxt = findViewById(R.id.btn_next_lesson);
        pre = findViewById(R.id.btn_pre_lesson);
        result = findViewById(R.id.result);
        recyclerView = findViewById(R.id.questionList);
        back = findViewById(R.id.return_btn);

        setLesson();
    }

    void setLesson() {
        List<LessonItem> lessonItem = (List<LessonItem>) getIntent().getSerializableExtra("lesson");
        int position = getIntent().getIntExtra("position", 0);
        int maxPosition = getIntent().getIntExtra("maxPosition", 0);
        courseId = getIntent().getStringExtra("courseId");
        lessonId = lessonItem.get(position).getLessonId();

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        testAdapter = new TestAdapter(getApplicationContext(), questionItemList);
        recyclerView.setAdapter(testAdapter);
        title.setText(lessonItem.get(position).getName());

        CollectionReference colRef = db.collection("courses").document(courseId).
                                    collection("lessons").document(lessonItem.get(position).getLessonId()).
                                    collection("question");

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    questionItemList.add(new QuestionItem(document.getId(), lessonItem.get(position).getLessonId(),
                            document.getString("topic"), document.getString("choice1"), document.getString("choice2"),
                            document.getString("choice3"), document.getString("choice4"), document.getDouble("answer").intValue()));
                }
                testAdapter.notifyDataSetChanged();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Boolean> ansRes = getResult();
                List<String> ansList = getAnswerList();
                answerAdapter = new AnswerAdapter(getApplicationContext(), ansRes, ansList);
                result.setVisibility(View.VISIBLE);
                pre.setVisibility(View.VISIBLE);
                nxt.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(answerAdapter);
                if (4 * correct >= 3 * total) {
                    FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                            .collection("learn").document(courseId).update(lessonId, true);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testLesson.this.onBackPressed();
            }
        });
        if (position > 0) {
            pre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonOnClick(position - 1, maxPosition, lessonItem);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }
        if (position != maxPosition - 1) {
            nxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonOnClick(position + 1, maxPosition, lessonItem);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }



    }

    List <String> getAnswerList() {
        List<String> ansList = new ArrayList<>();
        for (int i = 0; i <  questionItemList.size(); i++) {
            ansList.add(questionItemList.get(i).getAnswer());
        }
        return ansList;
    }

    List<Boolean> getResult() {
        total = questionItemList.size();
        correct = 0;
        List<Boolean> ansList = new ArrayList<Boolean>();
        for(int i =0; i < recyclerView.getAdapter().getItemCount();i++){
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if(holder == null){
                TestAdapter tmp = (TestAdapter) recyclerView.getAdapter();
                holder = tmp.getHolder(i);
            }
            if (holder == null) {
                ansList.add(false);
                continue;
            }
            TestAdapter.TestViewHolder testHolder = (TestAdapter.TestViewHolder) holder;
            int selected = testHolder.radioGroup.getCheckedRadioButtonId();
            if (selected == testHolder.choice1.getId()) selected = 1;
            else if (selected == testHolder.choice2.getId()) selected = 2;
            else if (selected == testHolder.choice3.getId()) selected = 3;
            else if (selected == testHolder.choice4.getId()) selected = 4;

            int ans = questionItemList.get(i).getAnswerId();
            if (ans == selected) {
                correct++;
                ansList.add(true);
            } else {
                ansList.add(false);
            }
        }

        String res = "Kết quả: " + correct + "/" + total;
        result.setText(res);
        return ansList;
    }

    void buttonOnClick(int position, int maxPosition, List<LessonItem> lessonItemList) {
        if (lessonItemList.get(position).getType().equals("video")) {
            Intent intent = new Intent(testLesson.this, videoLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
            finish();
        } else if (lessonItemList.get(position).getType().equals("text")) {
            Intent intent = new Intent(testLesson.this, textLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
            finish();
        } else if (lessonItemList.get(position).getType().equals("test")) {
            Intent intent = new Intent(testLesson.this, testLesson.class);
            intent.putExtra("lesson", (Serializable) lessonItemList);
            intent.putExtra("position", position);
            intent.putExtra("maxPosition", maxPosition);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
            finish();
        }
    }
}