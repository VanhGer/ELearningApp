package com.example.elearningapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CategoryActivity;
import com.example.elearningapp.object.PopularCategoryItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<PopularCategoryItem> popularCategoryItemList;

    LayoutInflater inflater;

    Activity activity;

    public CategoryAdapter(Context context, List<PopularCategoryItem> popularCategoryItemList, Activity activity) {
        this.context = context;
        this.popularCategoryItemList = popularCategoryItemList;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        if (popularCategoryItemList != null) {
            return  popularCategoryItemList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.popular_search_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.grid_search_image);
        TextView textView = convertView.findViewById(R.id.grid_search_text);
        Picasso.get().load(popularCategoryItemList.get(position).getImage()).into(imageView);
//        imageView.setImageResource(popularCategoryItemList.get(position).getImage());
        textView.setText(popularCategoryItemList.get(position).getName());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CategoryActivity.class);
                intent.putExtra("CategoryID", popularCategoryItemList.get(position).getId());
                activity.startActivity(intent);
            }
        });

        return convertView;
    }
}