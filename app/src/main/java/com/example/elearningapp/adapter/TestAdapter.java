package com.example.elearningapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.courseItem.LessonItem;
import com.example.elearningapp.courseItem.QuestionItem;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    Context context;
    List<QuestionItem> questionItemList;

    public TestAdapter(Context context, List<QuestionItem> questionItemList) {
        this.context = context;
        this.questionItemList = questionItemList;
    }

    @NonNull
    @Override
    public TestAdapter.TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lesson_question_item, parent, false);
        return new TestAdapter.TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.TestViewHolder holder, int position) {
        holder.questionNum.setText("Cau so " + (position + 1));
        holder.questionTitle.setText(questionItemList.get(position).getContent());
        holder.choice1.setText(questionItemList.get(position).getChoice1());
        holder.choice2.setText(questionItemList.get(position).getChoice2());
        holder.choice3.setText(questionItemList.get(position).getChoice3());
        holder.choice4.setText(questionItemList.get(position).getChoice4());
    }

    @Override
    public int getItemCount() {
        return questionItemList.size();
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder {
        TextView questionNum, questionTitle;
        RadioButton choice1, choice2, choice3, choice4;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNum = itemView.findViewById(R.id.question_num);
            questionTitle = itemView.findViewById(R.id.questionTitle);

            choice1 = itemView.findViewById(R.id.choice1);
            choice2 = itemView.findViewById(R.id.choice2);
            choice3 = itemView.findViewById(R.id.choice3);
            choice4 = itemView.findViewById(R.id.choice4);
        }
    }
}
