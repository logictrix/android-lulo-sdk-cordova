package com.demo.app.liveness.dialogs.resultsdialogs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


class SNScanResultVH extends RecyclerView.ViewHolder {

    private static final String TAG = SNScanResultVH.class.getSimpleName();
    private TextView dataTitleTV, dataValueTV;
    private ImageView dataImageIV;

    SNScanResultVH(View itemView) {
        super(itemView);
        //dataTitleTV = itemView.findViewById(R.id.snScanResultDataTitleTV);
        //dataValueTV = itemView.findViewById(R.id.snScanResultDataValueTV);
        //dataImageIV = itemView.findViewById(R.id.snScanResultDataImageIV);
    }

    void setUIDataOnView(final SNDisplayableResult data, SNScanResultsAdapter adapter, int position) {
        dataTitleTV.setText(data.getTitle());
        dataValueTV.setText(data.getValue());
        dataImageIV.setImageBitmap(data.getImage());
    }
}