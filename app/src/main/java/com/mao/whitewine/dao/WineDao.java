package com.mao.whitewine.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.mao.whitewine.DBOpenHelper.DBOpenHelper;
import com.mao.whitewine.IndexActivity;
import com.mao.whitewine.R;
import com.mao.whitewine.pojo.Wine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2021/4/30.
 * 白酒Dao层
 */
public class WineDao {
    private DBOpenHelper dbOpenHelper;// 声明 dbOpenHelper 对象

    public WineDao(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * 为wine数据表先添加几条数据，保证一开始的界面有数据
     */
    public void insert(){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String[] name = new String[]{"贵州茅台酒","剑南春"};
        double[] price = new double[]{3000.0,202.0,};
        String[] intro = new String[]{"茅台酒是大曲酱香型酒的鼻祖,它具有色清透明、酱香突出、醇香馥郁、幽雅细腻、入口柔绵、清冽甘爽、酒体醇厚丰满、回味悠长的特点","剑南春是中国传统白酒品牌，产于四川省绵竹市，与赵坡茶、绵竹年画并称为“绵竹三绝”"};
        for (int i = 0; i < 2; i++) {
            ContentValues values = new ContentValues();
            // 往values里面put值
            values.put("photoID",i);
            values.put("name",name[i]);
            values.put("price",price[i]);
            values.put("introduction",intro[i]);
        /*第二参数指定在ContentValues为空的情况下框架可在其中插入null的列的名称
        * （如果设置为null，那么框架将不会在没有纸的时候插入行*/
            db.insert("wine", null, values);
        }
        db.close();
    }
    /**
     * 增加白酒品种
     * @param wine
     * @return
     */
    public boolean addWine(Wine wine){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        // 往values里面put值
        values.put("photoID",wine.getPhotoID());
        values.put("name",wine.getName());
        values.put("price",wine.getPrice());
        values.put("introduction",wine.getIntroduction());
        /*第二参数指定在ContentValues为空的情况下框架可在其中插入null的列的名称
        * （如果设置为null，那么框架将不会在没有纸的时候插入行*/
        long result = db.insert("wine", null, values);
        db.close();
        // 如果插入成功则返回1；
        return result != -1;
    }

    /**
     * 查询全部白酒信息，返回list集合
     * @return
     */
    public List<Map<String, Object>> queryAllWine(){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        int[] photoList = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,
                R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine};
        // 查询全部的白酒
        Cursor cursor = db.query("wine", null, null, null, null, null, null);
        // 将数据封装在list集合里面
        ArrayList<Map<String, Object>> listWine = new ArrayList<Map<String, Object>>();
        Map<String,Object> map = null;
        while (cursor.moveToNext()){
            map = new HashMap<>();
            // 往map里面put值
            int photoID = cursor.getInt(cursor.getColumnIndex("photoID"));
            map.put("image", photoList[photoID]);
            map.put("name_Intro","【" + cursor.getString(cursor.getColumnIndex("name"))+ "】：介绍：" + cursor.getString(cursor.getColumnIndex("introduction")));
            map.put("price","单价:￥" + cursor.getInt(cursor.getColumnIndex("price")));
            listWine.add(map);
        }
        // 关闭数据库连接
        db.close();
        // 返回
        return listWine;
    }

    /**
     * 根据白酒名称查询白酒,返回list集合
     * @param name
     * @return
     */
    public List<Map<String, Object>> queryWineByName(String name){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        int[] photoList = new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,
                R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine};
        // 根据白酒名称查询白酒
        Cursor cursor = db.query("wine", null, "name like ?", new String[]{name}, null, null, null);
        List<Map<String, Object>> listWine = null;
        Map<String, Object> map = null;
        listWine = new ArrayList<Map<String, Object>>();
        while (cursor.moveToNext()){
            map = new HashMap<String, Object>();
            int photoID = cursor.getInt(cursor.getColumnIndex("photoID"));
            map.put("image", photoList[photoID]);
            map.put("name_Intro","【" + cursor.getString(cursor.getColumnIndex("name"))+ "】： " + cursor.getString(cursor.getColumnIndex("introduction")));
            map.put("price","单价:￥" + cursor.getInt(cursor.getColumnIndex("price")));
            listWine.add(map);
        }
        cursor.close();
        db.close();
        return listWine;
    }

    /**
     * 返回Wine，根据name查询Wine
     * @param name
     * @return
     */
    public Wine queryWineByNameWine(String name){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // 根据白酒名称查询白酒
        Cursor cursor = db.query("wine", null, "name=?", new String[]{name}, null, null, null);
        Wine wine = new Wine();
        if (cursor.getCount() == 1&&cursor.moveToNext()){
//            Log.wtf("cursor",cursor.toString());
            wine.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            wine.setName(cursor.getString(cursor.getColumnIndex("name")));
            wine.setPhotoID(cursor.getInt(cursor.getColumnIndex("photoID")));
            wine.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
            wine.setIntroduction(cursor.getString(cursor.getColumnIndex("introduction")));
            db.close();
            return wine;
        }else {
            Log.wtf("根据名称查询Wine","错误");
            return null;
        }
    }

    /**
     * 通过name删除白酒
     * @param name
     * @return
     */
    public boolean deleteWineByName(String name){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // 删除条件
        String whereClause = "name Like ?";
        // 删除条件的参数
        String[] whereArgs = new String[]{name};
        int result = 0;
        try {
            result = db.delete("wine", whereClause, whereArgs);
        }catch (SQLException e){
            Log.i("delete","删除失败");
            return false;
        }finally {
            db.close();
        }
        return result != 0;
    }

    /**
     * 根据id更新酒
     * @param wine
     * @return
     */
    public boolean updateWine(Wine wine){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        // 往values 里面put值
        values.put("photoID",wine.getPhotoID());
        values.put("name",wine.getName());
        values.put("price",wine.getPrice());
        values.put("introduction",wine.getIntroduction());
        int result = 0;
        try {
            result = db.update("wine", values, "_id=?", new String[]{String.valueOf(wine.getId())});
        }catch (SQLException e){
            Log.wtf("更新","失败");
            return false;
        }finally {
            db.close();
        }
        return result != 0;
    }
}
