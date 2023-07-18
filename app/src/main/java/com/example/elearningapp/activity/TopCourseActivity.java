package com.example.elearningapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.elearningapp.ClickHelper;
import com.example.elearningapp.R;
import com.example.elearningapp.adapter.TopCourseAdapter;
import com.example.elearningapp.object.CourseListItem;
import com.example.elearningapp.object.PopularCategoryItem;

import java.util.ArrayList;
import java.util.List;

public class TopCourseActivity extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView topCourseRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_course);

        getViews();

        backBtnClick();
        setRecycler();
    }


    private void getViews() {
        backBtn = findViewById(R.id.backBtnTopCourse);
        topCourseRecyclerView = findViewById(R.id.topCourseRecycler);
    }

    private void setRecycler() {
        if (topCourseRecyclerView == null) {
            return;
        }
        topCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topCourseRecyclerView.setAdapter(new TopCourseAdapter(getApplicationContext(), getListTopCouses()));
    }

    private void backBtnClick() {
        if (backBtn == null) {
            return;
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<CourseListItem> getListTopCouses() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> owners = new ArrayList<String>();
        ArrayList<Integer> images = new ArrayList<Integer>();
        ArrayList<Integer> numberStudents = new ArrayList<Integer>();
        ArrayList<Double> numberStars = new ArrayList<Double>();

        names.add("Adobe illustrator for all beginner artist");
        names.add("Digital illustration technique for procrete");
        names.add("Learn how to draw cartoon face in easy way!");
        names.add("Sketch book essential for everyone!");
        names.add("Adobe illustrator for all beginner artist");
        names.add("Digital illustration technique for procrete");
        names.add("Learn how to draw cartoon face in easy way!");
        names.add("Sketch book essential for everyone!");

        owners.add("Sarrah Morry");
        owners.add("Sarrah Morry");
        owners.add("Sarrah Morry");
        owners.add("Sarrah Morry");
        owners.add("Sarrah Morry");
        owners.add("Sarrah Morry");
        owners.add("Sarrah Morry");
        owners.add("Sarrah Morry");


        images.add(R.drawable.a);
        images.add(R.drawable.b);
        images.add(R.drawable.c);
        images.add(R.drawable.d);
        images.add(R.drawable.a);
        images.add(R.drawable.b);
        images.add(R.drawable.c);
        images.add(R.drawable.d);

        numberStars.add(3.4);
        numberStars.add(5.0);
        numberStars.add(2.3);
        numberStars.add(1.2);
        numberStars.add(3.4);
        numberStars.add(5.0);
        numberStars.add(2.3);
        numberStars.add(1.2);

        numberStudents.add(1000);
        numberStudents.add(123);
        numberStudents.add(121);
        numberStudents.add(111);
        numberStudents.add(1000);
        numberStudents.add(123);
        numberStudents.add(121);
        numberStudents.add(111);


        List <CourseListItem> topCourseItems = new ArrayList<>();
        for (int i = 0; i < numberStudents.size(); i++) {
            CourseListItem topCourseItem = new CourseListItem(images.get(i), names.get(i), owners.get(i), numberStudents.get(i), numberStars.get(i));
            topCourseItems.add(topCourseItem);
        }

        return topCourseItems;
    }
}

