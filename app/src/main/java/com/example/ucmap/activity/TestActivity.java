package com.example.ucmap.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ucmap.R;
import com.example.ucmap.adapter.AnalysisResultAdapter;
import com.example.ucmap.adapter.LayerAdapter;
import com.example.ucmap.adapter.MenuPageAdapter;
import com.example.ucmap.bean.LandType;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class TestActivity extends AppCompatActivity implements LayerAdapter.LoadingListener {
    private NavigationTabBar leftMenu;
    private CheckBox toolMenu;
    private RadioGroup toolBar;
    private String selected = "";
    private ViewPager menuPanel;
    private AutoCompleteTextView searchTextView;
    private MenuPageAdapter menuAdapter;
    private RecyclerView analysisResult;
    private ArrayList<LandType> analysisLands = new ArrayList<>();
    private AnalysisResultAdapter adapter;
    private ConstraintLayout analysisResultPanel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        menuPanel = findViewById(R.id.vp_menu);
        menuAdapter = new MenuPageAdapter(getSupportFragmentManager());
        menuPanel.setAdapter(menuAdapter);
        menuPanel.setOffscreenPageLimit(4);
        initLeftMenu();
        initTool();
        searchTextView = findViewById(R.id.atv_location_search);
        initAnalysis();
    }

    private void initAnalysis() {
        analysisResultPanel = findViewById(R.id.con_analysis_result);
        analysisResult = findViewById(R.id.rv_analysis_result);
        adapter = new AnalysisResultAdapter(analysisLands);
        analysisResult.setAdapter(adapter);
        analysisResult.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * 初始化底部工具栏
     */
    private void initTool() {
        toolMenu = findViewById(R.id.cb_tool);
        toolBar = findViewById(R.id.rg_tool_bar);
        toolMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toolBar.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        toolBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_tool_project://项目
                        Toast.makeText(TestActivity.this, "项目", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_tool_raging://测距
                        Toast.makeText(TestActivity.this, "测距", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_tool_area://面积
                        Toast.makeText(TestActivity.this, "面积", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_tool_query://搜索
                        Toast.makeText(TestActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_tool_take_pic://拍照
                        Toast.makeText(TestActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_tool_mark://标注
                        Toast.makeText(TestActivity.this, "标注", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_tool_clear://清除
                        Toast.makeText(TestActivity.this, "清除", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    /**
     * 初始化左侧菜单栏
     */
    private void initLeftMenu() {
        leftMenu = findViewById(R.id.ntb_left_menu);
        NavigationTabBar.Model layerModel = new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.icon_layer_normal),
                getResources().getColor(R.color.white)
        ).title("图层")
                .build();
        NavigationTabBar.Model searchModel = new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.icon_search_normal),
                getResources().getColor(R.color.white)
        ).title("搜索")
                .build();
        NavigationTabBar.Model analysisModel = new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.icon_analysis_normal),
                getResources().getColor(R.color.white)
        ).title("分析")
                .build();
        NavigationTabBar.Model userModel = new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.icon_user_normal),
                getResources().getColor(R.color.white)
        ).title("用户")
                .build();
        List<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(layerModel);
        models.add(searchModel);
        models.add(analysisModel);
        models.add(userModel);
        leftMenu.setModels(models);
        leftMenu.setIsTitled(true);
//        leftMenu.setViewPager(menuPanel);
        leftMenu.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {

            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                Toast.makeText(getApplicationContext(), model.getTitle(), Toast.LENGTH_LONG).show();
                if (selected.equals(model.getTitle())) {
                    selected = "";
                    leftMenu.deselect();
                    menuPanel.setCurrentItem(0);
                } else {
                    selected = model.getTitle();
                    menuPanel.setCurrentItem(index, false);
                }
                switch (selected) {
                    case "图层":
                        break;
                    case "搜索":
                        break;
                    case "分析":
                        break;
                    case "用户":
                        break;
                    default:
                        break;
                }
                menuPanel.setVisibility(selected.isEmpty() ? View.GONE : View.VISIBLE);
                if (!selected.equals("分析")) {
                    analysisResultPanel.setVisibility(View.GONE);
                }
            }
        });
    }


    public void closeMenu(View view) {
        leftMenu.deselect();
        menuPanel.setVisibility(View.GONE);
        analysisResultPanel.setVisibility(View.GONE);
        selected = "";
    }

    /*
    下边都是点击事件
     */


    /**
     * 分析 -> 绘制
     */
    public void drawAnalysis(View view) {
        Toast.makeText(this, "绘制", Toast.LENGTH_SHORT).show();
    }

    /**
     * 分析 -> 重置
     */
    public void resetAnalysis(View view) {
        Toast.makeText(this, "重置", Toast.LENGTH_SHORT).show();
    }

    private void mockAnalysisData() {
        analysisLands.clear();
        int index = (int) (Math.random() * 40 / 2);
        for (int i = 0; i < index; i++) {
            double area = Math.random();
            int num = (int) (Math.random() * 10);
            LandType land = new LandType();
            land.setLandTypeName("土地类型");
            land.setTotalAreas(area);
            land.setTotalNums(num);
            analysisLands.add(land);
        }
        adapter.setLands(analysisLands);
    }

    /**
     * 分析 -> 规划分析
     */
    public void planningAnalysis(View view) {
        Toast.makeText(this, "规划分析", Toast.LENGTH_SHORT).show();
        mockAnalysisData();
        analysisResultPanel.setVisibility(View.VISIBLE);
    }

    /**
     * 分析 -> 现状分析
     */
    public void situationAnalysis(View view) {
        Toast.makeText(this, "现状分析", Toast.LENGTH_SHORT).show();
        mockAnalysisData();
        analysisResultPanel.setVisibility(View.VISIBLE);
    }

    /**
     * 分析 -> 农田分析
     */
    public void farmlandAnalysis(View view) {
        Toast.makeText(this, "农田分析", Toast.LENGTH_SHORT).show();
        mockAnalysisData();
        analysisResultPanel.setVisibility(View.VISIBLE);
    }

    /**
     * 用户 -> 我的消息
     */
    public void myMessage(View view) {
        Toast.makeText(this, "我的消息", Toast.LENGTH_SHORT).show();
    }

    /**
     * 用户 -> 我的办公
     */
    public void myOffice(View view) {
        Toast.makeText(this, "我的办公", Toast.LENGTH_SHORT).show();
    }

    /**
     * 用户 -> 意见反馈
     */
    public void feedback(View view) {
        Toast.makeText(this, "意见反馈", Toast.LENGTH_SHORT).show();
    }

    /**
     * 用户 -> 关于我们
     */
    public void aboutUs(View view) {
        Toast.makeText(this, "关于我们", Toast.LENGTH_SHORT).show();
    }

    /**
     * 用户 -> 退出
     */
    public void exit(View view) {
        Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 定位
     */
    public void location(View view) {
        Toast.makeText(this, "定位", Toast.LENGTH_SHORT).show();
    }

    /**
     * 指向
     */
    public void compass(View view) {
        Toast.makeText(this, "指向", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingLayer(int id, String name, boolean isVisible) {
        Toast.makeText(this, name + (isVisible ? ": 显示" : ": 隐藏"), Toast.LENGTH_SHORT).show();
    }
}
