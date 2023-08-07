package com.example.elearningapp.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.elearningapp.fragment.FirstFragment;
import com.example.elearningapp.fragment.SecondFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    String userId;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String userId) {
        super(fragmentManager, lifecycle);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return new  FirstFragment();
        }
        return new SecondFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
