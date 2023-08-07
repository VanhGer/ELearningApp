package com.example.elearningapp.activity;

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

        // Sử dụng NotificationManager để lấy danh sách thông báo
        NotificationManager notificationManager = new NotificationManager(NotificationActivity.this);
        ArrayList<String> notifications = notificationManager.getNotifications();

        // Hiển thị danh sách thông báo trong ListView
        adapter = new ArrayAdapter<>(this, R.layout.notification_item, R.id.notificationMessage, notifications);
        notificationListView.setAdapter(adapter);
    }
}
