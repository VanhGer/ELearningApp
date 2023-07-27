package com.example.elearningapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.item.QuestionItem;

import java.util.HashMap;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    Context context;
    List<QuestionItem> questionItemList;
    public HashMap<Integer, RecyclerView.ViewHolder> holderHashMap = new HashMap<>();


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
        holder.questionNum.setText("Câu số " + (position + 1));
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

    @Override
    public void onViewDetachedFromWindow(@NonNull TestViewHolder holder) {
        holderHashMap.put(holder.getAdapterPosition(),holder);
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull TestViewHolder holder) {
        holderHashMap.remove(holder.getAdapterPosition());
        super.onViewAttachedToWindow(holder);
    }

    public RecyclerView.ViewHolder getHolder(int i) {
        return holderHashMap.get(i);
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder {
        public TextView questionNum, questionTitle;
        public RadioButton choice1, choice2, choice3, choice4;

        public RadioGroup radioGroup;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNum = itemView.findViewById(R.id.question_num);
            questionTitle = itemView.findViewById(R.id.questionTitle);
            radioGroup = itemView.findViewById(R.id.choiceGroup);
            choice1 = itemView.findViewById(R.id.choice1);
            choice2 = itemView.findViewById(R.id.choice2);
            choice3 = itemView.findViewById(R.id.choice3);
            choice4 = itemView.findViewById(R.id.choice4);
        }
    }
}
