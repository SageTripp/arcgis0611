package com.example.ucmap.bean;

import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.popup.Popup;
import com.example.ucmap.bean.Entry;

import java.util.List;

/**
 * Created by zkg on 2018/5/18.
 */

public interface ContentExtractorContract {

    List<Entry> getPopupFields(Popup popup);

    List<Entry> getEntriesFromGeoElement(GeoElement geoElement);


}
