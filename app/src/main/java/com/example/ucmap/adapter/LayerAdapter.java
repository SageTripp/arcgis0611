package com.example.ucmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ucmap.R;
import com.example.ucmap.bean.LayerBean;

import java.util.ArrayList;

public class LayerAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<LayerBean> groupList;
    private ArrayList<ArrayList<LayerBean>> childList;

    private LoadingListener mLoadingListener;

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public LayerAdapter(Context context, ArrayList<LayerBean> groupList, ArrayList<ArrayList<LayerBean>> childList) {
        mContext = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    /**
     * 获取组的个数
     *
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    /**
     * 获取指定组中的子元素个数
     *
     * @param groupPosition 分组位置
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    /**
     * 获取指定组中的数据
     *
     * @param groupPosition 分组位置
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    /**
     * 获取指定组中的指定子元素数据。
     *
     * @param groupPosition 分组位置
     * @param childPosition 条目位置
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    /**
     * 获取指定组的ID，这个组ID必须是唯一的
     *
     * @param groupPosition 分组位置
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取指定组中的指定子元素ID
     *
     * @param groupPosition 分组位置
     * @param childPosition 条目位置
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     *
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象
     * @param parent        返回的视图对象始终依附于的视图组
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, View,
     * ViewGroup)
     */

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_expend_group, null);
            groupHolder = new GroupHolder();
            groupHolder.lny_group_area = convertView.findViewById(R.id.lny_group_area);
            groupHolder.iv_group = convertView.findViewById(R.id.iv_group);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.lny_group_area.setChecked(isExpanded);
        groupHolder.iv_group.setSelected(isExpanded);
        groupHolder.lny_group_area.setText(groupList.get(groupPosition).getStname());
        return convertView;
    }

    /**
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView   重用已有的视图(View)对象
     * @param parent        返回的视图(View)对象始终依附于的视图组
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View,
     * ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ItemHolder itemHolder;
        if (convertView == null) {
            System.out.println("新建第" + groupPosition + "行" + childPosition + "个");
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_expendlist_item, null);
            itemHolder = new ItemHolder();
            itemHolder.ckb_item_checkBox = convertView.findViewById(R.id.ckb_item_visible);
            convertView.setTag(itemHolder);
        } else {
            System.out.println("已经有了第" + groupPosition + "行" + childPosition + "个");
            itemHolder = (ItemHolder) convertView.getTag();
        }
        final LayerBean bean = childList.get(groupPosition).get(childPosition);
        itemHolder.ckb_item_checkBox.setText(bean.getStname());

        itemHolder.ckb_item_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    bean.setVisible(isChecked);
                    if (null != mLoadingListener)
                        mLoadingListener.loadingLayer(bean.getId(), bean.getStname(), bean.isVisible());
                }

            }
        });

        itemHolder.ckb_item_checkBox.setChecked(bean.isVisible());
        return convertView;
    }

    /**
     * 是否选中指定位置上的子元素。
     *
     * @param groupPosition 分组位置
     * @param childPosition 条目位置
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GroupHolder {
        public CheckBox lny_group_area;
        public TextView txt_group_name;

        public ImageView iv_group;
    }

    public class ItemHolder {
        public CheckBox ckb_item_checkBox;
        public TextView txt_item_name;
    }


    //加载监听接口
    public interface LoadingListener {
        void loadingLayer(int id, String name, boolean isVisible);
    }
}
