package com.mao.whitewine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mao.whitewine.dao.UserDao;
import com.mao.whitewine.pojo.User;

public class UpdateUserActivity extends Activity {
    private UserDao userDao = new UserDao(UpdateUserActivity.this);
    // 提升作用域
    private Button register;
    private EditText name;
    private EditText pwd;
    private RadioGroup sex;
    private EditText age1;
    private EditText addr;
    private int id;// 设置用户等下修改的ID
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        register = (Button) findViewById(R.id.registerUpdate );// 获取注册按钮
        name = (EditText) findViewById(R.id.nameUpdate);// 获取用户对应信息的组件
        pwd = (EditText) findViewById(R.id.pwdUpdate);// 获取用户对应信息的组件
        sex = (RadioGroup) findViewById(R.id.sexUpdate);// 获取用户对应信息的组件
        age1 = (EditText) findViewById(R.id.ageUpdate);// 获取用户对应信息的组件
        addr = (EditText) findViewById(R.id.addrUpdate);// 获取用户对应信息的组件
        final Intent intent = getIntent();// 获取首页传过来的参数，为了查询出要更新哪个用户
        String username1 = intent.getStringExtra("username");// 获取用户名参数
        User user1 = userDao.queryUserByName(username1);// 查询出更新的用户
        id = user1.getId();
        name.setText(user1.getUsername());// 设置组件对应的原先的用户信息
        pwd.setText(user1.getPassword());// 设置组件对应的原先的用户信息
        RadioButton man = (RadioButton) findViewById(R.id.manUpdate);// 设置组件对应的原先的用户信息
        RadioButton woman = (RadioButton) findViewById(R.id.womanUpdate);
        if("男".equals(user1.getSex())){
            man.setChecked(true);
        }else {
            woman.setChecked(true);
        }
        age1.setText(String.valueOf(user1.getAge()));// 设置组件对应的原先的用户信息
        addr.setText(user1.getAddress());// 设置组件对应的原先的用户信息
        register.setOnClickListener(new View.OnClickListener() {
            // 设置监听事件
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();//获取用户修改后的信息
                String password = pwd.getText().toString();//获取用户修改后的信息
                int sexID = sex.getCheckedRadioButtonId();//获取用户修改后的信息
                String sex = "";
                if(sexID == R.id.manUpdate){
                    sex = "男";
                }else {
                    sex = "女";
                }
                Integer age = Integer.parseInt(age1.getText().toString());//获取用户修改后的信息
                String address = addr.getText().toString();//获取用户修改后的信息
                if("".equals(username)||"".equals(password)||"".equals(sex)||"".equals(age)||"".equals(address)){
                    showToast("请检查您修改的是否都有数据");
                }else {
                    User user = new User(id,username, password, sex, age, address);
                    boolean success = userDao.updateUser(user);// 修改用户数据
                    if(success){
                        // 如果成功，则跳转到MainActivity进行重新登录
                        showToast("修改成功，请重新登陆");
                        Intent intent1 = new Intent(UpdateUserActivity.this,MainActivity.class);
                        // 同时要将 SharedPreferences 中的数据删除
                        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.clear();
                        edit.commit();
                        startActivity(intent1);
                    }else {
                        showToast("修改失败，请检查是否修改正确");
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
