package com.example.elearningapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private ListView notificationListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        notificationListView = findViewById(R.id.notificationList);

        // Lấy trạng thái thông báo từ SharedPreferences
        boolean isNotificationEnabled = getNotificationState();

        // Sử dụng NotificationManager để lấy danh sách thông báo
        NotificationManager notificationManager = new NotificationManager(NotificationActivity.this);
        ArrayList<String> notifications = notificationManager.getNotifications(isNotificationEnabled); // Truyền trạng thái thông báo

        // Hiển thị danh sách thông báo trong ListView
        adapter = new ArrayAdapter<>(this, R.layout.notification_item, R.id.notificationMessage, notifications);
        notificationListView.setAdapter(adapter);
    }

    private boolean getNotificationState() {
        SharedPreferences prefs = getSharedPreferences("notification_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isNotificationEnabled", true); // Giá trị mặc định là true
    }
}
