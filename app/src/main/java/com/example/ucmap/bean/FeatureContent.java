package com.example.ucmap.bean;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.layers.FeatureLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zkg on 2018/5/18.
 */

public class FeatureContent {
    private final String mLayerName;
    private final FeatureLayer mFeatureLayer;
    private List<Entry> entries = null;
    private Feature mFeature = null;

    public FeatureContent(final FeatureLayer featureLayer){
        mFeatureLayer = featureLayer;
        mLayerName = mFeatureLayer.getName();
        setEntries(new ArrayList<Entry>());
    }

    public String getLayerName() {
        return mLayerName;
    }

    public void setFeature(final Feature feature){
        mFeature = feature;
    }
    public Feature getFeature(){
        return mFeature;
    }
    public FeatureLayer getFeatureLayer(){
        return mFeatureLayer;
    }
    public List<Entry> getEntries() {
        return entries;
    }


    public void setEntries(final List<Entry> entries) {
        this.entries = entries;
    }
}
