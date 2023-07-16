package com.example.elearningapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.fragment.SearchFragment;
import com.example.elearningapp.object.PopularCategoryItem;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<PopularCategoryItem> popularCategoryItemList;

    LayoutInflater inflater;

    public SearchAdapter(Context context, List<PopularCategoryItem> popularCategoryItemList) {
        this.context = context;
        this.popularCategoryItemList = popularCategoryItemList;
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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

        imageView.setImageResource(popularCategoryItemList.get(position).getImage());
        textView.setText(popularCategoryItemList.get(position).getName());

        return convertView;
    }
}
