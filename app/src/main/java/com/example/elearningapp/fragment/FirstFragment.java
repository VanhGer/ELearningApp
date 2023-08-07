package com.example.elearningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CourseOverallActivity;
import com.example.elearningapp.adapter.FilterableTopCourseAdapter;
import com.example.elearningapp.interfaces.CourseClickHelper;
import com.example.elearningapp.object.CourseListItem;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment implements CourseClickHelper{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseFirestore firestore;
    FirebaseUser currentUser;
    RecyclerView topCourseRecyclerView;
    FilterableTopCourseAdapter topCourseAdapter;
    List<CourseListItem> courseListItemList = new ArrayList<>();
    View rootView;






    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_course_in_profile, container, false);

        topCourseRecyclerView = rootView.findViewById(R.id.yourCourseRecycler);
        topCourseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        topCourseAdapter = new FilterableTopCourseAdapter(getContext(), courseListItemList, clickHelper);
        topCourseRecyclerView.setAdapter(topCourseAdapter);

        loadDataFromFirestore();
//        FilterableTopCourseAdapter adapterInstance = new FilterableTopCourseAdapter(requireContext(), courseListItemList, (CourseClickHelper) this);
//        adapterInstance.filterById("Bùi Tuấn Dũng");
        return rootView;
    }

    private void loadDataFromFirestore() {
        String userNameToSearch = "kxbPW27DzYQnlayRxkgPebGadJi2";
        Query query = FirebaseFirestore.getInstance().collection("courses")
                .whereEqualTo("owner", userNameToSearch);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                courseListItemList.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    courseListItemList.add(
                            new CourseListItem(
                                    document.getId(),
                                    document.getString("image"),
                                    document.getString("name"),
                                    document.getString("owner"),
                                    document.getString("description"),
                                    document.getDouble("students").intValue(),
                                    document.getDouble("star")));
                }

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        topCourseRecyclerView.setVisibility(View.VISIBLE);
                    }
                };
                handler.postDelayed(runnable, 1000);

                topCourseAdapter.notifyDataSetChanged();
            }
        });
    }

    CourseClickHelper clickHelper = new CourseClickHelper() {
        @Override
        public void onItemClick(String id) {
            Intent overallActivity = new Intent(getContext(), CourseOverallActivity.class);
            overallActivity.putExtra("courseId", id);
            startActivity(overallActivity);
        }
    };


    @Override
    public void onItemClick(String id) {
        Intent overallActivity = new Intent(getContext(), CourseOverallActivity.class);
        overallActivity.putExtra("courseId", id);
        startActivity(overallActivity);
    }
}