package com.example.elearningapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elearningapp.activity.CourseOverallActivity;
import com.example.elearningapp.interfaces.CourseClickHelper;
import com.example.elearningapp.interfaces.LessonClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.adapter.TopCourseAdapter;
import com.example.elearningapp.object.CourseListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment implements CourseClickHelper {




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseAuth mAuth;
    private View rootView;
    private RecyclerView recyclerView;
    private List<CourseListItem> courseListItemList = new ArrayList<>();
    TopCourseAdapter topCourseAdapter;

    FirebaseFirestore db;


    public BookmarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookmarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarkFragment newInstance(String param1, String param2) {
        BookmarkFragment fragment = new BookmarkFragment();
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
        rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);
        init(rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        topCourseAdapter = new TopCourseAdapter(this.getContext(), courseListItemList, this);
        recyclerView.setAdapter(topCourseAdapter);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        loadDataFromFirestore(currentUser.getUid(), currentUser.getDisplayName());
        return  rootView;

    }

    void init(View rootView) {
        recyclerView = rootView.findViewById(R.id.course_list_view);
    }

    private void loadDataFromFirestore(String userId, String userName) {
        CollectionReference colRef = FirebaseFirestore.getInstance().collection("users").
                document(userId).collection("courses");

        colRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {



                for (DocumentSnapshot document : value.getDocuments()) {
                    String courseId = document.getString("courseId");
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("courses").document(courseId);
                    courseListItemList.clear();
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            courseListItemList.add(
                                    new CourseListItem(
                                            document.getId(),
                                            document.getString("image"),
                                            document.getString("name")
                                            , userName, document.getString("description"),
                                            document.getDouble("students").intValue(),
                                            document.getDouble("star")));
                            topCourseAdapter.notifyDataSetChanged();
                        }
                    });
                }

            }
        });
    }
    @Override
    public void onItemClick(String id) {
        Intent overallActivity = new Intent(this.getContext(), CourseOverallActivity.class);
        overallActivity.putExtra("courseId", id);
        startActivity(overallActivity);
    }
}