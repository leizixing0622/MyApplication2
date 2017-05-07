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
    private static final String CREATE_FRUIT = "create table fruits(" +
            "id integer primary key autoincrement, " +
            "fruit_id integer, " +
            "name text, " +
            "price real, " +
            "amount integer, " +
            "title text," +
            "img_id integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FRUIT);
        Toast.makeText(mContext, "数据库初始化成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
