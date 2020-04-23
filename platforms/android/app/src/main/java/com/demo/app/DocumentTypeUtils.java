package com.demo.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.demo.app.model.GetConfigResponse;
import com.google.gson.Gson;



public class DocumentTypeUtils {


    public static boolean getLevelerUIValue(Context activity) {
        SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        return data.getBoolean("showLevelerUI", true);
    }

    public static String getDocumentType(Context activity) {
        String value = "1";
        SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("documentType", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }

        return value;
    }

    public static Integer getProductId(Context activity) {
        try {
            Integer value = 1;
            SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
            if (data != null) {
                value = Integer.parseInt(data.getString("productId", ""));
            }

            return value;
        } catch (Exception e) {
            return 1;
        }

    }

    public static Integer getTryLiveness(Context activity) {
        try {
            Integer value = 1;
            SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
            if (data != null) {
                value = Integer.parseInt(data.getString("tryLiveness", ""));
            }

            return value;
        } catch (Exception e) {
            return 1;
        }

    }

    public static String getUrlServicesLiveness(Context activity) {
        String value = "1";
        SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("urlServicesLiveness", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }
        return value;
    }

    public static String getTokenKyc(Context activity) {
        String value = "1";
        SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("tokenKyc", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }
        return value;
    }

    public static String getUuiddevice(Context activity) {
        String value = "1";
        SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("uuidDevice", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }
        return value;
    }

    public static String getNumberId(Context activity) {
        String value = "";
        SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("numberId", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "";
        }
        return value;
    }

    public static boolean getVerification(Context activity) {
        SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        return data.getBoolean("verification", true);
    }

    public static boolean getFrontDoc(Context activity) {
        SharedPreferences data = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        return data.getBoolean("frontDoc", true);
    }

    public static void storeDocumentType(Activity activity, String documentType) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("documentType", documentType);
        editor.commit();
    }

    public static void storeProductId(Activity activity, Integer ProductId) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("productId", ProductId.toString());
        editor.commit();
    }

    public static void storeLevelerUI(Activity activity, boolean showLevelerUI) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("showLevelerUI", showLevelerUI);
        editor.commit();
    }

    public static void storeUuidDevice(Activity activity, String uuidDevice) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("uuidDevice", uuidDevice);
        editor.commit();
    }


    public static void storeGetConfig(Activity activity, GetConfigResponse getConfigResponse) {

        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String Objjson = gson.toJson(getConfigResponse);
        editor.putString("ConfigData", Objjson);
        editor.commit();
    }

    public static void storeUrlLiveness(Activity activity, String urlservicesLiveness) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("urlServicesLiveness", urlservicesLiveness);
        editor.commit();
    }

    public static void storeTryLiveness(Activity activity, Integer TryLiveness) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("tryLiveness", TryLiveness.toString());
        editor.commit();
    }

    public static void storeTokenKyc(Activity activity, String tokenKyc) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("tokenKyc", tokenKyc);
        editor.commit();
    }

    public static void storeNumberId(Activity activity, String numberId) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("numberId", numberId);
        editor.commit();
    }

    public static void storeVerification(Activity activity, boolean verification) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("verification", verification);
        editor.commit();
    }

    public static void storeFrontDoc(Activity activity, boolean frontDoc) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("frontDoc", frontDoc);
        editor.commit();
    }

}
