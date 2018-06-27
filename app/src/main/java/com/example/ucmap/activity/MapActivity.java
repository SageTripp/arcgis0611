package com.example.ucmap.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.FillSymbol;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.example.ucmap.R;
import com.example.ucmap.adapter.LayerAdapter;
import com.example.ucmap.bean.BuildLand;
import com.example.ucmap.bean.ContentExtractor;
import com.example.ucmap.bean.FarmLand;
import com.example.ucmap.bean.LayerBean;
import com.example.ucmap.bean.NoUseLand;
import com.example.ucmap.bean.StatusAnalysisResult;
import com.example.ucmap.util.DBHelper;
import com.example.ucmap.widget.CustomPopWindow;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.ViewStateListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by Administrator on 2018/4/24.
 */

public class MapActivity extends Activity implements View.OnClickListener {
    private MapActivity mapActivity;
    private boolean[] visible;
    int type = 0;
    private SearchView mSearchView;
    private AutoCompleteTextView completeText;
    private EditText editView, txt_tool_content;
    private ExpandableListView lVi_layers;
    private LayerAdapter mLayerAdapter;
    private View currentMenu;
    private TextView tv_txt_tool_map, tv_txt_tool_search, tv_txt_tool_analyse, tv_txt_tool_mine, tv_map_tool_map, tv_map_tool_distance, tv_map_tool_area,
            tv_map_tool_query, tv_map_tool_label, tv_map_tool_phono, tv_map_tool_clear, tv_exit;
    private static List<String> list = new ArrayList<String>();
    private static ArrayList<LayerBean> groupList = new ArrayList<LayerBean>();
    private static ArrayList<ArrayList<LayerBean>> childList = new ArrayList<ArrayList<LayerBean>>();
    private DBHelper dbHelper; // 用户输入文本框
    private AutoCompleteTextView poi; // 定义数据库的名字
    private SQLiteDatabase database;
    private MapView mMapView;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明当前位置经纬度

    public double currentLon = 117.195800;
    public double currentLat = 34.056295;
    //声明是否初次定位

    public int firstLocate = 0;
    //定位图层
    public GraphicsOverlay locationGraphic = new GraphicsOverlay();
    //定位图片符号
    PictureMarkerSymbol pictureMarkerSymbol;
    //声明标注图层

    public GraphicsOverlay labelGaphic = new GraphicsOverlay();
    //声明标注符号
    PictureMarkerSymbol labelMarkerSymbol;
    //声明当前工具
    String curtool = "tvDelete";
    //声明底图图层
    ArcGISMap mMap;
    Basemap mStreetMap;
    Basemap mSatelliteMap;
    //声明当前底图

    String curBasemap = "street";
    //声明测量距离图层

    public GraphicsOverlay measureLengthPointGraphic = new GraphicsOverlay();
    public GraphicsOverlay measureLengthLineGraphic = new GraphicsOverlay();
    public GraphicsOverlay measureLengthTextGraphic = new GraphicsOverlay();
    PolylineBuilder polylineBuilder;
    //声明测量面积图层

    public GraphicsOverlay measureAreaPointGraphic = new GraphicsOverlay();
    public GraphicsOverlay measureAreaLineGrapphic = new GraphicsOverlay();
    public GraphicsOverlay measureAreaPolygonGraphic = new GraphicsOverlay();
    public GraphicsOverlay measureAreaTextGraphic = new GraphicsOverlay();
    PointCollection drawPoints = new PointCollection(SpatialReference.create(2383));
    //声明存储测量线段数组

    public ArrayList<Point> mPointsLine = new ArrayList<Point>();

    public String pathname;
    //弹框
    private CustomPopWindow popupWindow;

    private Button btn_analyze, btn_setting, btn_farm, btn_status, btn_search, buhua_search;

    private FeatureLayer farmLayer = null;
    private FeatureLayer statusLayer = null;
    private FeatureLayer planLayer = null;
    private ArcGISTiledLayer guihuatpkLayer = null;
    private ArcGISTiledLayer xianzhuangtpkLayer = null;

    //基本农田面积和总数
    private int farmCount = 0;
    private double farmTotalArea = 0;
    private GraphicsOverlay farmAnalysistAllGraphicsLayer = new GraphicsOverlay();


    private FillSymbol messureFillSymbol_green;//绿色面样式
    private FillSymbol messureFillSymbol_red;//红色面样式

    private FillSymbol messureFillSymbol_blue;//蓝色面样式


    private FillSymbol messureFillSymbol_jbnt;//基本农田样式
    private FillSymbol messureFillSymbol_ybnd;//一般用地样式

    private FillSymbol messureFillSymbol_chzjs;//城镇建设样式
    private FillSymbol messureFillSymbol_cunzjs;//村镇建设样式
    private FillSymbol messureFillSymbol_dlgk;//独立工矿样式

    private FillSymbol messureFillSymbol_fjly;//风景旅游样式
    private FillSymbol messureFillSymbol_sthj;//生态环境样式
    private FillSymbol messureFillSymbol_zrwh;//自然资源样式

    private FillSymbol messureFillSymbol_lyyd;//林业用地样式
    private FillSymbol messureFillSymbol_xmyyd;//畜牧业样式
    private FillSymbol messureFillSymbol_qtyd;//其他
    private StatusAnalysisResult statusAnalysisResult;

    private TextView tv_location;


    private int currentLayoutId = 0;
    private Callout mCallout = null;
    ContentExtractor popupInteractor;
    //农用地、建设用地、未利用
    private FarmLand farmLand;
    private BuildLand buildLand;
    private NoUseLand noUseLand;

    private View lLay_tool_map;
    private View lLay_tool_search;
    private View lLay_tool_analyse;
    private View lLay_tool_mine;

    private View currentToolMenu;
    private View currentToolMap;
    private View currentToolAnalyse;
    private Polygon analysePolygon;
    private LinearLayout linLay_tool;
    private ImageView map_tool_compass;
    private ArcGISTiledLayer tiledLayer;

    static {
        LayerBean bean1 = new LayerBean();
        bean1.setStname("基础地理");
        LayerBean bean2 = new LayerBean();
        bean2.setStname("地政基础");
        LayerBean bean3 = new LayerBean();
        bean3.setStname("土地调查");
        LayerBean bean4 = new LayerBean();
        bean4.setStname("土地权属");
        LayerBean bean5 = new LayerBean();
        bean5.setStname("项目范围");


        groupList.add(bean1);
        groupList.add(bean2);
        groupList.add(bean3);
        groupList.add(bean4);
        groupList.add(bean5);

        {
            ArrayList<LayerBean> list = new ArrayList<LayerBean>();
            LayerBean childBean1 = new LayerBean();
            childBean1.setStname("县级行政区");
            LayerBean childBean2 = new LayerBean();
            childBean2.setStname("乡级行政区");
            LayerBean childBean3 = new LayerBean();
            childBean3.setStname("村级行政区");
            LayerBean childBean4 = new LayerBean();
            childBean4.setStname("遥感影像数据");
            childBean4.setVisible(true);


            list.add(childBean1);
            list.add(childBean2);
            list.add(childBean3);
            list.add(childBean4);

            childList.add(list);
        }

        {
            ArrayList<LayerBean> list = new ArrayList<LayerBean>();
            LayerBean childBean1 = new LayerBean();
            childBean1.setStname("土地利用规划");
            LayerBean childBean2 = new LayerBean();
            childBean2.setStname("土地利用现状");
            LayerBean childBean3 = new LayerBean();
            childBean3.setStname("永久基本农田");
            LayerBean childBean4 = new LayerBean();
            childBean4.setStname("设施农用地");
            list.add(childBean1);
            list.add(childBean2);
            list.add(childBean3);
            list.add(childBean4);
            childList.add(list);
        }
        {
            ArrayList<LayerBean> list = new ArrayList<LayerBean>();
            LayerBean childBean1 = new LayerBean();
            childBean1.setStname("土地报批");
            LayerBean childBean2 = new LayerBean();
            childBean2.setStname("土地供地");
            LayerBean childBean3 = new LayerBean();
            childBean3.setStname("工业仓储用地");
            LayerBean childBean4 = new LayerBean();
            childBean4.setStname("农村建设用地");
            LayerBean childBean5 = new LayerBean();
            childBean5.setStname("农村流转土地");
            LayerBean childBean6 = new LayerBean();
            childBean6.setStname("批而未建土地");
            LayerBean childBean7 = new LayerBean();
            childBean7.setStname("闲置地");
            list.add(childBean1);
            list.add(childBean2);
            list.add(childBean3);
            list.add(childBean4);
            list.add(childBean5);
            list.add(childBean6);
            list.add(childBean7);
            childList.add(list);
        }

        {
            ArrayList<LayerBean> list = new ArrayList<LayerBean>();
            LayerBean childBean1 = new LayerBean();
            childBean1.setStname("土地所有权");
            LayerBean childBean2 = new LayerBean();
            childBean2.setStname("宗地");
            list.add(childBean1);
            list.add(childBean2);
            childList.add(list);
        }

        {
            ArrayList<LayerBean> list = new ArrayList<LayerBean>();
            LayerBean childBean1 = new LayerBean();
            childBean1.setStname("项目范围");
            LayerBean childBean2 = new LayerBean();
            childBean2.setStname("三区分布");
            list.add(childBean1);
            list.add(childBean2);
            childList.add(list);
        }

    }
    // exit

    private long mExitTime;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if(SharedPreferencesUtil.getValue(this,"isFirst",true)){
            initMapData();
        }
        */
        //添加ArcGIS许可
        //ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none,RP5X0H4AH7CLJ9HSX018");
        setContentView(R.layout.activity_main);
        //加载地图
        mMapView = findViewById(R.id.mapView);
        /*
        ArcGISTiledLayer  streetTiledLayer = new ArcGISTiledLayer("http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer");
        Basemap streetBasemap=new Basemap(streetTiledLayer);
        mStreetMap=new ArcGISMap(streetBasemap);
        mMapView.setMap(mStreetMap);
        */
        mMap = new ArcGISMap(Basemap.Type.OPEN_STREET_MAP, 34.056295, 117.195800, 16);
        mStreetMap = Basemap.createOpenStreetMap();
        mSatelliteMap = Basemap.createImageryWithLabels();
        mMapView.setMap(mMap);
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                switch (curtool) {
                    case "tvLabel":
                        // tvLabel(e);
                        break;
                    case "tvDistance":
                        //   tvDistance(e);
                        break;
                    case "tvArea":
                        //  tvArea(e);
                        break;
                    case "tvQuery":
                        //   tvQuery(e);
                        break;
                    case "draw":
                        //  drawFarm(e);
                        break;
                }
                return true;
            }

        });

        tv_map_tool_map = findViewById(R.id.map_tool_map);
        tv_map_tool_distance = findViewById(R.id.map_tool_distance);
        tv_map_tool_area = findViewById(R.id.map_tool_area);
        tv_map_tool_query = findViewById(R.id.map_tool_query);
        tv_map_tool_label = findViewById(R.id.map_tool_label);
        tv_map_tool_phono = findViewById(R.id.map_tool_phono);
        tv_map_tool_clear = findViewById(R.id.map_tool_clear);
        tv_txt_tool_map = findViewById(R.id.txt_tool_map);
        tv_txt_tool_search = findViewById(R.id.txt_tool_search);
        tv_txt_tool_analyse = findViewById(R.id.txt_tool_analyse);
        tv_txt_tool_mine = findViewById(R.id.txt_tool_mine);

        lLay_tool_map = findViewById(R.id.lLay_tool_map);
        lLay_tool_search = findViewById(R.id.lLay_tool_search);
        lLay_tool_analyse = findViewById(R.id.lLay_tool_analyse);
        lLay_tool_mine = findViewById(R.id.lLay_tool_mine);
        linLay_tool = findViewById(R.id.linearLayout);
        //搜索中的listview
        txt_tool_content = findViewById(R.id.txt_tool_content);
        btn_search = findViewById(R.id.btn_search);
        buhua_search = findViewById(R.id.btn_buhua);
        findViewById(R.id.txt_tool_map).setOnClickListener(this);
        findViewById(R.id.txt_tool_search).setOnClickListener(this);
        findViewById(R.id.txt_tool_analyse).setOnClickListener(this);
        findViewById(R.id.txt_tool_mine).setOnClickListener(this);

        findViewById(R.id.map_tool_compass).setOnClickListener(this);
        findViewById(R.id.map_tool_location).setOnClickListener(this);
        findViewById(R.id.map_tool_map).setOnClickListener(this);

        findViewById(R.id.map_tool_distance).setOnClickListener(this);
        findViewById(R.id.map_tool_area).setOnClickListener(this);
        findViewById(R.id.map_tool_query).setOnClickListener(this);
        findViewById(R.id.map_tool_label).setOnClickListener(this);
        findViewById(R.id.map_tool_phono).setOnClickListener(this);
        findViewById(R.id.map_tool_clear).setOnClickListener(this);
        findViewById(R.id.analyse_tool_draw).setOnClickListener(this);
        findViewById(R.id.guihua_analyse_tool_analyse).setOnClickListener(this);
        findViewById(R.id.analyse_tool_reset).setOnClickListener(this);
        findViewById(R.id.xianzhuang_analyse_tool_analyse).setOnClickListener(this);
        findViewById(R.id.nongtian_analyse_tool_analyse).setOnClickListener(this);
        map_tool_compass = (ImageView) findViewById(R.id.map_tool_compass);
        map_tool_compass.setOnClickListener(this);
        findViewById(R.id.map_tool_zoom_in).setOnClickListener(this);
        findViewById(R.id.map_tool_zoom_out).setOnClickListener(this);
        currentToolMenu = findViewById(R.id.txt_tool_map);
        currentToolMenu.setSelected(true);

        currentToolMap = findViewById(R.id.map_tool_map);
        //currentToolMap.setSelected(true);

        currentToolAnalyse = findViewById(R.id.guihua_analyse_tool_analyse);
        // currentToolAnalyse.setSelected(true);

        lVi_layers = (ExpandableListView) findViewById(R.id.lVi_layers);
        mLayerAdapter = new LayerAdapter(this, groupList, childList);
        lVi_layers.setAdapter(mLayerAdapter);

        //mLayerAdapter.setLoadingListener(this);
        lVi_layers.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = lVi_layers.getExpandableListAdapter().getGroupCount();
                for (int i = 0; i < count; i++) {
                    if (i != groupPosition) {
                        lVi_layers.collapseGroup(i);
                    }
                }
            }
        });


    }


    //点击按钮事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_tool_map:
                tv_txt_tool_map.setTextColor(Color.rgb(18, 150, 219));
                if (currentToolMenu != v) {
                    currentToolMenu.setSelected(false);
                    v.setSelected(true);
                    currentToolMenu = v;

                    lLay_tool_map.setVisibility(View.VISIBLE);
                    lLay_tool_search.setVisibility(View.GONE);
                    lLay_tool_analyse.setVisibility(View.GONE);
                    lLay_tool_mine.setVisibility(View.GONE);
                }
                break;
            case R.id.txt_tool_search:

                tv_txt_tool_search.setTextColor(Color.rgb(18, 150, 219));
                if (currentToolMenu != v) {
                    currentToolMenu.setSelected(false);
                    v.setSelected(true);
                    currentToolMenu = v;

                    lLay_tool_map.setVisibility(View.GONE);
                    lLay_tool_search.setVisibility(View.VISIBLE);
                    lLay_tool_analyse.setVisibility(View.GONE);
                    lLay_tool_mine.setVisibility(View.GONE);
                }
                break;
            case R.id.txt_tool_analyse:
                tv_txt_tool_analyse.setTextColor(Color.rgb(18, 150, 219));
                if (currentToolMenu != v) {
                    currentToolMenu.setSelected(false);
                    v.setSelected(true);
                    currentToolMenu = v;

                    lLay_tool_map.setVisibility(View.GONE);
                    lLay_tool_search.setVisibility(View.GONE);
                    lLay_tool_analyse.setVisibility(View.VISIBLE);
                    lLay_tool_mine.setVisibility(View.GONE);
                }
                break;
            case R.id.txt_tool_mine:
                tv_txt_tool_mine.setTextColor(Color.rgb(18, 150, 219));
                if (currentToolMenu != v) {
                    currentToolMenu.setSelected(false);
                    v.setSelected(true);
                    currentToolMenu = v;

                    lLay_tool_map.setVisibility(View.GONE);
                    lLay_tool_search.setVisibility(View.GONE);
                    lLay_tool_analyse.setVisibility(View.GONE);
                    lLay_tool_mine.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void resetAnalyze() {
        farmAnalysistAllGraphicsLayer.getGraphics().clear();
        drawPoints.clear();
    }

    //两次确认退出按钮
//     private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

//        //判断用户是否点击了“返回键”
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            //与上次点击返回键时刻作差
//            if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                //大于2000ms则认为是误操作，使用Toast进行提示
//                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                //并记录下本次点击“返回键”的时刻，以便下次进行判断
//                mExitTime = System.currentTimeMillis();
//            } else {
//                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
////                System.exit(0);
//
//                ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
//                am.restartPackage(getPackageName());
//            }
//            return true;
//        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showPositionInMap(double currentLat, double currentLon) {
        SpatialReference spacRef = SpatialReference.create(4326);
        final Point point = new Point(currentLon, currentLat, spacRef);
        locationGraphic.getGraphics().clear();
        Resources resources = getResources();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(resources, R.drawable.location));
        final PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(bitmapDrawable);
        pictureMarkerSymbol.loadAsync();
        pictureMarkerSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                Graphic graphic = new Graphic(point, pictureMarkerSymbol);
                locationGraphic.getGraphics().add(graphic);
                mMapView.setViewpointCenterAsync(point, 2000).addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        firstLocate = 1;
                        Log.e("平移到这里", "");
                    }
                });
            }
        });
    }


    //向上弹出弹框
    public void showUpPop(View view, int id, int width, int height) {
        if (popupWindow != null) {
            popupWindow.dissmiss();

        }

        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(view)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .setTouchable(true)
                .enableOutsideTouchableDissmiss(false)// 设置点击PopupWindow之外的地方，popWindow不关闭，如果不设置这个属性或者为true，则关闭
                .create();

        popupWindow.showAsDropDown(linLay_tool, 0, -(popupWindow.getHeight() + linLay_tool.getMeasuredHeight()));

        //得到button的左上角坐标
//        int[] positions = new int[2];
//        view.getLocationOnScreen(positions);
//        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, positions[0], positions[1] - popupWindow.getHeight());
    }

    //弹出悬浮窗
    public void showFloatWindow(View view, int id, int width, int height) {


        if (curtool == "btn_farm") {
            hideFloatWindow();
            FloatWindow
                    .with(getApplicationContext())
                    .setView(view)
                    .setTag("btn_farm")
                    .setX((int) linLay_tool.getX())
                    .setY((int) (linLay_tool.getY() - linLay_tool.getMeasuredHeight()))
                    .setMoveType(MoveType.active)
                    .setFilter(true, MapActivity.class)
                    .setViewStateListener(mViewStateListener)
                    .setPermissionListener(mPermissionListener)
                    .setDesktopShow(true)
                    .build();
            FloatWindow.get("btn_farm").show();
        } else if (curtool == "btn_status") {
            hideFloatWindow();

            FloatWindow
                    .with(getApplicationContext())
                    .setView(view)
                    .setTag("btn_status")
                    .setX((int) linLay_tool.getX())
                    .setY((int) (linLay_tool.getY() - linLay_tool.getMeasuredHeight()))
                    .setMoveType(MoveType.active)
                    .setFilter(true, MapActivity.class)
                    .setViewStateListener(mViewStateListener)
                    .setPermissionListener(mPermissionListener)
                    .setDesktopShow(true)
                    .build();
            FloatWindow.get("btn_status").show();
        } else if (curtool == "btn_plan") {
            hideFloatWindow();

            FloatWindow
                    .with(getApplicationContext())
                    .setView(view)
                    .setTag("btn_plan")
                    .setX((int) linLay_tool.getX())
                    .setY((int) (linLay_tool.getY() - linLay_tool.getMeasuredHeight()))
                    .setMoveType(MoveType.active)
                    .setFilter(true, MapActivity.class)
                    .setViewStateListener(mViewStateListener)
                    .setPermissionListener(mPermissionListener)
                    .setDesktopShow(true)
                    .build();
            FloatWindow.get("btn_plan").show();
        }


    }

    private void hideFloatWindow() {
        if (FloatWindow.get("btn_farm") != null) {
            FloatWindow.get("btn_farm").hide();
            FloatWindow.destroy("btn_farm");

        }
        if (FloatWindow.get("btn_status") != null) {
            FloatWindow.get("btn_status").hide();

            FloatWindow.destroy("btn_status");

        }
        if (FloatWindow.get("btn_plan") != null) {
            FloatWindow.get("btn_plan").hide();

            FloatWindow.destroy("btn_plan");

        }
    }

    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.e(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Toast.makeText(MapActivity.this, "请开启悬浮窗权限", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onFail");
        }
    };

    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };

    public void tvLabel(MotionEvent e) {
        android.graphics.Point labelpoint = new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY()));
        final com.esri.arcgisruntime.geometry.Point point1 = mMapView.screenToLocation(labelpoint);
        Resources resources = getResources();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(resources, R.drawable.label2));
        final PictureMarkerSymbol labelMarkerSymbol = new PictureMarkerSymbol(bitmapDrawable);
        labelMarkerSymbol.loadAsync();
        labelMarkerSymbol.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                Graphic graphic = new Graphic(point1, labelMarkerSymbol);
                labelGaphic.getGraphics().add(graphic);
            }
        });
    }


    private void showLayersByNames(String name) {
        for (Layer layer : mMapView.getMap().getOperationalLayers()) {
            if (layer.getName().equals(name)) {
                if (name.equals("土地利用规划")) {
                    guihuatpkLayer.setVisible(true);
                }
                if (name.equals("土地利用现状")) {
                    xianzhuangtpkLayer.setVisible(true);
                }
                layer.setVisible(true);
            }

        }

    }

    private void hideLayersByNames(String name) {
        for (Layer layer : mMapView.getMap().getOperationalLayers()) {
            if (layer.getName().equals(name)) {
                layer.setVisible(false);
            }

        }

    }

    private boolean isLoadedByNames(String name) {
        for (Layer layer : mMapView.getMap().getOperationalLayers()) {
            if (layer.getName().equals(name)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    private Layer getLayerByNames(String name) {
        for (Layer layer : mMapView.getMap().getOperationalLayers()) {
            if (layer.getName().equals(name)) {
                return layer;
            }

        }
        return null;
    }

}
