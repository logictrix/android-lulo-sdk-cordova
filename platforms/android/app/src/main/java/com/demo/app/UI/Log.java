package com.demo.app.UI;


import android.os.AsyncTask;

import com.demo.app.model.LogRequest;
import com.demo.app.network.ApiHelper;
import com.demo.app.network.RetrofitClient;

import okhttp3.ResponseBody;

public class Log extends AsyncTask<String, Integer, Integer> {

    protected Integer doInBackground(String... parameters) {

        try {
            String myVersion = android.os.Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
            int sdkVersion = android.os.Build.VERSION.SDK_INT;

            RetrofitClient retrofitClient = new RetrofitClient();
            retrofitClient.RegisterLog(new LogRequest(parameters[0], parameters[1], parameters[2], android.os.Build.MODEL, Integer.toString(sdkVersion) + "-" + myVersion, RetrofitClient.getNameProject_Sdk(), parameters[3],parameters[4], parameters[5]), new ApiHelper.RegisterLogHandler() {
                @Override
                public void onSuccess(int IdLog) {

                }

                @Override
                public void onSuccess(int statusCode, ResponseBody response) {

                }

                @Override
                public void onConnectionFailed() {

                }

                @Override
                public void onFailure(int statusCode, String error) {

                }
            });
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }
}