package com.example.myapplication1.Model;

public class Cart {
    private String pid;
    public String pname;
    public String pbrand;
    public String pro_price;
    public String pcategory;
    public int quantity;
    public String date;
    public String time;
    public String pimage;
    public String psize;
    public int tot_price;

    public Cart() {
    }

    public Cart(String pid, String pname, String pbrand, String pro_price, String pcategory, int quantity, String date, String time, String pimage, String psize, int tot_price) {
        this.pid = pid;
        this.pname = pname;
        this.pbrand = pbrand;
        this.pro_price = pro_price;
        this.pcategory = pcategory;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.pimage = pimage;
        this.psize = psize;
        this.tot_price = tot_price;
    }

    public String getName() {
        return pname;
    }

    public void setName(String pname) {
        this.pname = pname;
    }

    public String getBrand() {
        return pbrand;
    }

    public void setBrand(String pbrand) {
        this.pbrand = pbrand;
    }

    public String getPrice() {
        return pro_price;
    }

    public void setPrice(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getCategory() {
        return pcategory;
    }

    public void setCategory(String pcategory) {
        this.pcategory = pcategory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public  String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public void setTime(String image) {
        this.time = time;
    }

    public String getImage() {
        return pimage;
    }

    public void setImage(String pimage) {
        this.pimage = pimage;
    }

    public String getSize() {
        return psize;
    }

    public void setSize(String psize) {
        this.psize = psize;
    }

    public int getTot_price() {
        return tot_price;
    }

    public void setTot_price(int tot_price) {
        this.tot_price = tot_price;
    }
}
