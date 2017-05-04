package com.example.john.myapplication.model;

/**
 * Created by John on 2017/4/26.
 */

public class Book {

    private int id;
    private String author;
    private double price;
    private int pages;
    private String name;
    private String press;

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getPages() {
        return pages;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }
}
