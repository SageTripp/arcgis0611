package com.example.ucmap.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ucmap.R;
import com.example.ucmap.adapter.MenuPageAdapter;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private NavigationTabBar leftMenu;
    private CheckBox toolMenu;
    private RadioGroup toolBar;
    private String selected = "";
    private ViewPager menuPanel;
    private AutoCompleteTextView searchTextView;
    private Button btnLocation;
    private Button btnCompass;
    private MenuPageAdapter menuAdapter;

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
        initLeftMenu();
        initTool();
        searchTextView = findViewById(R.id.atv_location_search);
        btnLocation = findViewById(R.id.btn_location);
        btnCompass = findViewById(R.id.btn_compass);
        btnLocation.setOnClickListener(this);
        btnCompass.setOnClickListener(this);
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
                        break;
                    case R.id.rb_tool_raging://测距
                        break;
                    case R.id.rb_tool_area://面积
                        break;
                    case R.id.rb_tool_query://搜索
                        break;
                    case R.id.rb_tool_take_pic://拍照
                        break;
                    case R.id.rb_tool_mark://标注
                        break;
                    case R.id.rb_tool_clear://清除
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
        ).title("图层\n管理")
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
                    menuPanel.setCurrentItem(index,false);
                }
                switch (selected) {
                    case "图层\n管理":
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
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_location://定位
                break;
            case R.id.btn_compass://指向
                break;
        }
    }
}
