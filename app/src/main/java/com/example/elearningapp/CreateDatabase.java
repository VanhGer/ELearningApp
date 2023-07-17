package com.example.elearningapp;

import android.content.Context;
import android.util.Log;

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
                "image TEXT, " +
                "script TEXT, " +
                "content TEXT, " +
                "video TEXT" +
                ");";
        db.createTable(query, "Lesson");
    }


    public void createQuestionTable(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        String query = "CREATE TABLE question(" +
                "questionId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lessonId INTERGER, " +
                "content TEXT, " +
                "choice1 TEXT, " +
                "choice2 TEXT, " +
                "choice3 TEXT, " +
                "choice4 TEXT, " +
                "answer INTERGER" +
                ");";
        db.createTable(query, "question");
    }
    public void create(Context context) {
        Log.w("okkk", "ok");
        createQuestionTable(context);
        createLessonTable(context);
    }
}