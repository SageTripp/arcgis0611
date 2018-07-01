package com.example.ucmap.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ucmap.R;
import com.example.ucmap.bean.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectAadapter extends RecyclerView.Adapter<ProjectAadapter.ViewHolder> {
    private ArrayList<Project> projects = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public ProjectAadapter(ArrayList<Project> projects) {
        this.projects.clear();
        this.projects.addAll(projects);
    }

    public void setProjects(List<Project> projects) {
        this.projects.clear();
        this.projects.addAll(projects);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Project project = projects.get(position);
        holder.setProject(project);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != itemClickListener) {
                    itemClickListener.onClick(project, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProject;
        private TextView tvNmae;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProject = itemView.findViewById(R.id.iv_project);
            tvNmae = itemView.findViewById(R.id.tv_project_name);
        }

        public void setProject(Project project) {
            ivProject.setImageDrawable(project.getImg());
            tvNmae.setText(project.getName());
        }
    }

    public interface OnItemClickListener {
        void onClick(Project project, int position);
    }
}
