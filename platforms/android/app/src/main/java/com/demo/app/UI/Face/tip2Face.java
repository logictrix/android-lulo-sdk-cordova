package com.demo.app.UI.Face;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.app.R;
import com.demo.app.ScanovateApp;
import com.demo.app.UI.base.BaseActivity;

public class tip2Face extends BaseActivity {
    public static final int REQUEST_CODE = 20;
    private static Context context;
    Button nextscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip2_face);
        context = this;
        initViews();
    }

    void initViews() {
        nextscreen = findViewById(R.id.btn_siguiente1);
        nextscreen.setOnClickListener(v -> {
            Intent intent = new Intent(context, FaceActivity.class);
            startActivityForResult(intent, FaceActivity.CAMERA_REQUEST_CODE);
            finish();
        });
    };
}