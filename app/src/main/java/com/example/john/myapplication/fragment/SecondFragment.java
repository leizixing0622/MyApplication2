package com.example.john.myapplication.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.john.myapplication.activity.R;
import com.example.john.myapplication.adapter.CarFruitAdapter;
import com.example.john.myapplication.db.MyDatabaseHelper;
import com.example.john.myapplication.interfaces.MyCheckBoxListener;
import com.example.john.myapplication.interfaces.MyItemClickListener;
import com.example.john.myapplication.model.CardItem;
import com.example.john.myapplication.model.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/4/27.
 */

public class SecondFragment extends Fragment implements MyItemClickListener, MyCheckBoxListener, View.OnClickListener {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private RecyclerView recyclerView;
    private View view;
    private List<CardItem> fruitList = new ArrayList<CardItem>();
    private ImageView reduce_button;
    private ImageView add_button;
    private TextView sugar_amount;
    private TextView edit_button;
    private LinearLayout card_bottom;
    private TextView cancel_edit;
    private LayoutInflater  layoutInflater;
    private List<CardItem> checked_fruitList = new ArrayList<CardItem>();
    private TextView goto_pay_button;
    private TextView delete_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.second_fragment, container, false);
        edit_button = (TextView) view.findViewById(R.id.edit);
        cancel_edit = (TextView) view.findViewById(R.id.cancel_edit);
        cancel_edit.setOnClickListener(this);
        card_bottom = (LinearLayout) view.findViewById(R.id.card_bottom);
        layoutInflater = LayoutInflater.from(this.getActivity());
        sqLiteOpenHelper = new MyDatabaseHelper(this.getActivity(), "SugarStore.db", null, 1);
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        init();
        getAllSugar();
        return view;
    }

    private void getAllSugar(){
        Cursor cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
        if(cursor.getCount() == 0){
            edit_button.setVisibility(View.GONE);
        }else{
            fruitList.clear();
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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                CarFruitAdapter carFruitAdapter = new CarFruitAdapter(fruitList);
                recyclerView.setAdapter(carFruitAdapter);
                carFruitAdapter.setOnItemClickListener(this);
                carFruitAdapter.setMyCheckBoxListener(this);
                edit_button.setVisibility(View.VISIBLE);
                //改变底部显示
                card_bottom.removeAllViews();
                View view = layoutInflater.inflate(R.layout.card_bottom_info, null);
                card_bottom.addView(view);
                goto_pay_button = (TextView) view.findViewById(R.id.go_to_pay);
                goto_pay_button.setOnClickListener(this);
            }
        }
        cursor.close();
    }

    private void init(){
        edit_button.setOnClickListener(this);
    }

    @Override
    public void myOnItemClick(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit:
                card_bottom.removeAllViews();
                View view = layoutInflater.inflate(R.layout.card_bottom_info2, null);
                delete_button = (TextView) view.findViewById(R.id.delete);
                card_bottom.addView(view);
                edit_button.setVisibility(View.GONE);
                cancel_edit.setVisibility(View.VISIBLE);
                delete_button.setOnClickListener(this);
                break;
            case R.id.cancel_edit:
                card_bottom.removeAllViews();
                View view2 = layoutInflater.inflate(R.layout.card_bottom_info, null);
                card_bottom.addView(view2);
                edit_button.setVisibility(View.VISIBLE);
                cancel_edit.setVisibility(View.GONE);
                break;
            case R.id.go_to_pay:
                for(int i=0;i<checked_fruitList.size();i++)
                {
                    Log.d(">>>>>>", checked_fruitList.get(i).toString());
                }
                break;
            case R.id.delete:
                for(int i=0;i<checked_fruitList.size();i++)
                {
                    int fruit_id = checked_fruitList.get(i).getFruit().getFruitId();
                    sqLiteDatabase.execSQL("delete from fruits where fruit_id = ?", new String[]{String.valueOf(fruit_id)});
                }
                Cursor cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
                if(cursor.getCount() == 0){
                    edit_button.setVisibility(View.GONE);
                }else{
                    fruitList.clear();
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
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        CarFruitAdapter carFruitAdapter = new CarFruitAdapter(fruitList);
                        recyclerView.setAdapter(carFruitAdapter);
                        carFruitAdapter.setOnItemClickListener(this);
                        carFruitAdapter.setMyCheckBoxListener(this);
                    }
                }
                cursor.close();
                break;
            default:
                break;
        }
    }

    @Override
    public void myCheckBoxClick(int position) {
        //Toast.makeText(this.getActivity(), ">>>>>选中了" + position, Toast.LENGTH_SHORT).show();
        checked_fruitList.add(fruitList.get(position));
    }

    @Override
    public void myCheckBoxClick2(int position) {
        //Toast.makeText(this.getActivity(), ">>>>>取消了" + position, Toast.LENGTH_SHORT).show();
        int fruit_id = fruitList.get(position).getFruit().getFruitId();
        for(int i = 0; i < checked_fruitList.size(); i++){
            if(checked_fruitList.get(i).getFruit().getFruitId() == fruit_id){
                checked_fruitList.remove(i);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
