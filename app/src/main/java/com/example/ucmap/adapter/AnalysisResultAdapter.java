package com.example.ucmap.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ucmap.R;
import com.example.ucmap.bean.LandType;

import java.util.ArrayList;

public class AnalysisResultAdapter extends RecyclerView.Adapter<AnalysisResultAdapter.ViewHolder> {

    private ArrayList<LandType> lands = new ArrayList<>();

    public AnalysisResultAdapter(ArrayList<LandType> lands) {
        this.lands.clear();
        this.lands.addAll(lands);
    }

    public void setLands(ArrayList<LandType> lands) {
        this.lands.clear();
        this.lands.addAll(lands);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analysis_result, parent, false);
        if (viewType == 0) {
            inflate.setBackgroundColor(Color.WHITE);
        } else if (viewType == 1) {
            inflate.setBackgroundColor(Color.TRANSPARENT);
        }
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setValue(lands.get(position));
    }

    @Override
    public int getItemCount() {
        return lands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvType;
        private TextView tvArea;
        private TextView tvNum;

        public ViewHolder(View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_kind_analysis_result);
            tvArea = itemView.findViewById(R.id.tv_area_analysis_result);
            tvNum = itemView.findViewById(R.id.tv_num_analysis_result);
        }

        public void setValue(LandType land) {
            tvType.setText(land.getLandTypeName());
            tvArea.setText(String.valueOf(land.getTotalAreas()));
            tvNum.setText(String.valueOf(land.getTotalNums()));
        }
    }
}
