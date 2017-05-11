package com.example.john.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by John on 2017/5/11.
 */

public class FirstFragmentTab1 extends Fragment implements MyItemClickListener{
    private List<Fruit> fruits = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_tab1, container, false);
        initFruits();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getParentFragment().getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        FruitAdapter fruitAdapter = new FruitAdapter(fruits);
        recyclerView.setAdapter(fruitAdapter);
        fruitAdapter.setOnItemClickListener(this);
        return view;
    }

    private void initFruits(){
        Fruit fruit1 = new Fruit(0, "好邻居枣生贵子散装500g结婚独立包装喜枣好邻居蜜枣喜枣蜜饯", R.mipmap.candy1, "枣生贵子", 14.79f, 1);
        fruits.add(fruit1);
        Fruit fruit2 = new Fruit(1, "好邻居牵手一生牛奶硬糖500g散装结婚庆喜糖果零食品批发儿童糖果", R.mipmap.candy2, "牵手一生", 16.78f, 1);
        fruits.add(fruit2);
        Fruit fruit3 = new Fruit(2, "结婚庆喜糖果好邻居奇脆米牛奶巧克力500g约100个零食品散装批发", R.mipmap.candy3, "奇脆米", 23.80f, 1);
        fruits.add(fruit3);
        Fruit fruit4 = new Fruit(3, "好邻居真情告白500g约100个水果糖硬糖结婚庆喜糖果散装零食批发", R.mipmap.candy4, "真情告白", 13.80f, 1);
        fruits.add(fruit4);
        Fruit fruit5 = new Fruit(4, "好邻居扁桃仁牛奶糖杏仁糖酥糖500g 喜糖果散装六一休闲零食批发", R.mipmap.candy5, "桃仁奶酥", 25.80f, 1);
        fruits.add(fruit5);
    }


    @Override
    public void myOnItemClick(int position) {
        Intent intent = new Intent(this.getActivity(), DetailActivity.class);
        intent.putExtra("title", fruits.get(position).getTitle());
        Bundle bundle = new Bundle();
        bundle.putSerializable("fruit", fruits.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void addAmount(int position) {

    }

    @Override
    public void reduceAmount(int position) {

    }
}
