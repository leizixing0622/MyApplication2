package com.example.john.myapplication.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.john.myapplication.activity.R;
import com.example.john.myapplication.db.MyDatabaseHelper;

/**
 * Created by John on 2017/4/27.
 */

public class SecondFragment extends Fragment {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.second_fragment, container, false);
        getAllSugar();
        return view;
    }

    private void getAllSugar(){
        sqLiteOpenHelper = new MyDatabaseHelper(this.getActivity(), "SugarStore.db", null, 1);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("sugars", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                TextView textView = (TextView) view.findViewById(R.id.sugar_list);
                textView.setText("名称：" + name + "单价：" + price + "数量：" + amount);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
