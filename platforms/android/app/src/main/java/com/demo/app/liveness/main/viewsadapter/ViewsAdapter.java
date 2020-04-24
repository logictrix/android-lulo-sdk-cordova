package com.demo.app.liveness.main.viewsadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.app.R;
import com.demo.app.liveness.main.PermissionsRequestedListener;
import com.demo.app.liveness.main.StartActivityButtonData;

import java.util.List;



public class ViewsAdapter extends RecyclerView.Adapter<DemosGeneratorVH> {

    private List<StartActivityButtonData> dataSet;
    private PermissionsRequestedListener permissionsRequestedListener;

    @Override
    public DemosGeneratorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = getLayoutInflater(parent).inflate(R.layout.vh_demos_generator, parent, false);
        return new DemosGeneratorVH(view, permissionsRequestedListener);
    }

    public ViewsAdapter(List<StartActivityButtonData> dataSet, PermissionsRequestedListener permissionsRequestedListener) {
        this.dataSet = dataSet;
        this.permissionsRequestedListener = permissionsRequestedListener;
    }

    @Override
    public void onBindViewHolder(@NonNull DemosGeneratorVH holder, int position) {
        holder.setUIDataOnView(dataSet.get(position));
    }

    @Override
    public int getItemCount() {


        return dataSet.size();
    }

    public void setDataSet(List<StartActivityButtonData> dataSet) {
        this.dataSet = dataSet;
    }

    private LayoutInflater getLayoutInflater(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext());
    }
}