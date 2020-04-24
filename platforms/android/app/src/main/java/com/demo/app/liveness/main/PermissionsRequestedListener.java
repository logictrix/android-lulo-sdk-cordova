package com.demo.app.liveness.main;

import android.view.View;

public interface PermissionsRequestedListener {
    void onPermissionsRequested(View view, StartActivityButtonData clickedBtnListener);
}