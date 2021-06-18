package com.mao.whitewine.pojo;

/**
 * Created by Administrator on 2021/4/29.
 */
// 白酒实体类
public class Wine {
    // 白酒ID
    private int id;
    // 白酒图片
    private int photoID;
    // 白酒名字
    private String name;
    // 白酒价格
    private double price;
    // 白酒简介
    private String introduction;

    public Wine() {
    }

    public Wine(int photoID, String name, double price, String introduction) {
        this.photoID = photoID;
        this.name = name;
        this.price = price;
        this.introduction = introduction;
    }

    public Wine(int id, int photoID, String name, double price, String introduction) {
        this.id = id;
        this.photoID = photoID;
        this.name = name;
        this.price = price;
        this.introduction = introduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
