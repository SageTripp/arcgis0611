package com.example.ucmap.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ucmap.R;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class TestActivity extends AppCompatActivity {
    private NavigationTabBar leftMenu;
    private CheckBox toolMenu;
    private RadioGroup toolBar;
    private String selected = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initLeftMenu();
        toolMenu = findViewById(R.id.cb_tool);
        toolBar = findViewById(R.id.rg_tool_bar);
        toolMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toolBar.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
    }


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
                } else {
                    selected = model.getTitle();
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
            }
        });
    }
}
