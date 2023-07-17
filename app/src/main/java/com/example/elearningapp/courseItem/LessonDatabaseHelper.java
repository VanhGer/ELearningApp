package com.example.elearningapp.courseItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.elearningapp.connectData.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class LessonDatabaseHelper {
    public void createNewLesson(Context context, int courseId, String name, String des, String type, String img, String script,
                                String content, String video) {
        ContentValues cv = new ContentValues();
        cv.put("courseId", courseId);
        cv.put("name", name);
        cv.put("description", des);
        cv.put("type", type);
        cv.put("image", img);
        cv.put("script", script);
        cv.put("content", content);
        cv.put("video", video);
        DatabaseHelper db = new DatabaseHelper(context);
        db.InsertDatabase(cv, "lesson");
    }

    public void createNewQuestion(Context context, int lessonId, String content, String choice1, String choice2,
                                  String choice3, String choice4, int answer) {
        ContentValues cv = new ContentValues();
        cv.put("lessonId", lessonId);
        cv.put("content", content);
        cv.put("choice1", choice1);
        cv.put("choice2", choice2);
        cv.put("choice3", choice3);
        cv.put("choice4", choice4);
        cv.put("answer", answer);

        DatabaseHelper db = new DatabaseHelper(context);
        db.InsertDatabase(cv, "question");
    }

    public List<LessonItem> getListLessonByCourseId(Context context, int courseId) {
        List<LessonItem> list = new ArrayList<LessonItem>();
        DatabaseHelper db = new DatabaseHelper(context);
        String query = "SELECT * FROM lesson WHERE lesson.courseId = " + courseId + ";";
        Cursor cursor = db.readAllData(query);
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No lesson", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                LessonItem lessonItem = new LessonItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8));
                list.add(lessonItem);
            }
        }
        return list;
    }

    public List<QuestionItem> getQuestionbyLessonId(Context context, int lessonId) {
        List<QuestionItem> list = new ArrayList<QuestionItem>();
        DatabaseHelper db = new DatabaseHelper(context);
        String query = "SELECT * FROM question WHERE question.lessonId = " + lessonId + ";";
        Cursor cursor = db.readAllData(query);
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No question", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                QuestionItem questionItem = new QuestionItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7) );
                list.add(questionItem);
            }
        }
        return list;
    }
}
