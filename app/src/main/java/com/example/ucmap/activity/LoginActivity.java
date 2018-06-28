package com.example.ucmap.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ucmap.R;
import com.example.ucmap.util.DBHelper;
import com.example.ucmap.util.ICallback;
import com.example.ucmap.util.InitData;
import com.example.ucmap.util.SharedPreferencesUtil;
import com.example.ucmap.util.Utils;

/**
 * Created by Administrator on 2018/4/24.
 */

public class LoginActivity extends Activity implements ICallback {

    private static final long UNZIPTOTALSIZE = 5 * 1024 * 1024;//5M
    private ProgressDialog pBar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    long unzipCount = (Long) msg.obj;
                    pBar.setProgress((int) (((unzipCount * 100) / UNZIPTOTALSIZE)));
                    break;
                case 12:
                    pBar.dismiss();
                    Toast.makeText(LoginActivity.this, "初始化失败!,3秒后将退出", Toast.LENGTH_SHORT).show();
                    postDelayed(new Runnable() {
                        public void run() {
                            LoginActivity.this.finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                        }
                    }, 3000);
                    break;
                case 13:
                    pBar.dismiss();
                    break;
            }
        }
    };


    private DBHelper dbHelper; // 用户输入文本框
    private SQLiteDatabase database;
    private EditText edt_username, edt_userpwd;
    private String passResult;
    private Button btn_login;
    private CheckBox cb_remerber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        dbHelper = new DBHelper(getBaseContext());// 打开数据库
//        database = dbHelper.openDatabase();
        edt_username = findViewById(R.id.edt_username);
        edt_userpwd = findViewById(R.id.edt_userpwd);
        btn_login = findViewById(R.id.btn_login);
//        cb_remerber  = findViewById(R.id.cb_remerber);
//        try{
//  load();
//        }catch (IOException e){
//
//            e.printStackTrace();
//        }


        // 点击登陆，写入账户密码的方法

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = edt_username.getText().toString();
                String passWord = edt_userpwd.getText().toString();
//                String sql = "select passWord from users where username = ? ";
//                Cursor cursor = database.rawQuery(sql, new String[] {userName});

                if (userName.equals("1") && passWord.equals("1")) {
                    Intent intent = new Intent(LoginActivity.this, TestActivity.class);
                    startActivity(intent);
                    return;
                }

                if (checkUser(userName, passWord)) {

                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(intent);

                } else {

                    Toast.makeText(LoginActivity.this, "请确认帐号密码正确", Toast.LENGTH_LONG).show();

                }


            }
        });
        if (SharedPreferencesUtil.getValue(this, "isFirst", true)) {
            initMapData();
        }
    }

//    private void load() throws IOException {
//        FileInputStream fiStream = null;
//        BufferedReader br = null;
//        file = new File(getFilesDir(), "info.txt");
//        if (file.exists()) {
//            try {
//                fiStream = new FileInputStream(file);
//                /* 将字节流转化为字符流，转化是因为我们知道info.txt
//                 * 只有一行数据，为了使用readLine()方法，所以我们这里
//                 * 转化为字符流，其实用字节流也是可以做的。但比较麻烦
//                 */
//                br = new BufferedReader(new InputStreamReader(fiStream));
//                //读取info.txt
//                String str = br.readLine();
//                //分割info.txt里面的内容。这就是为什么写入的时候要加入##的原因
//                String arr[] = str.split("##");
//                edt_username.setText(arr[0]);
//                edt_userpwd.setText(arr[1]);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                if (br != null) {
//                    br.close();
//                }
//            }
//        } else {
//
//        }
//    }

    @Override
    public void onFaile() {
        handler.sendEmptyMessage(12);
    }

    @Override
    public void onSuccess() {
        SharedPreferencesUtil.putValue(LoginActivity.this, "isFirst", false);
        handler.sendEmptyMessage(13);
    }

    @Override
    public void callback(long count) {
        Log.e("message", "count:" + count);
    }

    public void initMapData() {
        try {
            long freeSize = Utils.getFreeSize(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (freeSize < (UNZIPTOTALSIZE + 10 * 1024 * 1024)) {
                Toast.makeText(LoginActivity.this, "空间不足！,3秒后将退出", Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        LoginActivity.this.finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                }, 3000);
                return;
            }

//            pBar = new ProgressDialog(LoginActivity.this);
//            pBar.setMessage("正在初始化地图数据中，请稍等...");
//            pBar.setCancelable(false);
//            pBar.show();

            new Thread() {
                public void run() {
                    try {
                        new InitData(LoginActivity.this.getAssets(), LoginActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(12);
                    }
                }
            }.start();
        } catch (Exception e) {
            handler.sendEmptyMessage(12);
        }
    }

    String users, userName, passWord;

    public boolean checkUser(String username, String password) {
        dbHelper = new DBHelper(this);// 打开数据库
        database = dbHelper.openDatabase();

//        Cursor cursor2 = database.rawQuery("select id from users", null);

        String sql = "select id from users where userName=? " + " And " + " passWord=?";
//        Cursor c = database.query("users",new String[]{"id"}," userName=? And passWord=?",new String[]{username,password},null,null,null);
        Cursor cursor = database.rawQuery(sql, new String[]{username, password});
        int cursorCount = cursor.getCount();
//        cursor.close();
//        database.close();
        if (cursorCount > 0) {
            return true;
        }
        cursor.close();
        database.close();
        return true;
    }

}
