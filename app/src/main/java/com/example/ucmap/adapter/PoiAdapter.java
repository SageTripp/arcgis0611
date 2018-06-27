package com.example.ucmap.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ucmap.R;

/**
 * Created by zkg on 2018/5/29.
 */

public class PoiAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;
    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.getString(cursor.getColumnIndex("名称"));
    }
    // 将单词信息显示到列表中
    private void setView(View view, Cursor cursor) {
        TextView tvWordItem = (TextView) view;
        tvWordItem.setText(cursor.getString(cursor.getColumnIndex("名称")));
    }
    // 绑定选项到列表中
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setView(view, cursor);
    }
    // 生成新的选项
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.poi_list_item, null);
        setView(view, cursor);
        return view;
    }

    public PoiAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
