package com.example.elearningapp.fragment;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

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

        clickBtnSearch();

        return rootView;
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

    private List<PopularCategoryItem> getListPopularCategory() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> images = new ArrayList<Integer>();

        names.add("Animation");
        names.add("Graphic Design");
        names.add("Film");
        names.add("Photography");
        names.add("Illustration");
        names.add("Crafts");
        names.add("Deep Learning");
        names.add("Computer Vision");
        names.add("Mathematics");
        names.add("Machine Learning");
        names.add("Originals");
        names.add("Staff Picks");

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