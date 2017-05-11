package com.example.john.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.john.myapplication.activity.DetailActivity;
import com.example.john.myapplication.activity.MainActivity;
import com.example.john.myapplication.activity.R;
import com.example.john.myapplication.adapter.FruitAdapter;
import com.example.john.myapplication.interfaces.MyItemClickListener;
import com.example.john.myapplication.model.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/4/27.
 */

public class FirstFragment extends Fragment implements MyItemClickListener{

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private int[] images = new int[]{R.mipmap.icon11, R.mipmap.icon21, R.mipmap.icon31, R.mipmap.icon12, R.mipmap.icon22, R.mipmap.icon32};
    private List<String> list_Title;
    private TabLayout tabLayout;
    private Context context = getActivity();

    @Override
    public void myOnItemClick(int position) {

    }

    @Override
    public void addAmount(int position) {

    }

    @Override
    public void reduceAmount(int position) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        initData();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void initData(){
        final List<Fragment> fragments = new ArrayList<Fragment>();
        Fragment fragment1 = new FirstFragmentTab1();
        Fragment fragment2 = new SecondFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        list_Title = new ArrayList<>();
        list_Title.add("选喜糖");
        list_Title.add("选喜糖盒子");
        pagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()){

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
                return list_Title.get(position % list_Title.size());
            }

            /**
             * 在调用notifyDataSetChanged()方法后，随之会触发该方法，根据该方法返回的值来确定是否更新
             * object对象为Fragment，具体是当前显示的Fragment和它的前一个以及后一个
             */
            @Override
            public int getItemPosition(Object object) {
                if(object.getClass().getName().equals(SecondFragment.class.getName())){
                    return POSITION_NONE;
                }
                return super.getItemPosition(object);
            }
        };
    }
}
