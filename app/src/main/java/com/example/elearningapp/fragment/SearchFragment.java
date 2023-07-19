package com.example.elearningapp.fragment;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.MainActivity;
import com.example.elearningapp.activity.SearchActivity;
import com.example.elearningapp.adapter.SearchAdapter;
import com.example.elearningapp.object.PopularCategoryItem;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.flow.Flow;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    private GridView gridPopularSearch;

    private FlowLayout topSearchLayout;



    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        SearchAdapter searchAdapter = new SearchAdapter(this.getActivity(), getListPopularCategory());

        gridPopularSearch = rootView.findViewById(R.id.gridPopularView);
        gridPopularSearch.setAdapter(searchAdapter);

        topSearchLayout = rootView.findViewById(R.id.topSearchLayout);

        topSearchMake();

        clickBtnSearch();

        return rootView;
    }

    private int scaleDptoPx(float dp){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    private void topSearchMake() {
        if (topSearchLayout == null) {
            return;
        }

        topSearchLayout.removeAllViews();

        List<String> topSearchList = getListTopSearch();
        if (topSearchList != null && topSearchList.size() > 0) {
            for (int i = 0; i < topSearchList.size(); i++) {
                TextView textView = new TextView(this.getActivity());
                FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
                        FlowLayout.LayoutParams.WRAP_CONTENT,
                        FlowLayout.LayoutParams.WRAP_CONTENT
                        );
                params.setMargins(0,scaleDptoPx(5), scaleDptoPx(10), scaleDptoPx(5));
                String topSearchItem = topSearchList.get(i);
                textView.setLayoutParams(params);
                textView.setText(topSearchItem);
                textView.setPadding(scaleDptoPx(15), scaleDptoPx(5), scaleDptoPx(15), scaleDptoPx(5));
                textView.setBackgroundResource(R.drawable.custom_topsearch_item);
                textView.setTextColor(getResources().getColor(R.color.md_amber_600));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.gilroymedium));
                topSearchLayout.addView(textView);
            }
        }

    }

    private void clickBtnSearch() {
        TextView searchButton = (TextView) rootView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<String> getListTopSearch() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("Guitar");
        names.add("Thiết kế");
        names.add("Toán học");
        names.add("Hoạt cảnh");
        names.add("Học sâu");
        names.add("Thị giác máy tính");
        names.add("Học máy");
        names.add("Ngoại ngữ");
        names.add("Chính thức");
        names.add("Khuyên học");

        return names;
    }

    private List<PopularCategoryItem> getListPopularCategory() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> images = new ArrayList<Integer>();

        names.add("Ngoại ngữ");
        names.add("Marketing");
        names.add("Tin học văn phòng");
        names.add("Thiết kế");
        names.add("Kỹ năng mềm");
        names.add("Công nghệ thông tin");
        names.add("Học sâu");
        names.add("Thị giác máy tính");
        names.add("Toán học");
        names.add("Học máy");
        names.add("Chính thức");
        names.add("Khuyên học");

        images.add(R.drawable.a);
        images.add(R.drawable.b);
        images.add(R.drawable.c);
        images.add(R.drawable.d);
        images.add(R.drawable.b);
        images.add(R.drawable.d);
        images.add(R.drawable.c);
        images.add(R.drawable.a);
        images.add(R.drawable.c);
        images.add(R.drawable.d);
        images.add(R.drawable.c);
        images.add(R.drawable.a);

        List <PopularCategoryItem> popularCategoryItems = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            PopularCategoryItem popularCategoryItem = new PopularCategoryItem(names.get(i), images.get(i));
            popularCategoryItems.add(popularCategoryItem);
        }

        return popularCategoryItems;
    }
}