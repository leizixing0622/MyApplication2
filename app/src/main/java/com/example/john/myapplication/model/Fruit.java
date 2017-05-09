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
    private int checked;

    public Fruit(int fruitId, String name, int imageId, String title, float price, int checked) {
        this.fruitId = fruitId;
        this.name = name;
        this.imageId = imageId;
        this.title = title;
        this.price = price;
        this.checked = checked;
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

    public int getChecked() {
        return checked;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "fruitId=" + fruitId +
                ", name='" + name + '\'' +
                ", imageId=" + imageId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", checked=" + checked +
                '}';
    }
}
