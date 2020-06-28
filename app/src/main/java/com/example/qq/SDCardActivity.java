package com.example.qq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class SDCardActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "SDCardActivity";
    private Button writeToSDCard;
    private Switch aSwitch;
    private SharedPreferences mySharePreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sd_card);

        /*初始布局*/
        writeToSDCard = findViewById(R.id.writeToSDCard);
        writeToSDCard.setOnClickListener(this);
        aSwitch = findViewById(R.id.open_and_close);
        aSwitch.setOnCheckedChangeListener(this);

        mySharePreferences = getSharedPreferences("settings_info",MODE_PRIVATE);
        //回显
        Boolean status = mySharePreferences.getBoolean("status",false);//后一个参数是默认值
        aSwitch.setChecked(status);
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG,"进入点击事件");
        if(v==writeToSDCard){
//            File filePath = new File("/storage/self/primary");//系统sd卡的路径
            File file = new File("/storage/self/primary","info.txt");  //常用路径
//            File file = new File(filePath,"info.text");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file,true);//加个true表示追加(不会覆盖)
                fileOutputStream.write("成功写入sd卡".getBytes());//写入
                fileOutputStream.write("\r\n".getBytes());//换行
                Log.d(TAG,"存入成功!");
                Toast.makeText(this, "存入成功", Toast.LENGTH_SHORT).show();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG,"current_status:"+isChecked);
        SharedPreferences.Editor editor = mySharePreferences.edit();
        editor.putBoolean("status",isChecked);
        editor.commit();
    }
}
