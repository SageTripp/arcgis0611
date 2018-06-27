package com.example.ucmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ucmap.activity.R;
import com.example.ucmap.bean.LayerBean;

import java.util.ArrayList;

public class LayerAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<LayerBean> groupList = new ArrayList<LayerBean>();
    private ArrayList<ArrayList<LayerBean>> childList = new ArrayList<ArrayList<LayerBean>>();
    //加载监听接口
    public static interface LoadingListener {
        public void loadingLayer(int id,String name,boolean isVisible);
    }
    private LoadingListener mLoadingListener;
    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }
    public LayerAdapter(Context context,ArrayList<LayerBean> groupList,ArrayList<ArrayList<LayerBean>> childList) {
        mContext = context;
        this.groupList=groupList;
        this.childList=childList;
    }

    /**
     *
     * 获取组的个数
     *
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */

    @Override
    public int getGroupCount()
    {
        return groupList.size();
    }

    /**
     *
     * 获取指定组中的子元素个数
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return childList.get(groupPosition).size();
    }

    /**
     *
     * 获取指定组中的数据
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */

    @Override
    public Object getGroup(int groupPosition)
    {
        return groupList.get(groupPosition);
    }

    /**
     *
     * 获取指定组中的指定子元素数据。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return childList.get(groupPosition).get(childPosition);
    }

    /**
     *
     * 获取指定组的ID，这个组ID必须是唯一的
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    /**
     *
     * 获取指定组中的指定子元素ID
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    /**
     *
     * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     *
     * @return
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, View,
     *      ViewGroup)
     */

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        System.out.println("第"+groupPosition+"行");
        GroupHolder groupHolder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_expendlist_group, null);
            groupHolder = new GroupHolder();
            groupHolder.lny_group_area = (LinearLayout) convertView.findViewById(R.id.lny_group_area);
            groupHolder.txt_group_name = (TextView)convertView.findViewById(R.id.txt_group_name);
            convertView.setTag(groupHolder);
        }
        else
        {
            groupHolder = (GroupHolder)convertView.getTag();
        }

        if(isExpanded){
            groupHolder.lny_group_area.setBackground(mContext.getResources().getDrawable(R.drawable.layer_select_bg));
        }else{
            groupHolder.lny_group_area.setBackground(mContext.getResources().getDrawable(R.drawable.layer_unselect_bg));
        }
        groupHolder.txt_group_name.setText(groupList.get(groupPosition).getStname());
        return convertView;
    }

    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild 子元素是否处于组中的最后一个
     * @param convertView 重用已有的视图(View)对象
     * @param parent 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View,
     *      ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        ItemHolder itemHolder = null;
        if (convertView == null)
        {
            System.out.println("新建第"+groupPosition+"行"+childPosition+"个");
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_expendlist_item, null);
            itemHolder = new ItemHolder();
            itemHolder.ckb_item_checkBox = (CheckBox)convertView.findViewById(R.id.ckb_item_visible);
//            itemHolder.txt_item_name = (TextView)convertView.findViewById(R.id.txt_item_name);
            convertView.setTag(itemHolder);
        }
        else
        {
            System.out.println("已经有了第"+groupPosition+"行"+childPosition+"个");
            itemHolder = (ItemHolder)convertView.getTag();
        }
        final LayerBean bean = childList.get(groupPosition).get(childPosition);
//        itemHolder.txt_item_name.setText(bean.getStname());
        itemHolder.ckb_item_checkBox.setText(bean.getStname());

//        itemHolder.ckb_item_checkBox = (CheckBox)convertView.findViewById(R.id.ckb_item_visible);
//
        itemHolder.ckb_item_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed()){
                 System.out.println(bean.getStname()+"被执行了，哈哈");

                    bean.setVisible(isChecked);
                    mLoadingListener.loadingLayer(bean.getId(), bean.getStname(), bean.isVisible());
                }

            }
        });

        itemHolder.ckb_item_checkBox.setChecked(bean.isVisible());
        return convertView;
    }

    /**
     *
     * 是否选中指定位置上的子元素。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

   public class GroupHolder
    {
        public LinearLayout lny_group_area;
        public TextView txt_group_name;

    }

    public class ItemHolder
    {
        public CheckBox ckb_item_checkBox;
        public TextView txt_item_name;
    }


}
