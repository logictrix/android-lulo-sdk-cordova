package com.demo.app.UI.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
    void onNoConnection();
}
