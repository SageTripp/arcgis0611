package com.example.ucmap.util;

import android.os.StatFs;

/**
 * Created by Administrator on 2018/5/7.
 */

public class Utils {

    public static long getFreeSize(String dataPath) {
        StatFs sf = new StatFs(dataPath);
        long bs = sf.getBlockSize();
        long free = sf.getAvailableBlocks() * bs;
        return free;
    }




}
