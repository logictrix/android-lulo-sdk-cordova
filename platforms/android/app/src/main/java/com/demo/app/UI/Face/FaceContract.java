package com.demo.app.UI.Face;

import android.graphics.Bitmap;

import com.demo.app.UI.base.BasePresenter;
import com.demo.app.UI.base.BaseView;
import com.demo.app.model.CloseResponse;


public interface FaceContract {

    interface Presenter extends BasePresenter {

        void onLivenessSuccess(Bitmap bitmap, String latitude, String longitude, String uuidDevice);
        void verificationCustomer(String docType, String numberId, Bitmap face, Bitmap fingerPrint, String latitude, String longitude);
        void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber, String uuidDevice);
        void onLivenessFailed();

    }

    interface View extends BaseView<Presenter> {

        void onWebServiceSuccess();
        void onWebServiceFailed(String error, int statuscode);
        void onWebServiceStart();
        void finishFlow(boolean result, CloseResponse response, Integer code);
        void continueFlow(boolean result, CloseResponse response, int code);


    }
}
