package com.example.ucmap.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.example.ucmap.R;
import java.util.List;

/**
 * Created by Administrator on 2018/6/7.
 */

public class QueryResultAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Feature> mList;
    private MapView mapView;


    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public QueryResultAdapter(Context context, List<Feature> list,MapView mMapView) {

        this.mapView=mMapView;
        mInflater = LayoutInflater.from(context);
        mList = list;

    }

    //返回数据集的长度
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //这个方法才是重点，我们要为它编写一个ViewHolder
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.feature_list_item, parent, false); //加载布局
            holder = new ViewHolder();
            holder.descTv = (TextView) convertView.findViewById(R.id.descTv);
            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }
        Feature feature = mList.get(position);
        if(feature.getFeatureTable().getTableName().equals("JBNT_YJJBNTTB")){
            Object obj=feature.getAttributes().get("JBNTMJ");
        }
        Object obj=feature.getAttributes().get("BSM");
        String value=obj.toString();
        holder.descTv.setText(value);

        holder.descTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllFeatureSelect();
                setFeatureSelect(mList.get(position));
            }
        });
        return convertView;
    }
    /**
     * 清空所有要素选择
     */
    public void clearAllFeatureSelect(){
        List<Layer> layers = mapView.getMap().getOperationalLayers();
        for (int i=0;i<layers.size();i++){
            if(layers.get(i) instanceof FeatureLayer) {
                FeatureLayer featureLayer = (FeatureLayer) layers.get(i);
                featureLayer.clearSelection();
            }
        }
    }
    /**
     * 设置要素选中
     * @param feature
     */
    public void setFeatureSelect(Feature feature) {
        //设置要素选中
        FeatureLayer identifiedidLayer=feature.getFeatureTable().getFeatureLayer();
        identifiedidLayer.setSelectionColor(Color.YELLOW);
        identifiedidLayer.setSelectionWidth(20);
        identifiedidLayer.selectFeature(feature);

        Geometry buffer = GeometryEngine.buffer(feature.getGeometry(),200);//缓冲500
        mapView.setViewpointGeometryAsync(buffer);
//        mapView.setViewpointScaleAsync(50000);
    }
    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView descTv;
    }
}