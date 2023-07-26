package com.example.elearningapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.fragment.SearchFragment;
import com.example.elearningapp.object.PopularCategoryItem;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private List<PopularCategoryItem> popularCategoryItemList;

    LayoutInflater inflater;

//    private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public SearchAdapter(Context context, List<PopularCategoryItem> popularCategoryItemList) {
        this.context = context;
        this.popularCategoryItemList = popularCategoryItemList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popular_search_item, parent, false);
//        view.setOnClickListener(mOnClickListener);
        return new SearchAdapter.SearchViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
//        holder.imageView.setImageResource(popularCategoryItemList.get(position).getImage());
        holder.categoryName.setText(popularCategoryItemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return popularCategoryItemList.size();
    }


    public static class SearchViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView imageView;
        TextView categoryName;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grid_search_image);
            categoryName = itemView.findViewById(R.id.grid_search_text);
        }
    }
}
