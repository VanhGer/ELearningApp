package com.example.elearningapp;

import android.content.Context;

import com.example.elearningapp.connectData.DatabaseHelper;

/// use only one time.
public class CreateDatabase {
    public void createLessonTable(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        String query = "CREATE TABLE lesson(" +
                "lessonId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "courseId INTERGER, " +
                "name TEXT, " +
                "description TEXT, " +
                "type TEXT, " +
                "image INTERGER, " +
                "script TEXT, " +
                "content TEXT, " +
                "video TEXT, " +
                "qCategoryId INTERGER" +
                ");";
        db.createTable(query, "Lesson");
    }

    public void create(Context context) {
        createLessonTable(context);
    }
}
