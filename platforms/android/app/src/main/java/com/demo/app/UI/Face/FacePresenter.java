package com.demo.app.UI.Face;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.demo.app.Utils;
import com.demo.app.model.CloseRegistroRequest;
import com.demo.app.model.CloseResponse;
import com.demo.app.model.FaceRequest;
import com.demo.app.model.VerificationIdentityRequest;
import com.demo.app.network.ApiHelper;
import com.demo.app.network.RetrofitClient;

import okhttp3.ResponseBody;

public class FacePresenter implements FaceContract.Presenter {

    private FaceContract.View view;
    Context context;


    FacePresenter(FaceContract.View view, Context context) {

        this.view = view;
        this.context = context;
    }

    @Override
    public void onViewCreated(Intent intent) {

    }

    @Override
    public void onLivenessSuccess(Bitmap bitmap, String latitude, String longitude, String uuidDevice) {
        view.onWebServiceStart();
        new RetrofitClient().postFace(
                new FaceRequest(
                        "1", Utils.BitMapToString(bitmap, 100), latitude, longitude)
                , new ApiHelper.FaceCompletionHandler() {
                    @Override
                    public void onSuccess(int statusCode, CloseResponse response) {
                        view.onWebServiceSuccess();
                        Log.d("ScanovateSdk", "face: " + statusCode);
                        if (statusCode == 200) {
                            String uid = response.getUid();
                            //view.continueFlowRegistro(uid);
                            view.continueFlow(true,response,statusCode);
                        } else if (statusCode == 201) {
                            view.finishFlow(true, response, statusCode);
                            //view.finishFlow();
                        }
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
                        Log.d("ScanovateSdk", "face: " + statusCode + " " + error);
                        view.onWebServiceFailed(error, statusCode);
                    }
                },context);
    }

    @Override
    public void verificationCustomer(String docType, String numberId, Bitmap face, Bitmap fingerPrint,String latitude, String longitude) {
        view.onWebServiceStart();
        new RetrofitClient().customerVerification(
                new VerificationIdentityRequest(
                        docType, numberId, Utils.BitMapToString(face, 100),"",latitude, longitude)
                , new ApiHelper.VerificationCustomerHandler() {
                    @Override
                    public void onSuccess(int statusCode, CloseResponse response) {
                        view.onWebServiceSuccess();
                        Log.d("ScanovateSdk", "verification: " + statusCode);
                        view.continueFlow(true,response,statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, ResponseBody response) {

                    }

                    @Override
                    public void onConnectionFailed() {

                    }

                    @Override
                    public void onFailure(int statusCode, String error) {
                        view.onWebServiceSuccess();
                        Log.d("ScanovateSdk", "verification: " + statusCode);
                        view.finishFlow(false, null,statusCode);
                    }
                }, context);
    }

    @Override
    public void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber, String uuidDevice) {

        new RetrofitClient().CloseRegistro(
                new CloseRegistroRequest(uid,IdentificationNumber,IndividualCodeFinger),
                new ApiHelper.CloseCompletionHandler() {
                    @Override
                    public void onSuccess(int statusCode, CloseResponse response) {
                        Log.d("ScanovateSdk", "close: onSuccess");
                        view.finishFlow(true, response, statusCode);
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
                        Log.d("ScanovateSdk", "close: onFailure: " + error);
                        view.finishFlow(false, null, statusCode);
                    }
                }, context
        );

    }

    @Override
    public void onLivenessFailed() {

    }
}
