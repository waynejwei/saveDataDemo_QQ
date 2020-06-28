package com.example.qq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText account,password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化布局
        initView();
        //监听事件
        initListener();

    }

    /*
    * 第二次登陆时可以直接回显出账号密码
    * @Param
    * */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            FileInputStream fileInputStream = this.openFileInput("info.text");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String info = bufferedReader.readLine();
            String[] s= info.split("\\*\\*\\*");//因为*是特殊字符，需要转义一下
            String accountText = s[0];
            String passwordText = s[1];
            //回显出来
            account.setText(accountText);
            password.setText(passwordText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerLoginEvent(v);
            }
        });
    }

    /*
    * 处理登陆事件
    * @Param View
    * */
    private void handlerLoginEvent(View v) {
        String accountText = account.getText().toString();
        String passwordText = password.getText().toString();
        //数据判空
        if(TextUtils.isEmpty(accountText)){
            Toast.makeText(this, "账号不能为空..", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(passwordText)){
            Toast.makeText(this, "密码不能为空..", Toast.LENGTH_SHORT).show();
        }
        //将数据保存在文件中
        saveUserInfo(accountText,passwordText);
        //清空输入框的文字
        account.setText("");
        password.setText("");
    }

    /*
    * 将用户账号和密码存入文件中
    * @Param account password
    * */
    private void saveUserInfo(String accountText, String passwordText) {
        Log.d(TAG, "保存用户信息...");
        Log.d(TAG, "file_dir"+getFilesDir());//显示该项目的文件路径
        Log.d(TAG, "cache_dir"+getCacheDir());//显示该项目的缓存路径
        File myFile = new File(getFilesDir(),"info.text");  //常用路径
//        File myFile = new File("/data/data/com.example.qq/info.text");//每一个Android的app都有一个文件
        try {
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(myFile,true);//加个true表示追加(不会覆盖)
            fileOutputStream.write((accountText+"***"+passwordText).getBytes());//写入
            fileOutputStream.write("\r\n".getBytes());//换行
            Log.d(TAG,"存入成功!");
            Toast.makeText(this, "存入成功", Toast.LENGTH_SHORT).show();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 初始化布局
    * @Param
    * */
    private void initView() {
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
    }


}
