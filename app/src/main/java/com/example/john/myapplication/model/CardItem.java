package com.example.john.myapplication.model;

/**
 * Created by Administrator on 2017/5/6.
 */

public class CardItem {

    private Fruit fruit;
    private int amount;

    public CardItem(int amount, Fruit fruit) {
        this.amount = amount;
        this.fruit = fruit;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "CardItem{" +
                "fruit=" + fruit.toString() +
                ", amount=" + amount +
                '}';
    }
}
