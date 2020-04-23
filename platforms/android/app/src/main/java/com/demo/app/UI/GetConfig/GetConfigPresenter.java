package com.demo.app.UI.GetConfig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.demo.app.ScanovateSdk;
import com.demo.app.model.CloseResponse;
import com.demo.app.model.GetConfigRequest;
import com.demo.app.model.GetConfigResponse;
import com.demo.app.network.ApiHelper;
import com.demo.app.network.RetrofitClient;

import okhttp3.ResponseBody;


public class GetConfigPresenter extends Activity implements GetConfigContract.Presenter {

    private GetConfigContract.View view;
    private Context context;

    GetConfigPresenter(GetConfigContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onViewCreated(Intent intent) {
    }

    @Override
    public void getConfig(String message) {
        view.onWebServiceStart();
        new RetrofitClient().GetConfig(
                new GetConfigRequest(message),
                new ApiHelper.GetConfigHandler() {
                    @Override
                    public void onSuccess(int statusCode, GetConfigResponse response) {
                        view.onWebServiceSuccess();
                        Log.d("ScanovateSdk", "projectName and apiKey: onSuccess");
                        view.continueFlow(response);
                    }

                    @Override
                    public void onSuccess(int statusCode, ResponseBody response) {
                    }

                    @Override
                    public void onConnectionFailed() {
                        view.onNoConnection();
                    }

                    @Override
                    public void onFailure(int statusCode, String error) {
                        Log.d("ScanovateSdk", "projectName and/or apiKey: " + error);
                        view.onWebServiceFailed(error);
                        view.finishFlow(statusCode, error);
                    }
                }, context
        );
    }


    @Override
    public void finishFlow(boolean result, CloseResponse response, Integer code, String uuidDevice) {
        if (result) {
            ScanovateSdk.getHandler().onSuccess(response, code, uuidDevice);
        } else {
            ScanovateSdk.getHandler().onFailure(response);
        }
        view.finishFlow(code,"");
    }
}
