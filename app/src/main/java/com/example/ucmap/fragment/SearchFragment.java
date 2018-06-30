package com.example.ucmap.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ucmap.R;
import com.example.ucmap.adapter.SearchResultAdapter;

import java.util.ArrayList;
import java.util.Random;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private EditText etSearch;
    private Spinner spLayer;
    private Button btnSearch;
    private RecyclerView rvResult;
    ArrayList<String> results = new ArrayList<>();
    private SearchResultAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        etSearch = getView().findViewById(R.id.et_search_query);
        spLayer = getView().findViewById(R.id.sp_layer_search);
        btnSearch = getView().findViewById(R.id.btn_search_query);
        btnSearch.setOnClickListener(this);
        rvResult = getView().findViewById(R.id.rv_search_result);
        adapter = new SearchResultAdapter(results);
        rvResult.setAdapter(adapter);
rvResult.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_query:
                String input = etSearch.getText().toString();
                String layer = (String) spLayer.getSelectedItem();
                Toast.makeText(getActivity(), "输入:" + input + "\t图层:" + layer, Toast.LENGTH_SHORT).show();
                /*
                下边是假数据
                 */
                int i = new Random().nextInt(10);
                results.clear();
                for (int j = 0; j < i; j++) {
                    results.add(input + " 的第" + j + "个结果");
                }
                adapter.setResults(results);
                break;
        }
    }

}
