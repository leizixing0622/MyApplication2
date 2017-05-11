package com.example.john.myapplication.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.john.myapplication.activity.R;
import com.example.john.myapplication.adapter.CarFruitAdapter;
import com.example.john.myapplication.db.MyDatabaseHelper;
import com.example.john.myapplication.interfaces.MyCheckBoxListener;
import com.example.john.myapplication.interfaces.MyItemClickListener;
import com.example.john.myapplication.model.CardItem;
import com.example.john.myapplication.model.Fruit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    private TextView goto_pay_button;
    private TextView delete_button;
    private CarFruitAdapter carFruitAdapter;
    private TextView total_money;
    private Boolean isEditMode = false;
    private CheckBox all_check;
    private boolean allCheckFoUser=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.second_fragment, container, false);
        edit_button = (TextView) view.findViewById(R.id.edit);
        cancel_edit = (TextView) view.findViewById(R.id.cancel_edit);
        cancel_edit.setOnClickListener(this);
        card_bottom = (LinearLayout) view.findViewById(R.id.card_bottom);
        layoutInflater = LayoutInflater.from(this.getActivity());
        sqLiteOpenHelper = new MyDatabaseHelper(this.getActivity(), "SugarStore.db", null, 1);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        init();
        getAllSugar();
        return view;
    }

    private void init(){
        edit_button.setOnClickListener(this);
    }

    private void getAllSugar(){
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
        if(cursor.getCount() == 0){
            edit_button.setVisibility(View.GONE);
        }else{
            fruitList.clear();
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
                int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                int checked = cursor.getInt(cursor.getColumnIndex("checked"));
                Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                CardItem cardItem = new CardItem(amount, fruit);
                fruitList.add(cardItem);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                carFruitAdapter = new CarFruitAdapter(fruitList);
                recyclerView.setAdapter(carFruitAdapter);
                carFruitAdapter.setOnItemClickListener(this);
                carFruitAdapter.setMyCheckBoxListener(this);
                edit_button.setVisibility(View.VISIBLE);
                refreshTotalPrice();
            }
        }
        cursor.close();
    }



    @Override
    public void myOnItemClick(int position) {

    }

    @Override
    public void addAmount(int position) {
        int fruitId = fruitList.get(position).getFruit().getFruitId();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from fruits where fruit_id = ?", new String[]{String.valueOf(fruitId)}, null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            int i1 = cursor.getInt(cursor.getColumnIndex("amount"));
            if(i1 == 12){
                new MaterialDialog.Builder(this.getActivity())
                        .title("提示")
                        .content("每一种糖果的最大数量为12颗")
                        .positiveText("知道了")
                        .show();
            }else{
                sqLiteDatabase.execSQL("update fruits set amount = ? where fruit_id = ?", new String[]{String.valueOf(i1 + 1), String.valueOf(fruitId)});
            }
        }
        fruitList.clear();
        cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Float price = cursor.getFloat(cursor.getColumnIndex("price"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            int checked = cursor.getInt(cursor.getColumnIndex("checked"));
            Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
            CardItem cardItem = new CardItem(amount, fruit);
            fruitList.add(cardItem);
        }
        cursor.close();
        carFruitAdapter.notifyDataSetChanged();
        if(!isEditMode){
            refreshTotalPriceQuickly();
        }
    }

    @Override
    public void reduceAmount(int position) {
        final int fruitId = fruitList.get(position).getFruit().getFruitId();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from fruits where fruit_id = ?", new String[]{String.valueOf(fruitId)}, null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            int i1 = cursor.getInt(cursor.getColumnIndex("amount"));
            if(i1 == 1){
                new MaterialDialog.Builder(this.getActivity())
                        .title("提示")
                        .content("你确定要删除这件商品吗?")
                        .positiveText("确定")
                        .negativeText("我再想想")
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    //确认删除
                                    deleteByFruitId(fruitId);
                                    refreshCar();
                                } else if (which == DialogAction.NEGATIVE) {
                                    //我再想想
                                }

                            }
                        })
                        .show();
            }else{
                sqLiteDatabase.execSQL("update fruits set amount = ? where fruit_id = ?", new String[]{String.valueOf(i1 - 1), String.valueOf(fruitId)});
            }
        }
        fruitList.clear();
        cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Float price = cursor.getFloat(cursor.getColumnIndex("price"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            int checked = cursor.getInt(cursor.getColumnIndex("checked"));
            Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
            CardItem cardItem = new CardItem(amount, fruit);
            fruitList.add(cardItem);
        }
        cursor.close();
        carFruitAdapter.notifyDataSetChanged();
        if(!isEditMode){
            refreshTotalPriceQuickly();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit:
                card_bottom.removeAllViews();
                View view = layoutInflater.inflate(R.layout.card_bottom_info2, null);
                card_bottom.addView(view);
                delete_button = (TextView) view.findViewById(R.id.delete);
                delete_button.setOnClickListener(this);
                edit_button.setVisibility(View.GONE);
                cancel_edit.setVisibility(View.VISIBLE);
                isEditMode = true;
                all_check = (CheckBox) view.findViewById(R.id.all_check);
                //刷新全选checkbox
                Cursor cursor1 = sqLiteDatabase.rawQuery("select * from fruits where checked = 0", null);
                if(cursor1.getCount() != 0 ){
                    all_check.setChecked(false);
                }else{
                    all_check.setChecked(true);
                }
                cursor1.close();
                all_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(allCheckFoUser == true){
                        if(isChecked){
                            sqLiteDatabase.execSQL("update fruits set checked = 1");
                        }else{
                            sqLiteDatabase.execSQL("update fruits set checked = 0");
                        }
                        Cursor cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
                        fruitList.clear();
                        while(cursor.moveToNext()){
                            int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                            String title = cursor.getString(cursor.getColumnIndex("title"));
                            int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
                            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                            int checked = cursor.getInt(cursor.getColumnIndex("checked"));
                            Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                            CardItem cardItem = new CardItem(amount, fruit);
                            fruitList.add(cardItem);
                        }
                        cursor.close();
                        carFruitAdapter.notifyDataSetChanged();
                    }
                    }
                });
                break;
            case R.id.cancel_edit:
                card_bottom.removeAllViews();
                View view2 = layoutInflater.inflate(R.layout.card_bottom_info, null);
                card_bottom.addView(view2);
                edit_button.setVisibility(View.VISIBLE);
                cancel_edit.setVisibility(View.GONE);
                isEditMode = false;
                all_check = (CheckBox) view2.findViewById(R.id.all_check);
                //刷新全选checkbox
                Cursor cursor2 = sqLiteDatabase.rawQuery("select * from fruits where checked = 0", null);
                if(cursor2.getCount() != 0 ){
                    all_check.setChecked(false);
                }else{
                    all_check.setChecked(true);
                }
                cursor2.close();
                all_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(allCheckFoUser){
                        if(isChecked){
                            sqLiteDatabase.execSQL("update fruits set checked = 1");
                        }else{
                            sqLiteDatabase.execSQL("update fruits set checked = 0");
                        }
                        Cursor cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
                        fruitList.clear();
                        while(cursor.moveToNext()){
                            int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                            String title = cursor.getString(cursor.getColumnIndex("title"));
                            int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
                            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                            int checked = cursor.getInt(cursor.getColumnIndex("checked"));
                            Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                            CardItem cardItem = new CardItem(amount, fruit);
                            fruitList.add(cardItem);
                        }
                        cursor.close();
                        carFruitAdapter.notifyDataSetChanged();
                    }
                    }
                });
                //刷新总价信息
                total_money = (TextView) view2.findViewById(R.id.total_money);
                List<CardItem> cardItems = new ArrayList<>();
                Cursor cursor = sqLiteDatabase.rawQuery("select * from fruits where checked = 1", null);
                if(cursor.getCount() == 0){
                    total_money.setText("¥0.00(每份)");
                }else{
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                        String title = cursor.getString(cursor.getColumnIndex("title"));
                        int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
                        int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                        int checked = cursor.getInt(cursor.getColumnIndex("checked"));
                        Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                        CardItem cardItem = new CardItem(amount, fruit);
                        cardItems.add(cardItem);
                    }
                    double f = 0;
                    for(int i = 0; i < cardItems.size(); i++){
                        f += cardItems.get(i).getFruit().getPrice()/50*cardItems.get(i).getAmount();
                    }
                    BigDecimal b = new BigDecimal(f);
                    double f1 = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                    total_money.setText("¥" + doubleTransform(f1) + "(每份)");
                }
                cursor.close();
                break;
            case R.id.go_to_pay:
                Toast.makeText(this.getActivity(), "去结算", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                sqLiteDatabase.execSQL("delete from fruits where checked = 1");
                Toast.makeText(this.getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                Cursor cursor3 = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
                if(cursor3.getCount() == 0){
                    cancel_edit.setVisibility(View.GONE);
                    fruitList.clear();
                    //改变底部显示
                    card_bottom.removeAllViews();
                    View view3 = layoutInflater.inflate(R.layout.card_bottom_info3, null);
                    card_bottom.addView(view3);
                    isEditMode = false;
                }else{
                    fruitList.clear();
                    while(cursor3.moveToNext()){
                        int id = cursor3.getInt(cursor3.getColumnIndex("fruit_id"));
                        String name = cursor3.getString(cursor3.getColumnIndex("name"));
                        Float price = cursor3.getFloat(cursor3.getColumnIndex("price"));
                        String title = cursor3.getString(cursor3.getColumnIndex("title"));
                        int imgId = cursor3.getInt(cursor3.getColumnIndex("img_id"));
                        int amount = cursor3.getInt(cursor3.getColumnIndex("amount"));
                        int checked = cursor3.getInt(cursor3.getColumnIndex("checked"));
                        Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                        CardItem cardItem = new CardItem(amount, fruit);
                        fruitList.add(cardItem);
                    }
                }
                cursor3.close();
                carFruitAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void myCheckBoxClick(int position) {
        int i1 = fruitList.get(position).getFruit().getFruitId();
        sqLiteDatabase.execSQL("update fruits set checked = 1 where fruit_id = ?", new String[]{String.valueOf(i1)});
        if(!isEditMode){
            refreshTotalPriceQuickly();
        }
        //刷新全选checkbox
        allCheckFoUser = false;
        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from fruits where checked = 0", null);
        if(cursor2.getCount() != 0 ){
            all_check.setChecked(false);
        }else{
            all_check.setChecked(true);
        }
        cursor2.close();
        allCheckFoUser = true;
    }

    @Override
    public void myCheckBoxClick2(int position) {
        int i1 = fruitList.get(position).getFruit().getFruitId();
        sqLiteDatabase.execSQL("update fruits set checked = 0 where fruit_id = ?", new String[]{String.valueOf(i1)});
        if(!isEditMode){
            refreshTotalPriceQuickly();
        }
        //刷新全选checkbox
        allCheckFoUser = false;
        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from fruits where checked = 0", null);
        if(cursor2.getCount() != 0 ){
            all_check.setChecked(false);
        }else{
            all_check.setChecked(true);
        }
        cursor2.close();
        allCheckFoUser = true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    //删除指定业务ID商品的方法
    public void deleteByFruitId(int fruitId){
        sqLiteDatabase.execSQL("delete from fruits where fruit_id = ?", new String[]{String.valueOf(fruitId)});
    }
    //刷新购物车recyclerview的方法
    public void refreshCar(){
        Cursor cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
        if(cursor.getCount() == 0){
            cancel_edit.setVisibility(View.GONE);
            fruitList.clear();
            //改变底部显示
            card_bottom.removeAllViews();
            View view3 = layoutInflater.inflate(R.layout.card_bottom_info3, null);
            card_bottom.addView(view3);
        }else{
            fruitList.clear();
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
                int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                int checked = cursor.getInt(cursor.getColumnIndex("checked"));
                Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                CardItem cardItem = new CardItem(amount, fruit);
                fruitList.add(cardItem);
            }
        }
        cursor.close();
        carFruitAdapter.notifyDataSetChanged();
        Toast.makeText(this.getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
    }
    //显示底部总价并刷新总价的方法
    public void refreshTotalPrice(){
        List<CardItem> cardItems = new ArrayList<>();
        //替换布局文件
        card_bottom.removeAllViews();
        View view = layoutInflater.inflate(R.layout.card_bottom_info, null);
        card_bottom.addView(view);
        goto_pay_button = (TextView) view.findViewById(R.id.go_to_pay);
        goto_pay_button.setOnClickListener(this);
        total_money = (TextView) view.findViewById(R.id.total_money);
        all_check = (CheckBox) view.findViewById(R.id.all_check);
        all_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(allCheckFoUser){
                    if(isChecked){
                        sqLiteDatabase.execSQL("update fruits set checked = 1");
                    }else{
                        sqLiteDatabase.execSQL("update fruits set checked = 0");
                    }
                    Cursor cursor = sqLiteDatabase.query("fruits", null, null, null, null, null, null);
                    fruitList.clear();
                    while(cursor.moveToNext()){
                        int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                        String title = cursor.getString(cursor.getColumnIndex("title"));
                        int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
                        int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                        int checked = cursor.getInt(cursor.getColumnIndex("checked"));
                        Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                        CardItem cardItem = new CardItem(amount, fruit);
                        fruitList.add(cardItem);
                    }
                    cursor.close();
                    carFruitAdapter.notifyDataSetChanged();
                }else{

                }
            }
        });
        //刷新总价信息
        Cursor cursor = sqLiteDatabase.rawQuery("select * from fruits where checked = 1", null);
        if(cursor.getCount() == 0){
            total_money.setText("¥0.00(每份)");
        }else{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
                int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                int checked = cursor.getInt(cursor.getColumnIndex("checked"));
                Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                CardItem cardItem = new CardItem(amount, fruit);
                cardItems.add(cardItem);
            }
            float f = 0;
            for(int i = 0; i < cardItems.size(); i++){
                f += cardItems.get(i).getFruit().getPrice()/50*cardItems.get(i).getAmount();
            }
            BigDecimal b = new BigDecimal(f);
            double f1 = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
            total_money.setText("¥" + doubleTransform(f1) + "(每份)");
        }
        cursor.close();
        //刷新全选checkbox
        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from fruits where checked = 0", null);
        if(cursor2.getCount() != 0 ){

        }else{
            all_check.setChecked(true);
        }
        cursor2.close();
    }
    //显示底部总价并刷新总价的方法2
    public void refreshTotalPriceQuickly(){
        List<CardItem> cardItems = new ArrayList<>();
        goto_pay_button = (TextView) view.findViewById(R.id.go_to_pay);
        total_money = (TextView) view.findViewById(R.id.total_money);
        //刷新总价信息
        Cursor cursor = sqLiteDatabase.rawQuery("select * from fruits where checked = 1", null);
        if(cursor.getCount() == 0){
            total_money.setText("¥0.00(每份)");
        }else{
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("fruit_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Float price = cursor.getFloat(cursor.getColumnIndex("price"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int imgId = cursor.getInt(cursor.getColumnIndex("img_id"));
                int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                int checked = cursor.getInt(cursor.getColumnIndex("checked"));
                Fruit fruit = new Fruit(id, name, imgId, title, price, checked);
                CardItem cardItem = new CardItem(amount, fruit);
                cardItems.add(cardItem);
            }
            double f = 0;
            for(int i = 0; i < cardItems.size(); i++){
                f += cardItems.get(i).getFruit().getPrice()/50*cardItems.get(i).getAmount();
            }
            BigDecimal b = new BigDecimal(f);
            double f1 = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
            total_money.setText("¥" + doubleTransform(f1) + "(每份)");
        }
        cursor.close();
    }
    //double类型转String 类型的小数点后的00
    public static String doubleTransform(double num){
        String strNum = num+"";
        int a = strNum.indexOf(".");
        if(a>0){
            //获取小数点后面的数字
            String dianAfter = strNum.substring(a+1);
            if("0".equals(dianAfter)){
                return strNum+"0";
            }else{
                if(dianAfter.length()==1){
                    return strNum +"0";
                }else{
                    return strNum;
                }
            }
        }else{
            return strNum+".00";
        }
    }
}
