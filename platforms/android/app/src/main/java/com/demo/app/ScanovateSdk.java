package com.demo.app;

import android.app.Activity;
import android.content.Intent;

import com.demo.app.UI.GetConfig.GetConfigActivity;
import com.demo.app.UI.Log;
import com.demo.app.network.RetrofitClient;

import java.util.UUID;


public class ScanovateSdk {

    private static ScanovateHandler sHandler;

    public static void start(final Activity activity,
                             String documentType,
                             Integer productId,
                             boolean showLevelerUI,
                             String projectName_Sdk,
                             String apiKey_Sdk,
                             String Url_Sdk,
                             String numberId,
                             boolean verification,
                             ScanovateHandler handler) {

        String uuidDevice = UUID.randomUUID().toString();
        Intent myIntent = new Intent(activity, GetConfigActivity.class);
        DocumentTypeUtils.storeLevelerUI(activity, showLevelerUI);
        DocumentTypeUtils.storeProductId(activity, productId);
        DocumentTypeUtils.storeUuidDevice(activity, uuidDevice);
        DocumentTypeUtils.storeDocumentType(activity, documentType);
        GetConfigActivity.setContext(activity);
        RetrofitClient.setContext(activity);
        RetrofitClient.storeConfigSdk(activity, Url_Sdk, projectName_Sdk, apiKey_Sdk);
        DocumentTypeUtils.storeNumberId(activity,numberId);
        DocumentTypeUtils.storeVerification(activity,verification);
        sHandler = handler;
        new Log().execute("Info", "Start_SKD", "SKD initialization", "", uuidDevice, "");
        activity.startActivity(myIntent);
    }

    public static ScanovateHandler getHandler() {
        return sHandler;
    }
}
