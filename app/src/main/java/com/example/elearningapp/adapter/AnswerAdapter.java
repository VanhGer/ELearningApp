package com.example.elearningapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    Context context;

    List<String> ansList;
    List<Boolean> ansRes;

    public AnswerAdapter(Context context, List<Boolean> ansRes, List<String> ansList) {
        this.context = context;
        this.ansList = ansList;
        this.ansRes = ansRes;
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
        Log.w("positionn", position + "");
        if (ansRes.get(position)) {
            holder.answer.setTextColor(Color.parseColor("#FF558B2F"));
            holder.answer.setText("Chính xác! ");
        }
        else {
            holder.answer.setTextColor(Color.parseColor("#FFC62828"));
            holder.answer.setText("Sai!");
        }
    }

    @Override
    public int getItemCount() {
        return ansList.size();
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
