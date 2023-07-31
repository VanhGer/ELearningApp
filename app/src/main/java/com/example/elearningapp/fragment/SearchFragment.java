package com.example.elearningapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.SearchActivity;
import com.example.elearningapp.adapter.CategoryAdapter;
import com.example.elearningapp.object.PopularCategoryItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.wefika.flowlayout.FlowLayout;

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

    private FlowLayout topSearchLayout;

    FirebaseFirestore db;

    DocumentReference reference;

    ProgressDialog TemDialog;

    private CategoryAdapter searchAdapter;

    private List<PopularCategoryItem> popularCategoryItemList = new ArrayList<>();
    private List<String> topSearchList = new ArrayList<>();

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

        init(rootView);

        searchAdapter = new CategoryAdapter(this.getActivity(), popularCategoryItemList);
        gridPopularSearch.setAdapter(searchAdapter);


        topSearchMake();

        clickBtnSearch();

        loadDataFromFirestore();

        return rootView;
    }

    private void init(View view) {
        gridPopularSearch = rootView.findViewById(R.id.gridPopularView);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);



//        gridPopularSearch.setLayoutManager(new GridLayoutManager(getActivity(), 2,
//                LinearLayoutManager.VERTICAL, false));

        topSearchLayout = rootView.findViewById(R.id.topSearchLayout);

//        FirebaseAuth auth  = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
    }


    private int scaleDptoPx(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void loadDataFromFirestore() {
        CollectionReference categoryRef = FirebaseFirestore.getInstance().collection("categories");
        categoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                popularCategoryItemList.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    popularCategoryItemList.add(
                            new PopularCategoryItem(
                                    document.getString("name"),
                                    document.getString("image")));
                    Log.v("Firebase", document.getString("name"));
                }
                searchAdapter.notifyDataSetChanged();
            }
        });
    }


    private void topSearchMake() {
        if (topSearchLayout == null) {
            return;
        }

        CollectionReference topSearchRef = FirebaseFirestore.getInstance().collection("topsearches");
        topSearchRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                topSearchLayout.removeAllViews();
                for (DocumentSnapshot document : value.getDocuments()) {
                    TextView textView = new TextView(getActivity());
                    FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
                            FlowLayout.LayoutParams.WRAP_CONTENT,
                            FlowLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, scaleDptoPx(5), scaleDptoPx(10), scaleDptoPx(5));
                    String topSearchItem = document.getString("name");
                    textView.setLayoutParams(params);
                    textView.setText(topSearchItem);
                    textView.setPadding(scaleDptoPx(15), scaleDptoPx(5), scaleDptoPx(15), scaleDptoPx(5));
                    textView.setBackgroundResource(R.drawable.custom_topsearch_item);
                    textView.setTextColor(getResources().getColor(R.color.md_blue_300));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    textView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.gilroymedium));
                    topSearchLayout.addView(textView);
                    Log.v("Firebase", document.getString("name"));
                }
            }
        });

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
}