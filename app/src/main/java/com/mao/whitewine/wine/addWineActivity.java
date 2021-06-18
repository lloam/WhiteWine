package com.mao.whitewine.wine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mao.whitewine.IndexActivity;
import com.mao.whitewine.R;
import com.mao.whitewine.dao.WineDao;
import com.mao.whitewine.pojo.Wine;

public class addWineActivity extends Activity {

    private WineDao wineDao = new WineDao(addWineActivity.this);
    private EditText photo;
    private EditText nameWine;
    private EditText priceWine;
    private EditText introductionWine;
    private Button addWine;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wine);
        photo = (EditText) findViewById(R.id.winePhotoID);// 获取用户添加的白酒数据
        nameWine = (EditText) findViewById(R.id.wineName);// 获取用户添加的白酒数据
        priceWine = (EditText) findViewById(R.id.winePrice);// 获取用户添加的白酒数据
        introductionWine = (EditText) findViewById(R.id.wineIntroduction);// 获取用户添加的白酒数据
        addWine = (Button) findViewById(R.id.addWine);// 获取用户添加的白酒数据
        addWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的白酒信息
                String photoid = photo.getText().toString();
                int photoID = -1;
                double price = 0;
                // 这里传的是 int 类型的数据，要先判断非空，非空调用 Integer.parseInt 方法会有 NumberFormatException 异常
                // 所以下面的价格同样要这样判断
                if("".equals(photoid)){
                    showToast("请将信息填写完整");
                }else {
                    int i = Integer.parseInt(photoid);
                    if(i > 8){
                        showToast("图片ID只能在0~8之间哦~~");
                    }else {
                        photoID = Integer.parseInt(photoid);
                        String name = nameWine.getText().toString();
                        String priceS = priceWine.getText().toString();
                        if("".equals(priceS)){
                            showToast("请将信息填写完整");
                        }else {
                            price = Double.parseDouble(priceS);
                        }
                        String introduction = introductionWine.getText().toString();
                        // 判断是否都填写了数据
                        if(photoID == -1||price == 0||"".equals(name)||"".equals(introduction)){
                            showToast("请将信息填写完整");
                        }else {
                            // 将白酒信息封装成一个 Wine 对象
                            Wine wine = new Wine(photoID, name, price, introduction);
                            // 调用dao层方法增加白酒品种
                            boolean success = wineDao.addWine(wine);
                            if(success){
                                //　如果添加成功
                                showToast("添加成功");
                                Intent intent = new Intent(addWineActivity.this, IndexActivity.class);
                                startActivity(intent);
                            }else {
                                showToast("添加失败");
                            }
                        }
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
