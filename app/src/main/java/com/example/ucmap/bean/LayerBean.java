package com.example.ucmap.bean;

/**
 * Created by Administrator on 2018/4/26.
 */

public class LayerBean {

    private int id;
    private String Stname;
    private boolean visible;
    private String describe;

    public LayerBean() {
    }

    public LayerBean(String name) {
        Stname = name;
    }

    public LayerBean(String name, boolean visible) {
        Stname = name;
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStname() {
        return Stname;
    }

    public void setStname(String stname) {
        Stname = stname;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
