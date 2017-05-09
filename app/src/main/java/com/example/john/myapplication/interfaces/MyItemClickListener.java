package com.example.john.myapplication.interfaces;

import com.example.john.myapplication.model.Fruit;

/**
 * Created by John on 2017/5/3.
 */

public interface MyItemClickListener {
    public void myOnItemClick(int position);
    public void addAmount(int position);
    public void reduceAmount(int position);
}

