package com.example.ucmap.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.ucmap.R;
import com.example.ucmap.adapter.LayerAdapter;
import com.example.ucmap.bean.LayerBean;

import java.util.ArrayList;

public class LayerFragment extends Fragment {
    private ArrayList<LayerBean> groupList = new ArrayList<>();
    private ArrayList<ArrayList<LayerBean>> childList = new ArrayList<>();

    private ExpandableListView elvLayer;
    private LayerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);

    }

    private void initView(View view) {
        elvLayer = view.findViewById(R.id.elv_layer);
        adapter = new LayerAdapter(getContext(), groupList, childList);
        adapter.setLoadingListener((LayerAdapter.LoadingListener) getActivity());
        elvLayer.setAdapter(adapter);
        elvLayer.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = elvLayer.getExpandableListAdapter().getGroupCount();
                for (int i = 0; i < count; i++) {
                    if (i != groupPosition) {
                        elvLayer.collapseGroup(i);
                    }
                }
            }
        });
    }

    private void initData() {
        /*
        分组
         */
        groupList.add(new LayerBean("基础地理"));
        groupList.add(new LayerBean("地政基础"));
        groupList.add(new LayerBean("土地调查"));
        groupList.add(new LayerBean("土地权属"));
        groupList.add(new LayerBean("项目范围"));
        /*
        基础地理
         */
        ArrayList<LayerBean> baseGeoList = new ArrayList<>();
        baseGeoList.add(new LayerBean("县级行政区"));
        baseGeoList.add(new LayerBean("乡级行政区"));
        baseGeoList.add(new LayerBean("村级行政区"));
        baseGeoList.add(new LayerBean("遥感影像数据", true));
        childList.add(baseGeoList);
        /*
        地政基础
         */
        ArrayList<LayerBean> landFoundationList = new ArrayList<>();
        landFoundationList.add(new LayerBean("土地利用规划"));
        landFoundationList.add(new LayerBean("土地利用现状"));
        landFoundationList.add(new LayerBean("永久基本农田"));
        landFoundationList.add(new LayerBean("设施农用地"));
        childList.add(landFoundationList);
        /*
        土地调查
         */
        ArrayList<LayerBean> landSurveyList = new ArrayList<>();
        landSurveyList.add(new LayerBean("土地报批"));
        landSurveyList.add(new LayerBean("土地供地"));
        landSurveyList.add(new LayerBean("工业仓储用地"));
        landSurveyList.add(new LayerBean("农村建设用地"));
        landSurveyList.add(new LayerBean("农村流转土地"));
        landSurveyList.add(new LayerBean("批而未建土地"));
        landSurveyList.add(new LayerBean("闲置地"));
        childList.add(landSurveyList);
        /*
        土地权属
         */
        ArrayList<LayerBean> landOwnershipList = new ArrayList<>();
        landOwnershipList.add(new LayerBean("土地所有权"));
        landOwnershipList.add(new LayerBean("宗地"));
        childList.add(landOwnershipList);
        /*
        项目范围
         */
        ArrayList<LayerBean> projectScopList = new ArrayList<>();
        projectScopList.add(new LayerBean("项目范围"));
        projectScopList.add(new LayerBean("三区分布"));
        childList.add(projectScopList);

    }
}
