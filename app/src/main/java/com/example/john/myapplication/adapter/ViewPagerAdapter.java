package com.example.john.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by John on 2017/5/2.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private String[] tabTitles = {"全部", "视频", "声音"};

    public ViewPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView view = new TextView(mContext);
        view.setText(tabTitles[position]);
        view.setTextColor(Color.BLACK);
        view.setTextSize(20);
        view.setGravity(Gravity.CENTER);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((TextView) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
