package com.mao.whitewine.pojo;

import android.content.Intent;

/**
 * Created by Administrator on 2021/4/29.
 */
// 用户实体类
public class User {
    // 用户id
    private int id;
    // 用户姓名
    private String username;
    // 用户密码
    private String password;
    // 用户性别
    private String sex;
    // 用户年龄
    private int age;
    // 用户地址
    private String address;

    public User() {
    }

    public User(int id, String username, String password, String sex, int age, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.address = address;
    }

    public User(String username, String password, String sex, int age, String address) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
