package com.demo.app.UI.GetConfig;


import com.demo.app.UI.base.BasePresenter;
import com.demo.app.UI.base.BaseView;
import com.demo.app.model.CloseResponse;
import com.demo.app.model.GetConfigResponse;

public interface GetConfigContract {

    interface Presenter extends BasePresenter {

        void getConfig(String message);
        void finishFlow(boolean result, CloseResponse response, Integer code, String uuidDevice);

    }

    interface View extends BaseView<Presenter>
    {

        void onWebServiceSuccess();
        void onWebServiceFailed(String error);
        void onWebServiceStart();
        void finishFlow(int statuscode, String error);
        void continueFlow(GetConfigResponse response);

    }
}

