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

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    Context context;
    List<QuestionItem> questionItemList;

    public AnswerAdapter(Context context, List<QuestionItem> questionItemList) {
        this.context = context;
        this.questionItemList = questionItemList;
    }

    @NonNull
    @Override
    public AnswerAdapter.AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lesson_question_answer, parent, false);
        return new AnswerAdapter.AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.AnswerViewHolder holder, int position) {
        holder.questionNum.setText("Cau hoi: " + position);
        holder.answer.setText(questionItemList.get(position).getAnswerId());
    }

    @Override
    public int getItemCount() {
        return questionItemList.size();
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView questionNum;
        TextView answer;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNum = itemView.findViewById(R.id.question_num2);
            answer = itemView.findViewById(R.id.answer);

        }
    }
}
