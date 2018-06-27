package com.example.ucmap.util;
import java.util.HashMap;
/**
 * Created by zkg on 2018/5/14.
 */

    public class GCJ2WGS {
        //圆周率 GCJ_02_To_WGS_84
        double PI = 3.14159265358979324;

        public HashMap<String, Double> delta(double lat,double lon) {
            double a = 6378245.0;//克拉索夫斯基椭球参数长半轴a
            double ee = 0.00669342162296594323;//克拉索夫斯基椭球参数第一偏心率平方
            double dLat = this.transformLat(lon - 105.0, lat - 35.0);
            double dLon = this.transformLon(lon - 105.0, lat - 35.0);
            double radLat = lat / 180.0 * this.PI;
            double magic = Math.sin(radLat);
            magic = 1 - ee * magic * magic;
            double sqrtMagic = Math.sqrt(magic);
            dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * this.PI);
            dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * this.PI);

            HashMap<String, Double> hm = new HashMap<String, Double>();
            hm.put("lat",lat - dLat);
            hm.put("lon",lon - dLon);

            return hm;
        }
        //转换经度
        public double transformLon(double x, double y) {
            double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
            ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
            ret += (20.0 * Math.sin(x * this.PI) + 40.0 * Math.sin(x / 3.0 * this.PI)) * 2.0 / 3.0;
            ret += (150.0 * Math.sin(x / 12.0 * this.PI) + 300.0 * Math.sin(x / 30.0 * this.PI)) * 2.0 / 3.0;
            return ret;
        }
        //转换纬度
        public double transformLat(double x, double y) {
            double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
            ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
            ret += (20.0 * Math.sin(y * this.PI) + 40.0 * Math.sin(y / 3.0 * this.PI)) * 2.0 / 3.0;
            ret += (160.0 * Math.sin(y / 12.0 * this.PI) + 320 * Math.sin(y * this.PI / 30.0)) * 2.0 / 3.0;
            return ret;
        }

    }
