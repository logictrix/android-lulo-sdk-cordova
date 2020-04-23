package com.demo.app.network;

import android.content.Context;

import com.demo.app.model.CloseRegistroRequest;
import com.demo.app.model.CloseRequest;
import com.demo.app.model.CloseResponse;
import com.demo.app.model.DocRequest;
import com.demo.app.model.DocRequestFront;
import com.demo.app.model.FaceRequest;
import com.demo.app.model.GetConfigRequest;
import com.demo.app.model.GetConfigResponse;
import com.demo.app.model.LogRequest;
import com.demo.app.model.UserAuthenticationRequest;
import com.demo.app.model.UserAuthenticationResponse;
import com.demo.app.model.VerificationIdentityRequest;

import okhttp3.ResponseBody;

public interface ApiHelper {

    interface CompletionHandler {

        void onSuccess(int statusCode, ResponseBody response);
        void onConnectionFailed();
        void onFailure(int statusCode, String error);
    }


    interface FaceCompletionHandler extends CompletionHandler {

        void onSuccess(int statusCode, CloseResponse response);
    }

    interface CloseCompletionHandler extends CompletionHandler {

        void onSuccess(int statusCode, CloseResponse response);
    }

    interface RegisterLogHandler extends CompletionHandler {
        void onSuccess(int IdLog);
    }

    public interface ValidateTransactionHandler extends ApiHelper.CompletionHandler {
        void onSuccess(String var1);
    }

    interface UserAuthenticationHandler extends CompletionHandler {
        void onSuccess(int statusCode, UserAuthenticationResponse response);
    }

    interface GetConfigHandler extends CompletionHandler {
        void onSuccess(int statusCode, GetConfigResponse response);
    }

    interface VerificationCustomerHandler extends CompletionHandler {
        void onSuccess(int statusCode, CloseResponse response);
    }


    void postFace(FaceRequest request, FaceCompletionHandler handler, Context mContext);
    void postDocFront(DocRequestFront request, CompletionHandler handler, String documentType, Context mContext);
    void postDocBack(DocRequest request, CloseCompletionHandler handler, Context mContext);
    void close(CloseRequest request, CloseCompletionHandler handler, Context mContext);
    void RegisterLog(LogRequest request, RegisterLogHandler handler);
    void CloseRegistro(CloseRegistroRequest registro, CloseCompletionHandler handler, Context mContext);
    void UserAuthentication(UserAuthenticationRequest userAuthentication, UserAuthenticationHandler handler, Context mContext);
    void GetConfig(GetConfigRequest getConfigRequest, GetConfigHandler handler, Context mContext);
    void validateTransaction(String project, String txId, ValidateTransactionHandler handler, Context mContext);
    void customerVerification(VerificationIdentityRequest request, VerificationCustomerHandler handler, Context context);

}
