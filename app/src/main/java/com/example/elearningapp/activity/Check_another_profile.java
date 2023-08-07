package com.example.elearningapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elearningapp.R;
import com.example.elearningapp.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class Check_another_profile extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_prf);
//        String courseId = getIntent().getStringExtra("courseId");
//        String ownerName = getIntent().getStringExtra("authorName");
//
//        Log.v("CourseOverallActivity", "Owner Name: " + ownerName);
//
//        TextView username = findViewById(R.id.textView80);
//        username.setText(ownerName);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        tabLayout.addTab(tabLayout.newTab().setText("thông tin chính"));
        tabLayout.addTab(tabLayout.newTab().setText("thông tin course"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter= new FragmentAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }}