package com.example.john.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.john.myapplication.activity.DetailActivity;
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

    private List<Fruit> fruits = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        initFruits();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        FruitAdapter fruitAdapter = new FruitAdapter(fruits);
        recyclerView.setAdapter(fruitAdapter);
        fruitAdapter.setOnItemClickListener(this); 
        return view;
    }

    private void initFruits(){
        for(int i = 0; i < 20; i++){
            Fruit fruit = new Fruit("好邻居枣生贵子散装500g结婚独立包装喜枣好邻居蜜枣喜枣蜜饯", R.mipmap.candy1, "枣生贵子");
            fruits.add(fruit);
        }
    }


    @Override
    public void myOnItemClick(int position) {
        Intent intent = new Intent(this.getActivity(), DetailActivity.class);
        intent.putExtra("title", fruits.get(position).getTitle());
        startActivity(intent);
    }
}
