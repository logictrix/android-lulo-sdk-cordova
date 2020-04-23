package com.demo.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class ScanovateApp extends Application {

    private static final String TAG = "ScanovateApp";
    private static Context appContext;
    public static Context getAppContext() {

        return appContext;
    }

    public static void SetContext(Context context) {
        appContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException (Thread thread, Throwable e)
            {
                Log.e(TAG, e.toString());
            }
        });
    }
}



