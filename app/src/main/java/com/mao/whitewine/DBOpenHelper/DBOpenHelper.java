package com.mao.whitewine.DBOpenHelper;

/**
 * Created by Administrator on 2021/4/29.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mao.whitewine.pojo.Wine;

/**
 * 创建用户数据表
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    // 定义创建用户数据表的SQL语句
    final String CREATE_TABLEUser_SQL = "create table user (_id integer primary key autoincrement,username varchar(255) unique,password varchar(30),sex varchar(5),age integer,address varchar(255))";
    final String CREATE_TABLEWine_SQL = "create table wine (_id integer primary key autoincrement,photoID integer,name varchar(255) unique,price double,introduction varchar(255))";
    private static String name = "whitewine";// 数据库名称
    private static int version = 1;// 版本
    public DBOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    /**
     * 创建用户数据表
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLEUser_SQL);
        db.execSQL(CREATE_TABLEWine_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("版本","版本更新" + oldVersion + "-->" + newVersion);
    }

}
