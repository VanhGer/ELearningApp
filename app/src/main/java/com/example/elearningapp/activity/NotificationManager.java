package com.example.elearningapp.activity;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NotificationManager {

    private static final String NOTIFICATION_PREFS = "notification_prefs";
    private static final String COMPLETED_COURSES_PREF = "completed_courses";

    private Context context;
    private SharedPreferences sharedPreferences;

    public NotificationManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NOTIFICATION_PREFS, Context.MODE_PRIVATE);
    }

    public void registerCourse(String courseName) {
        Set<String> completedCourses = sharedPreferences.getStringSet(COMPLETED_COURSES_PREF, new HashSet<>());
        completedCourses.add(courseName);
        sharedPreferences.edit().putStringSet(COMPLETED_COURSES_PREF, completedCourses).apply();
    }

    public boolean isCourseCompleted(String courseName) {
        Set<String> completedCourses = sharedPreferences.getStringSet(COMPLETED_COURSES_PREF, new HashSet<>());
        return completedCourses.contains(courseName);
    }

    public ArrayList<String> getNotifications() {
        ArrayList<String> notifications = new ArrayList<>();
        Set<String> completedCourses = sharedPreferences.getStringSet(COMPLETED_COURSES_PREF, new HashSet<>());

        for (String courseName : completedCourses) {
            notifications.add("Chúc mừng bạn đã hoàn thành khóa học " + courseName);
        }

        // Add other notifications (e.g., registration notifications) here

        return notifications;
    }
}

