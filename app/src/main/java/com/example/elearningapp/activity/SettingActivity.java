package com.example.elearningapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.elearningapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseUser user;


    private int currentLayout;

    ConstraintLayout delete , dieukhoan ;
    Switch notificationSwitch ;
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

        notificationSwitch = findViewById(R.id.switch1); // Ánh xạ switch
        notificationSwitch.setChecked(getNotificationState()); // Đặt trạng thái switch dựa trên trạng thái hiện tại

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setNotificationState(isChecked); // Lưu trạng thái thông báo vào SharedPreferences

                // Hiển thị Toast tùy thuộc vào trạng thái bật/tắt
                if (isChecked) {
                    Toast.makeText(SettingActivity.this, "Đã bật thông báo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingActivity.this, "Đã tắt thông báo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean getNotificationState() {
        SharedPreferences prefs = getSharedPreferences("notification_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isNotificationEnabled", true); // Giá trị mặc định là true
    }

    private void setNotificationState(boolean isEnabled) {
        SharedPreferences prefs = getSharedPreferences("notification_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isNotificationEnabled", isEnabled);
        editor.apply();
    }

    private void updateLayout(int layoutId) {
        setContentView(layoutId);
        setClickListeners();

    }

    private void setClickListeners() {
        if(currentLayout == R.layout.setting){
            delete =findViewById(R.id.delete);
            dieukhoan = findViewById(R.id.constraintLayout10);

            dieukhoan.setOnClickListener(this);
            delete.setOnClickListener(this);
    }}
    @Override
    public void onClick(View view) {
        if (currentLayout == R.layout.setting) {
            if (view.getId() == R.id.constraintLayout10) {
               currentLayout = R.layout.dieukhoan;
                updateLayout(currentLayout);
            } else if (view.getId() == R.id.delete) {
                Intent intent = new Intent(getApplicationContext(), DeleteAccountActivity.class);
                startActivity(intent);
                finish();
            } }}}
        // Xử lý các sự kiện onClick khác tùy thuộc vào currentLayout





