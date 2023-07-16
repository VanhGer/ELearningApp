package com.example.elearningapp.connectData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.elearningapp.courseItem.LessonDatabaseHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "course.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /* create a Table in database */
    public void createTable(String query, String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(query);
        /// the query is the same with sql language.
        // EX: "CREATE TABLE table_name (
        //    column1 datatype,
        //    column2 datatype,
        //    column3 datatype,
        //   ....
        //);"
    }

    /** insert. */
    public void InsertDatabase(ContentValues cv, String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Insert Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Inserted Successfully!", Toast.LENGTH_SHORT).show();
        }

        // the cx format: cv.put(column_name, column_value).
    }

    /** get database into a cursor. */
    public Cursor readAllData(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;

        /// query EX: "SELECT * FROM TABLE_NAME";
    }

    /** update database. */
    void updateData(ContentValues cv, String TABLE_NAME, String whereClause, String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update(TABLE_NAME, cv, whereClause, new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    //** delete database. It should go with a confirm Dialog*/
    void deleteOneRow(String TABLE_NAME, String whereClause, String row_id ) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, whereClause, new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
