package com.example.john.myapplication.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.myapplication.activity.R;
import com.example.john.myapplication.adapter.CarFruitAdapter;
import com.example.john.myapplication.db.MyDatabaseHelper;
import com.example.john.myapplication.interfaces.MyItemClickListener;
import com.example.john.myapplication.model.CardItem;
import com.example.john.myapplication.model.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/4/27.
 */

public class SecondFragment extends Fragment implements MyItemClickListener{

    private SQLiteOpenHelper sqLiteOpenHelper;
    private View view;
    private List<CardItem> fruitList = new ArrayList<CardItem>();
    private ImageView reduce_button;
    private ImageView add_button;
    private TextView sugar_amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.second_fragment, container, false);
        getAllSugar();
        return view;
    }

    private void getAllSugar(){
        sqLiteOpenHelper = new MyDatabaseHelper(this.getActivity(), "SugarStore.db", null, 1);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
        fruitList.clear();
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Float price = cursor.getFloat(cursor.getColumnIndex("price"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            Fruit fruit = new Fruit(id, name, imgId, title, price);
            CardItem cardItem = new CardItem(amount, fruit);
            fruitList.add(cardItem);
        }
        cursor.close();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        CarFruitAdapter carFruitAdapter = new CarFruitAdapter(fruitList);
        recyclerView.setAdapter(carFruitAdapter);
        carFruitAdapter.setOnItemClickListener(this);
    }

    @Override
    public void myOnItemClick(int position) {

    }
}
