package com.example.elearningapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.courseItem.QuestionItem;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    Context context;
    List<QuestionItem> questionItemList;

    List<Boolean> ansList;

    public AnswerAdapter(Context context, List<QuestionItem> questionItemList, List<Boolean> ansList) {
        this.context = context;
        this.questionItemList = questionItemList;
        this.ansList = ansList;
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
        holder.questionNum.setText("Câu số: " + (position + 1));
        if (ansList.get(position)) {
            holder.answer.setTextColor(Color.parseColor("#FF558B2F"));
            holder.answer.setText("Chính xác! " + "Đáp án là: " + questionItemList.get(position).getAnswer());
        }
        else {
            holder.answer.setTextColor(Color.parseColor("#FFC62828"));
            holder.answer.setText("Sai! Đáp án đúng là: " + questionItemList.get(position).getAnswer());
        }
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
