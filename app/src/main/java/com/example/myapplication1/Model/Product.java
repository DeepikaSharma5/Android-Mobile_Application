package com.example.myapplication1.Model;

public class Product {

    private String name;
    private String brand;
    private String price;
    private String image;
    private String pid;
    private String category;
    private String date;
    private String time;
    private String color;



    public Product() {
    }

    public Product(String name, String brand, String price, String image, String pid, String category, String date, String time, int quantity,
                   String color) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.image = image;
        this.pid = pid;
        this.category = category;
        this.date = date;
        this.time = time;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
