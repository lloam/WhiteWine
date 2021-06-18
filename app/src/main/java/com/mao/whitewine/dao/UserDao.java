package com.mao.whitewine.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mao.whitewine.DBOpenHelper.DBOpenHelper;
import com.mao.whitewine.pojo.User;

/**
 * Created by Administrator on 2021/4/29.
 * 用户dao层
 */
public class UserDao {
    private DBOpenHelper DBOpenHelper; // 声明userDBOpenHelper对象

    public UserDao(Context context) {
        DBOpenHelper = new DBOpenHelper(context);
    }

    /**
     * 增加用户
     *
     * @param user
     */
    public boolean addUser(User user) {
        SQLiteDatabase db = DBOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // 往values里面放值，这就是个HashMap
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("sex", user.getSex());
        values.put("age", user.getAge());
        values.put("address", user.getAddress());
        /*第二参数指定在ContentValues为空的情况下框架可在其中插入null的列的名称
        * （如果设置为null，那么框架将不会在没有纸的时候插入行*/
        long result = db.insert("user", null, values);
        db.close();
        return result != -1;
    }

    /**
     * 根据username删除用户
     *
     * @param username
     */
    public boolean deleteUser(String username) {
        SQLiteDatabase db = DBOpenHelper.getReadableDatabase();
        // 删除条件
        String whereClause = "username Like ?";
        // 删除条件的参数
        String[] whereArgs = new String[]{username};
        // 数据库 删除条件 删除参数
        int result = db.delete("user", whereClause, whereArgs);
        db.close();
        // 删除成功则返回1
        return result != 0;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        SQLiteDatabase db = DBOpenHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        // 往values 里面put值
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("sex", user.getSex());
        values.put("age", user.getAge());
        values.put("address", user.getAddress());
        // 数据库 values 更新条件 更新条件的参数
        int result = db.update("user", values, "_id=?", new String[]{String.valueOf(user.getId())});
        // 更新成功则返回 one
        return result != 0;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    public boolean queryUserByNamePwd(String username, String password) {
        SQLiteDatabase db = DBOpenHelper.getWritableDatabase();
        // 根据用户名与密码查询用户
        Cursor cursor = db.query("user", null, "username=? and password=?", new String[]{username, password}, null, null, null);
        // 如果查询结果等于 one ，说明存在这个人
        if (cursor.getCount() == 1) {
            db.close();
            return true;
        } else {
            // 否则不存在
            db.close();
            return false;
        }
    }

    public User queryUserByName(String username) {
        User user = new User();
        SQLiteDatabase db = DBOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("user", null, "username=?", new String[]{username}, null, null, null);
        while (cursor.moveToNext()) {
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setSex(cursor.getString(3));
            user.setAge(cursor.getInt(4));
            user.setAddress(cursor.getString(5));
        }
        db.close();
        return user;
    }
}
