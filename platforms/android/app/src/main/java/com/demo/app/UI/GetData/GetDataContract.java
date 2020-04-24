package com.demo.app.UI.GetData;


import com.demo.app.UI.base.BasePresenter;
import com.demo.app.UI.base.BaseView;
import com.demo.app.model.CloseResponse;

public interface GetDataContract {

    interface Presenter extends BasePresenter {


        void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber, String uuidDevice);
        void finishFlow(boolean result, CloseResponse response, Integer code, String uuidDevice);
        //void validateRequest(boolean result, CloseResponse response, int statusCode);

    }

    interface View extends BaseView<Presenter> {

        void onWebServiceSuccess();
        void onWebServiceFailed(String error);
        void onWebServiceStart();
        void finishFlow();
        void validateRequest(boolean result, CloseResponse response, int statusCode);
        void continueFlow(String uid);
        void continueFlowRegistro(String uid);

    }
}

