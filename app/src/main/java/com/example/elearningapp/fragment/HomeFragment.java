package com.example.elearningapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elearningapp.R;
import com.example.elearningapp.activity.CourseOverallActivity;
import com.example.elearningapp.activity.TopCourseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView titleView = rootView.findViewById(R.id.greeting1);

        ImageView imageView = rootView.findViewById(R.id.profilePic);

        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users").document(Uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    titleView.setText("Xin chào, " + document.getString("name") + "!");
                    Picasso.get().load(document.getString("image")).into(imageView);
                }
            }
        });

        TextView seeMore1 = rootView.findViewById(R.id.student);

        seeMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TopCourseActivity.class);
                intent.putExtra("title", "Khóa học được theo dõi nhiều nhất");
                startActivity(intent);
            }
        });

        TextView seeMore2 = rootView.findViewById(R.id.seeMore2);

        seeMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TopCourseActivity.class);
                intent.putExtra("title", "Tiếp tục xem các khóa học");
                startActivity(intent);
            }
        });

        TextView seeMore3 = rootView.findViewById(R.id.seeMore3);

        seeMore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TopCourseActivity.class);
                intent.putExtra("title", "Các khóa học bạn có thể thích");
                startActivity(intent);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ImageView mostViewedCourse1 = rootView.findViewById(R.id.mostViewedCourse1);

        mostViewedCourse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CourseOverallActivity.class);
                startActivity(intent);
            }
        });

        ImageView mostViewedCourse2 = rootView.findViewById(R.id.mostViewedCourse2);

        mostViewedCourse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CourseOverallActivity.class);
                startActivity(intent);
            }
        });
        TextView mostViewedCourseName1 = rootView.findViewById(R.id.mostViewedCourseName1);
        TextView mostViewedCourseName2 = rootView.findViewById(R.id.mostViewedCourseName2);
        TextView mostViewedCourseTeacher1 = rootView.findViewById(R.id.mostViewedCourseTeacher1);
        TextView mostViewedCourseTeacher2 = rootView.findViewById(R.id.mostViewedCourseTeacher2);
        TextView mostViewedCourseStar1 = rootView.findViewById(R.id.mostViewedCourseStar1);
        TextView mostViewedCourseStar2 = rootView.findViewById(R.id.mostViewedCourseStar2);
        TextView mostViewedCourseStudent1 = rootView.findViewById(R.id.mostViewedCourseStudent1);
        TextView mostViewedCourseStudent2 = rootView.findViewById(R.id.mostViewedCourseStudent2);
        db.collection("courses").orderBy("students", Query.Direction.DESCENDING).limit(2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int cur = 1;
                    for (DocumentSnapshot document: task.getResult()) {
                        if (cur == 1) {
                            mostViewedCourseName1.setText(document.getString("name"));
                            //mostViewedCourseTeacher1.setText(document.getString("owner"));
                            mostViewedCourseStar1.setText(document.getDouble("star") + "⭐");
                            mostViewedCourseStudent1.setText("(" + document.getLong("students") + ")");
                            Picasso.get().load(document.getString("image")).into(mostViewedCourse1);
                        }
                        else {
                            mostViewedCourseName2.setText(document.getString("name"));
                            //mostViewedCourseTeacher2.setText(document.getString("owner"));
                            mostViewedCourseStar2.setText(document.getDouble("star") + "⭐");
                            mostViewedCourseStudent2.setText("(" + document.getLong("students") + ")");
                            Picasso.get().load(document.getString("image")).into(mostViewedCourse2);
                        }
                        cur++;
                    }
                }
            }
        });


        ImageView continueCourse1 = rootView.findViewById(R.id.continueCourse1);

        continueCourse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CourseOverallActivity.class);
                startActivity(intent);
            }
        });

        ImageView continueCourse2 = rootView.findViewById(R.id.continueCourse2);

        continueCourse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CourseOverallActivity.class);
                startActivity(intent);
            }
        });

        ImageView mayLikeCourse1 = rootView.findViewById(R.id.mayLikeCourse1);

        mayLikeCourse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CourseOverallActivity.class);
                startActivity(intent);
            }
        });

        ImageView mayLikeCourse2 = rootView.findViewById(R.id.mayLikeCourse2);

        mayLikeCourse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CourseOverallActivity.class);
                startActivity(intent);
            }
        });
        TextView mayLikeCourseName1 = rootView.findViewById(R.id.mayLikeCourseName1);
        TextView mayLikeCourseName2 = rootView.findViewById(R.id.mayLikeCourseName2);
        TextView mayLikeCourseTeacher1 = rootView.findViewById(R.id.mayLikeCourseTeacher1);
        TextView mayLikeCourseTeacher2 = rootView.findViewById(R.id.mayLikeCourseTeacher2);
        TextView mayLikeCourseStar1 = rootView.findViewById(R.id.mayLikeCourseStar1);
        TextView mayLikeCourseStar2 = rootView.findViewById(R.id.mayLikeCourseStar2);
        TextView mayLikeCourseStudent1 = rootView.findViewById(R.id.mayLikeCourseStudent1);
        TextView mayLikeCourseStudent2 = rootView.findViewById(R.id.mayLikeCourseStudent2);
        db.collection("courses").orderBy("star", Query.Direction.DESCENDING).limit(2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int cur = 1;
                    for (DocumentSnapshot document: task.getResult()) {
                        if (cur == 1) {
                            mayLikeCourseName1.setText(document.getString("name"));
                            //mayLikeCourseTeacher1.setText(document.getString("owner"));
                            mayLikeCourseStar1.setText(document.getDouble("star") + "⭐");
                            mayLikeCourseStudent1.setText("(" + document.getLong("students") + ")");
                            Picasso.get().load(document.getString("image")).into(mayLikeCourse1);
                        }
                        else {
                            mayLikeCourseName2.setText(document.getString("name"));
                            //mayLikeCourseTeacher2.setText(document.getString("owner"));
                            mayLikeCourseStar2.setText(document.getDouble("star") + "⭐");
                            mayLikeCourseStudent2.setText("(" + document.getLong("students") + ")");
                            Picasso.get().load(document.getString("image")).into(mayLikeCourse2);
                        }
                        cur++;
                    }
                }
            }
        });

        return rootView;
    }
}