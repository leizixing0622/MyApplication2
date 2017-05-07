package com.example.john.myapplication.model;

import java.io.Serializable;

/**
 * Created by John on 2017/5/2.
 */

public class Fruit implements Serializable{

    private int fruitId;
    private String name;
    private int imageId;
    private String title;
    private float price;

    public Fruit(int fruitId, String name, int imageId, String title, float price) {
        this.fruitId = fruitId;
        this.name = name;
        this.imageId = imageId;
        this.title = title;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public int getFruitId() {
        return fruitId;
    }
}
