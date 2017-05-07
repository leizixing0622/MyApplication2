package com.example.john.myapplication.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.myapplication.db.MyDatabaseHelper;
import com.example.john.myapplication.fragment.DetailFirstFragment;
import com.example.john.myapplication.fragment.DetailSecondFragment;
import com.example.john.myapplication.fragment.DetailThirdFragment;
import com.example.john.myapplication.model.Fruit;
import com.example.john.myapplication.viewpager.CustomViewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/5/3.
 */

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private PagerAdapter pagerAdapter;
    private CustomViewpager viewPager;
    private TabLayout tabLayout;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private Fruit fruit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        //接受首页传递过来的单件商品信息
        Intent intent = getIntent();
        String s1 = intent.getStringExtra("title");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(s1);
        fruit = (Fruit) intent.getSerializableExtra("fruit");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
        initView();
        initTabLayout();
        TextView textView = (TextView) findViewById(R.id.add_to_candy_list);
        textView.setOnClickListener(this);
    }
    private void initData(){
        final List<Fragment> fragments = new ArrayList<Fragment>();
        Fragment fragment1 = new DetailFirstFragment();
        Fragment fragment2 = new DetailSecondFragment();
        Fragment fragment3 = new DetailThirdFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }
        };
    }
    private void initView(){
        viewPager = (CustomViewpager) findViewById(R.id.detail_viewpager);
        viewPager.setAdapter(pagerAdapter);
    }
    private void initTabLayout(){
        tabLayout = (TabLayout) findViewById(R.id.detail_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("商品介绍");
        tabLayout.getTabAt(1).setText("详情信息");
        tabLayout.getTabAt(2).setText("客户评价");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.resetHeight(tab.getPosition());
                if(tab == tabLayout.getTabAt(0)){
                    viewPager.setCurrentItem(0);
                }else if(tab == tabLayout.getTabAt(1)){
                    viewPager.setCurrentItem(1);
                }else if(tab == tabLayout.getTabAt(2)){
                    viewPager.setCurrentItem(2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_to_candy_list:
                sqLiteOpenHelper = new MyDatabaseHelper(this, "SugarStore.db", null, 1);
                SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("select * from fruits where fruit_id = ?", new String[]{String.valueOf(fruit.getFruitId())});
                if(cursor.getCount() != 0){
                    Log.d("add", ">>>已经有这种商品了");
                    cursor.moveToFirst();
                    int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                    sqLiteDatabase.execSQL("update fruits set amount = ? where fruit_id = ?", new String[]{String.valueOf(amount + 1), String.valueOf(fruit.getFruitId())});
                    Toast.makeText(DetailActivity.this, "已经成功添加到您的喜糖盒子", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("add", ">>>还没有这种商品");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("fruit_id", fruit.getFruitId());
                    contentValues.put("name", fruit.getName());
                    contentValues.put("price", fruit.getPrice());
                    contentValues.put("title", fruit.getTitle());
                    contentValues.put("amount", 1);
                    contentValues.put("img_id", fruit.getImageId());
                    sqLiteDatabase.insert("fruits", null, contentValues);
                    contentValues.clear();
                    Toast.makeText(DetailActivity.this, "已经成功添加到您的喜糖盒子", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

                break;
        }
    }
}
