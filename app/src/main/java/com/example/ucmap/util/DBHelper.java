package com.example.ucmap.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;


import com.example.ucmap.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zkg on 2018/5/29.
 */

public class DBHelper {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "poi.db"; // 保存的数据库文件名
    public static final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/yzt";
    /*// SDCard 定义数据库的存放路径
    private final String DATABASE_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/dictionary";*/

    private Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase() {
        try {
            File myDataPath = new File(DB_PATH);
            if (!myDataPath.exists()) {
                myDataPath.mkdirs();// 如果没有这个目录则创建
            }
            String dbfile = myDataPath + "/" + DB_NAME;
            if (!(new File(dbfile).exists())) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = context.getResources().openRawResource(R.raw.poi); // 欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            return db;
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }
}
