package com.example.elearningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.elearningapp.R;
import com.example.elearningapp.fragment.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseUser user;

    private Button button_done_update_profile;
    private Button button_download;
    private Button button_help_and_support;
    private Button button_about_app;
     Button button_log_out_setting;
    private int currentLayout;

    private ImageButton imageButton_back_change_user_profile;
    private Button button_update_profile;
    private ImageButton imageButton_back_history_of_download;
    private ImageButton imageButton_back_help_and_support;
    private Button Button_back_readme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        currentLayout = R.layout.setting;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Log.v("Firebase", "StartAct");


        if(user == null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        updateLayout(currentLayout);

    }

    private void updateLayout(int layoutId) {
        setContentView(layoutId);
        setClickListeners();

    }

    private void setClickListeners() {
        if(currentLayout == R.layout.setting){
            button_update_profile =findViewById(R.id.button_update_profile);
            button_download = findViewById(R.id.button_download);
            button_help_and_support = findViewById(R.id.button_help_and_support);
            button_about_app = findViewById(R.id.button_about_app);
            button_log_out_setting = findViewById(R.id.button_log_out_setting);
            button_update_profile.setOnClickListener(this);
            button_download.setOnClickListener(this);
            button_help_and_support.setOnClickListener(this);
            button_about_app.setOnClickListener(this);
            button_log_out_setting.setOnClickListener(this);


        } else if (currentLayout == R.layout.change_user_profle) {
            imageButton_back_change_user_profile =findViewById(R.id.imageButton_back_change_user_profile);
            imageButton_back_change_user_profile.setOnClickListener(this);
            button_done_update_profile =findViewById(R.id.button_done_update_profile);
            button_done_update_profile.setOnClickListener(this);
        } else if (currentLayout == R.layout.history_of_download) {
            imageButton_back_history_of_download =findViewById(R.id.imageButton_back_history_of_download);
            imageButton_back_history_of_download.setOnClickListener(this);
        } else if (currentLayout == R.layout.help_and_support) {
            imageButton_back_help_and_support =findViewById(R.id.imageButton_back_help_and_support);
            imageButton_back_help_and_support.setOnClickListener(this);
        } else if (currentLayout == R.layout.readme) {
            Button_back_readme =findViewById(R.id.Button_back_readme);
            Button_back_readme.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View view) {
        if (currentLayout == R.layout.setting) {
            if (view.getId() == R.id.button_update_profile) {
                currentLayout = R.layout.change_user_profle;
                updateLayout(currentLayout);
            } else if (view.getId() == R.id.button_download) {
                currentLayout = R.layout.history_of_download;
                updateLayout(currentLayout);
            } else if (view.getId() == R.id.button_help_and_support) {
                currentLayout = R.layout.help_and_support;
                updateLayout(currentLayout);
            } else if (view.getId() == R.id.button_about_app) {
                currentLayout = R.layout.readme;
                updateLayout(currentLayout);
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//                finish();
            } else if (view.getId() == R.id.button_log_out_setting) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (currentLayout == R.layout.history_of_download) {
            if (view.getId() == R.id.imageButton_back_history_of_download) {
                currentLayout = R.layout.setting;
                updateLayout(currentLayout);
            }
        } else if (currentLayout == R.layout.change_user_profle) {
            if (view.getId() == R.id.imageButton_back_change_user_profile) {
                currentLayout = R.layout.setting;
                updateLayout(currentLayout);
            } else if (view.getId() == R.id.button_done_update_profile) {
                currentLayout = R.layout.setting;
                Toast.makeText(SettingActivity.this, "Cập nhật thành công!",
                        Toast.LENGTH_SHORT).show();
                updateLayout(currentLayout);
            }
        } else if (currentLayout == R.layout.readme) {
            if (view.getId() == R.id.Button_back_readme) {
                currentLayout = R.layout.setting;
                updateLayout(currentLayout);
            }
        } else if (currentLayout == R.layout.help_and_support) {
            if (view.getId() == R.id.imageButton_back_help_and_support) {
                currentLayout = R.layout.setting;
                updateLayout(currentLayout);
            }
        }
        // Xử lý các sự kiện onClick khác tùy thuộc vào currentLayout
    }



}
