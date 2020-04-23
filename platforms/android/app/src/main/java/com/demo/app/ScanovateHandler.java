package com.demo.app;


import com.demo.app.model.CloseResponse;

public interface ScanovateHandler {

    void onSuccess(CloseResponse response, int code, String uuidDevice);
    void onFailure(CloseResponse response);
}
