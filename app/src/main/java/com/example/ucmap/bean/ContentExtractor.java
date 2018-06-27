package com.example.ucmap.bean;

import android.content.Context;
import android.util.Log;

import com.esri.arcgisruntime.data.Field;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.popup.Popup;
import com.esri.arcgisruntime.mapping.popup.PopupField;
import com.esri.arcgisruntime.mapping.popup.PopupFieldFormat;
import com.esri.arcgisruntime.mapping.popup.PopupManager;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by zkg on 2018/5/18.
 */

public class ContentExtractor implements ContentExtractorContract {

    private final Context mContext;
    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private static final String TAG = ContentExtractor.class.getSimpleName();


    public ContentExtractor(final Context context){
        mContext = context;
    }

    @Override public List<Entry> getPopupFields(final Popup popup) {

        final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        final PopupFieldFormat dateFormat = new PopupFieldFormat();
        dateFormat.setDateFormat(PopupFieldFormat.DateFormat.SHORT_DATE_SHORT_TIME);

        final PopupManager mPopupManager = new PopupManager(mContext, popup);
        final List<Entry> entries = new ArrayList<>();
        final List<PopupField> fields = mPopupManager.getDisplayedFields();
        for (final PopupField field : fields) {
            final Field.Type fieldType = mPopupManager.getFieldType(field);
            final Object fieldValue = mPopupManager.getFieldValue(field);
            final String fieldLabel = field.getLabel();
            String value = "";
            if (fieldType == Field.Type.DATE && fieldValue !=null ){
                final GregorianCalendar date = (GregorianCalendar) fieldValue;
                value = formatter.format(date.getTime());
                final Entry entry = new Entry(fieldLabel, value);
                entries.add(entry);

            }else if (fieldType == Field.Type.TEXT && fieldValue != null) {
                value = fieldValue.toString();
                final Entry entry = new Entry(fieldLabel, value);
                entries.add(entry);
            }

        }

        return entries;
    }

    /**
     * Extract attributes from a GeoElement and return
     * a list of <Entry></Entry> items
     * @param geoElement - GeoElement
     * @return - List<Entry></Entry>
     */

    @Override
    public List<Entry> getEntriesFromGeoElement(GeoElement geoElement) {
        final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        final PopupFieldFormat dateFormat = new PopupFieldFormat();
        dateFormat.setDateFormat(PopupFieldFormat.DateFormat.SHORT_DATE_SHORT_TIME);

        Map<String,Object> attrMap =geoElement.getAttributes();
        List<Entry> entries = new ArrayList<>(attrMap.size());
        Set<String> keys = attrMap.keySet();
        for (String key: keys){
            Object o = attrMap.get(key);
            if (o != null){
//                String camelCase = key.substring(0,1) + key.substring(1).toLowerCase();
                // FANGFA1
                Properties proper = ProperTies.getProperties(mContext.getApplicationContext());
                 String camelCase = proper.getProperty(key);


                if (o instanceof GregorianCalendar){
                    final GregorianCalendar date = (GregorianCalendar) o;
                    final String value = formatter.format(date.getTime());

                    entries.add(new Entry(camelCase, value));
                }else{
                    entries.add(new Entry(camelCase, o.toString()));
                }

                Log.i(TAG,o.toString());
            }
        }
        return entries;
    }

}
