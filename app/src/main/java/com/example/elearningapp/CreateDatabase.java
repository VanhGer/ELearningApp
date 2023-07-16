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

    public void createUserTable (Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        String query = "CREATE TABLE user(" +
                "userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(55)" +
                ");";
        db.createTable(query, "User");
    }

    public void createCourseTable (Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        String query = "CREATE TABLE course(" +
                "courseId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(55)" +
                "users INTEGER, " +
                ");";
        db.createTable(query, "Course");
    }

    public void createLearnTable (Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        String query = "CREATE TABLE learn(" +
                "learnId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "courseId, INTEGER, " +
                ");";
        db.createTable(query, "Learn");
    }

    public void createVoteTable (Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        String query = "CREATE TABLE vote(" +
                "voteId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "courseId, INTEGER, " +
                ");";
        db.createTable(query, "Vote");
    }

    public void create(Context context) {
        createLessonTable(context);
        createUserTable(context);
        createCourseTable(context);
        createLearnTable(context);
        createVoteTable(context);
    }
}
