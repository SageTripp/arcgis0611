package com.example.ucmap.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ucmap.R;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter {
    private ArrayList<String> results = new ArrayList<>();

    public SearchResultAdapter(ArrayList<String> results) {
        this.results.addAll(results);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView tvResult = (TextView) holder.itemView;
        tvResult.setText(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(ArrayList<String> results) {
        this.results.clear();
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
