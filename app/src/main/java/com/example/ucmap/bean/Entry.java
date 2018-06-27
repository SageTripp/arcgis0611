package com.example.ucmap.bean;

/**
 * Created by zkg on 2018/5/18.
 */

public class Entry {
    private final String mField;
    private final String mValue;

    public Entry(final String field, final String value){
        mField = field;
        mValue = value;
    }

    public String getField() {
        return mField;
    }

    public String getValue() {
        return mValue;
    }
}
