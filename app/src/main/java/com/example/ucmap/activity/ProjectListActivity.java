package com.example.ucmap.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.ucmap.R;
import com.example.ucmap.adapter.ProjectAadapter;
import com.example.ucmap.bean.Project;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectListActivity extends AppCompatActivity {
    private RecyclerView projectsList;
    private ArrayList<Project> projects = new ArrayList<>();
    private ProjectAadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        initData();
        initView();
    }

    private void initData() {
        try {
            Drawable drawable1 = Drawable.createFromStream(getAssets().open("project1.jpg"), null);
            Drawable drawable2 = Drawable.createFromStream(getAssets().open("project2.jpg"), null);
            projects.add(new Project("农产品产业园项目地图", drawable1));
            projects.add(new Project("驿城区地图", drawable2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        projectsList = findViewById(R.id.rv_project);
        adapter = new ProjectAadapter(projects);
        projectsList.setAdapter(adapter);
        projectsList.setLayoutManager(new GridLayoutManager(this, 3));
        adapter.setOnItemClickListener(new ProjectAadapter.OnItemClickListener() {
            @Override
            public void onClick(Project project, int position) {
                Toast.makeText(ProjectListActivity.this, "进入" + project.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProjectListActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }
}
