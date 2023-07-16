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
    public void createNewLesson(Context context, int courseId, String name, String des, String type, int img, String script,
                                String content, String video, int qCategoryId) {
        ContentValues cv = new ContentValues();
        cv.put("courseId", courseId);
        cv.put("name", name);
        cv.put("description", des);
        cv.put("type", type);
        cv.put("image", img);
        cv.put("script", script);
        cv.put("content", content);
        cv.put("video", video);
        cv.put("qCategoryId", qCategoryId);
        DatabaseHelper db = new DatabaseHelper(context);
        db.InsertDatabase(cv, "lesson");
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
                        cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getInt(9));
                list.add(lessonItem);
            }
        }
        return list;
    }
}
