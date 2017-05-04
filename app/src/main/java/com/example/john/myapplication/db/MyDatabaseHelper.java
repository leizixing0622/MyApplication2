package com.example.john.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by John on 2017/4/25.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String CREATE_BOOK = "create table books(" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "pages integer, " +
            "name text)";
    private static final String CREATE_CATEGORY = "create table categories(" +
            "id integer primary key autoincrement, " +
            "category_name text, " +
            "category_code integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "create table success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists books");
        db.execSQL("drop table if exists categories");
        onCreate(db);
        Log.d("MyDatabaseHelper", "onUpgrade");
    }
}
