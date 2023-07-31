//package com.example.elearningapp.activity;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//public class VoteActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    private boolean canVote(int userId, int courseId, Context context) {
//        String query = "SELECT * FROM Learn " +
//                "WHERE userId = " + userId +
//                "AND courseId = " + courseId +
//                ";";
//        DatabaseHelper db = new DatabaseHelper(context);
//        Cursor cs = db.readAllData(query);
//        int lessonLearned = cs.getColumnCount();
//
//        query = "SELECT * FROM Lesson " +
//                "WHERE courseId = " + courseId +
//                ";";
//        cs = db.readAllData(query);
//        int lesson = cs.getColumnCount();
//        return lessonLearned * 100 >= lesson * 20;
//
//    }
//}
