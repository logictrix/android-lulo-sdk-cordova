package com.demo.app.UI.Document;

import android.graphics.Bitmap;

import com.demo.app.UI.base.BasePresenter;
import com.demo.app.UI.base.BaseView;
import com.demo.app.model.CloseResponse;


public interface DocContract {

    interface Presenter extends BasePresenter {

        void onFrontImageCaptured(Bitmap bitmap, String documentType, String uuidDevice);
        void onBackImageCaptured(Bitmap bitmap, String uuidDevice);
        void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber);
        void close(String uid);
    }

    interface View extends BaseView<Presenter> {

        void onWebServiceStart();
        void finishFlow(boolean result, CloseResponse response, Integer code);
        void onWebServiceStop();
        void onWebServiceResume();
        void continueFlow(boolean result, CloseResponse response, Integer code);
    }
}
