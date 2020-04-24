package com.demo.app.liveness.dialogs.resultsdialogs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.app.R;

import java.util.List;



public class SNScanResultsAdapter extends RecyclerView.Adapter<SNScanResultVH> {
    List<SNDisplayableResult> dataSet;

    @Override
    public SNScanResultVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = getLayoutInflater(parent).inflate(R.layout.sn_scanned_data_results_vh, parent, false);
        return new SNScanResultVH(view);
    }

    public SNScanResultsAdapter(List<SNDisplayableResult> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public void onBindViewHolder(@NonNull SNScanResultVH holder, int position) {
        holder.setUIDataOnView(dataSet.get(position), this, position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private LayoutInflater getLayoutInflater(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext());
    }
}