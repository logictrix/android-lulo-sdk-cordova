package com.demo.app.UI.Document;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.app.R;
import com.demo.app.ScanovateApp;
import com.demo.app.UI.base.BaseActivity;

public class tip1Doc extends BaseActivity {
    public static final int REQUEST_CODE = 20;
    private static Context context;
    Button nextScreen;

    public static void setContext(Context context) {
        tip1Doc.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip1_doc);
        context = this;
        initViews();

    }

    void initViews() {
        nextScreen = findViewById(R.id.btn_siguiente1);
        nextScreen.setOnClickListener(v -> {
            Intent intent = new Intent(context, tip2Doc.class);
            startActivityForResult(intent, tip2Doc.REQUEST_CODE);
            finish();
        });
    }
}