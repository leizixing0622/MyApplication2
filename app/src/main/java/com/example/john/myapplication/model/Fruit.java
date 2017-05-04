package com.example.john.myapplication.model;

/**
 * Created by John on 2017/5/2.
 */

public class Fruit {

    private String name;
    private int imageId;
    private String title;

    public Fruit(String name, int imageId, String title) {
        this.name = name;
        this.imageId = imageId;
        this.title = title;
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
}
