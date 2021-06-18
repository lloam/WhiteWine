package com.mao.whitewine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mao.whitewine.dao.WineDao;
import com.mao.whitewine.pojo.Wine;
import com.mao.whitewine.wine.UpdateWineActivity;
import com.mao.whitewine.wine.addWineActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IndexActivity extends Activity {

    private WineDao wineDao = new WineDao(IndexActivity.this);// 依赖白酒Dao层
    private MenuItem username;
    private MenuItem update;
    private MenuItem exit;
    private Button addWine;
    private Button updateWine;
    private Button deleteWine;
    private Button showAllWines;
    private ListView listView;
    private Button queryWine;
    private EditText wineText;
    private String usernamePa;
    // 将这个用户名存储在 SharedPreferences
    private SharedPreferences sp;
    private Toast mToast = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
//        wineDao.insert();
        queryWine = (Button) findViewById(R.id.queryWine);// 获取查询按钮
        wineText = (EditText) findViewById(R.id.WineText);// 获取查询文本框
        Intent intent = getIntent();// 获取loginActivity传过来的参数
        // 获取用户名参数
        usernamePa = intent.getStringExtra("username");
        sp = getSharedPreferences("user", MODE_PRIVATE);
        if(usernamePa != null){
            saveUsername(usernamePa);
        }
        addWine = (Button) findViewById(R.id.addW);// 获取添加按钮
        updateWine = (Button) findViewById(R.id.updateWine);
        deleteWine = (Button) findViewById(R.id.deleteWine);
        showAllWines = (Button) findViewById(R.id.showAllWines);
        // 设置事件监听
        addWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到添加白酒页面
                Intent intent1 = new Intent(IndexActivity.this,addWineActivity.class);
                startActivity(intent1);
            }
        });
        // 给查询按钮添加事件
        queryWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = wineText.getText().toString();
                List<Map<String, Object>> listWine = wineDao.queryWineByName(name);
                if(listWine.size() == 0){
                    showToast("没有您所查询的酒");
                }else {
                    // 设置适配器
                    SimpleAdapter simpleAdapter = new SimpleAdapter(IndexActivity.this, listWine,
                            R.layout.item,new String[]{"image","name_Intro","price"},
                            new int[]{R.id.image,R.id.name_Intro,R.id.price});
                    // 添加适配器
                    listView.setAdapter(simpleAdapter);
                }
            }
        });
        // 更新监听事件
        updateWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = wineText.getText().toString();
                if(name == null){
                    showToast("请输入要修改的酒的名称");
                    return;
                }
                Wine wine = wineDao.queryWineByNameWine(name);
                if(wine != null){
                    Intent intent1 = new Intent(IndexActivity.this, UpdateWineActivity.class);
                    intent1.putExtra("name",name);
                    startActivity(intent1);
                }else {
                    showToast("更新错误，没有这个品种的酒");
                }
            }
        });
        // 删除酒的事件，弹出对话框判断是否删除酒
        deleteWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = wineText.getText().toString();
                if("".equals(name)){
                    showToast("请输入要删除的酒的名称");
                }else {
                    Wine wine = wineDao.queryWineByNameWine(name);
                    if(wine == null){
                        showToast("对不起，没有这种酒");
                    }else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(IndexActivity.this);
                        builder.setTitle("删除酒");
                        builder.setIcon(R.drawable.delete);
                        builder.setMessage("确定要删除这种酒吗？？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean success = wineDao.deleteWineByName(name);
                                if(success){
                                    showToast("删除成功");
                                }else {
                                    showToast("删除失败");
                                }
                                showList(IndexActivity.this);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            }
        });
        showAllWines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList(IndexActivity.this);
            }
        });
        showList(this);// 显示酒信息
    }
    // 解析菜单资源文件
    @Override
    /**
     * 因为用户名和修改个人信息都在actionBar上，所以要在这里定义
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();// 实例化一个MenuInflater对象
        inflater.inflate(R.menu.menu,menu);//解析菜单文件
        MenuItem username = menu.findItem(R.id.userN);
        final String name = showUsername();
        username.setTitle(name);
        update = menu.findItem(R.id.updateU);
        update.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                 设置监听器，跳转到更新按钮
                Intent updateIntent = new Intent(IndexActivity.this, UpdateUserActivity.class);
                updateIntent.putExtra("username", name);
                startActivity(updateIntent);
                return true;
            }
        });
        exit = menu.findItem(R.id.exit);
        // 实现注销功能
        exit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                deleteUsername();
                Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 显示全部的白酒信息，返回到一个list集合
     */
    public List<Map<String,Object>> showWine(){
        return wineDao.queryAllWine();
    }

    /**
     * 将显示酒信息封装成一个方法
     */
    public void showList(Context context){
        // 获取 ListView 组件
        listView = (ListView) findViewById(R.id.list_item);
        // 设置适配器
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, showWine(),
                R.layout.item,new String[]{"image","name_Intro","price"},
                new int[]{R.id.image,R.id.name_Intro,R.id.price});
//        // 添加适配器
        listView.setAdapter(simpleAdapter);
    }

    /**
     * 通过 SharedPreferences 保存username
     * @param username
     */
    public void saveUsername(String username){
        SharedPreferences.Editor edit = sp.edit();
        // 先判断是否存在这个用户名
        String userN= sp.getString("username", null);
        if("".equals(userN)){
            edit.putString("username",username);
            edit.commit();
        }else {
            edit.clear();
            edit.putString("username",username);
            edit.commit();
        }
    }

    /**
     * 从 SharedPreferences 中将用户名读出
     * @return
     */
    public String showUsername(){
        String username = sp.getString("username", null);
        return username;
    }

    /**
     * 删除 sharedpreference 中的username数据
     */
    public void deleteUsername(){
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }

    public void showToast(String toastText){
        if (mToast != null)
        {
            mToast.setText(toastText);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
        } else
        {
            mToast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
}
