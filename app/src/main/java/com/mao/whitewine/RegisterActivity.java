package com.mao.whitewine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mao.whitewine.dao.UserDao;
import com.mao.whitewine.pojo.User;

public class RegisterActivity extends Activity {

    private UserDao userDao = new UserDao(RegisterActivity.this);
    // 提升作用域
    private Button register;
    private EditText name;
    private EditText pwd;
    private RadioGroup sex;
    private EditText age1;
    private EditText addr;
    private Toast mToast;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.name);// 获得用户注册的name输入框
        pwd = (EditText) findViewById(R.id.pwd);// 获得用户注册的密码输入框
        sex = (RadioGroup) findViewById(R.id.sex);// 获得用户注册的性别输入框
        age1 = (EditText) findViewById(R.id.age); // 获得用户注册的年龄输入框
        addr = (EditText) findViewById(R.id.addr);// 获得用户注册的地址输入框
        register = (Button) findViewById(R.id.register);// 获得注册按钮
        //为注册按钮注册监听器＝＝点击事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();// 获得用户输入的name
                String password = pwd.getText().toString();// 获得用户注册的密码
                int sexID = sex.getCheckedRadioButtonId();// 获得用户注册的性别
                String sex = "";
                if(sexID == R.id.man){
                    sex = "男";
                }else {
                    sex = "女";
                }
                Integer age = 0;
                if("".equals(age1.getText().toString())){
                    showToast("请将信息填写完整");
                }else {
                    age = Integer.parseInt(age1.getText().toString());// 获得用户注册的年龄
                }
                String address = addr.getText().toString();// 获得用户注册的地址
                if("".equals(username)||"".equals(password)||"".equals(sex)||"".equals(age)||"".equals(address)){
                    showToast("请将信息填写完整");
                }else {
                    User user = new User(username, password, sex, age, address);// 用户输入信息封装成一个User对象
                    boolean b = userDao.addUser(user);// 通过UserDao添加User对象,返回boolean
                    if(b){
                        // 如果注册成功，并返回主页面登陆
                        showToast("注册成功");
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        // 如果注册失败
                        showToast("注册失败");
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
