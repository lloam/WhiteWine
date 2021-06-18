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

import java.util.List;
import java.util.Map;

public class UpdateWineActivity extends Activity {

    private EditText updateWinePhotoID;
    private EditText updateWineName;
    private EditText updateWinePrice;
    private EditText updateWineIntroduction;
    private Button updateWineBtn;
    private WineDao wineDao = new WineDao(UpdateWineActivity.this);
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_wine);
        updateWinePhotoID = (EditText) findViewById(R.id.updateWinePhotoID);
        updateWineName = (EditText) findViewById(R.id.updateWineName);
        updateWinePrice = (EditText) findViewById(R.id.updateWinePrice);
        updateWineIntroduction = (EditText) findViewById(R.id.updateWineIntroduction);
        updateWineBtn = (Button) findViewById(R.id.updateWineBtn);
        Intent intent = getIntent();
        // 获取IndexActivity传递的参数
        final String name = intent.getStringExtra("name");
        final Wine wine = wineDao.queryWineByNameWine(name);// 查询出要更新的白酒信息，并显示
        updateWinePhotoID.setText(String.valueOf(wine.getPhotoID()));
        updateWineName.setText(wine.getName());
        updateWinePrice.setText(String.valueOf(wine.getPrice()));
        updateWineIntroduction.setText(wine.getIntroduction());

        // 设置监听事件
        updateWineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String photo = updateWinePhotoID.getText().toString();
                int photoID = -1;
                double price = 0;
                // 这里传的是 int 类型的数据，要先判断非空，非空调用 Integer.parseInt 方法会有 NumberFormatException 异常
                // 所以下面的价格同样要这样判断
                if("".equals(photo)){
                    showToast("请将信息填写完整");
                }else {
                    int i = Integer.parseInt(photo);
                    if(i > 8){
                        showToast("图片ID只能在0~8之间哦~~");
                    }else {
                        photoID = Integer.parseInt(photo);
                        String name = updateWineName.getText().toString();
                        String priceS = updateWinePrice.getText().toString();
                        if("".equals(priceS)){
                            showToast("请将信息填写完整");
                        }else {
                            price = Double.parseDouble(priceS);
                        }
                        String introduction = updateWineIntroduction.getText().toString();
                        int id = wine.getId();
                        // 判断是否都填写了数据
                        if(photoID == -1||price == 0||"".equals(name)||"".equals(introduction)){
                            showToast("请将信息填写完整");
                        }else {
                            Wine wine1 = new Wine(id, photoID, name, price, introduction);
                            boolean success = wineDao.updateWine(wine1);
                            if(success){
                                showToast("更新成功");
                                Intent intent1 = new Intent(UpdateWineActivity.this, IndexActivity.class);
                                startActivity(intent1);
                            }else {
                                showToast("更新失败，请检查是否都有数据");
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
