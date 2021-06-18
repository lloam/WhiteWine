package com.mao.whitewine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mao.whitewine.dao.UserDao;

public class LoginActivity extends Activity {

    // 依赖于UserDao
    private UserDao userDao = new UserDao(LoginActivity.this);
    private EditText name;
    private EditText pwd;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button) findViewById(R.id.login);// 获取登录按钮
        // 注册点击事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的用户名与密码框
                name = (EditText) findViewById(R.id.username);
                pwd = (EditText) findViewById(R.id.password);
                String username = name.getText().toString();// 获取用户名
                String password = pwd.getText().toString();// 获取用户密码
                if("".equals(username)||"".equals(password)){// 确保都有数据
                    showToast("请输入用户名或密码");
                }else {
                    // 通过UserDao的查询方法查询是否存在这个用户
                    boolean success = userDao.queryUserByNamePwd(username, password);
                    if(success){
                        // 如果成功，则跳转到首页Activity
                        Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }else {
                        showToast("登陆失败，请检查用户名和密码是否正确");
                    }
                }
            }
        });
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
