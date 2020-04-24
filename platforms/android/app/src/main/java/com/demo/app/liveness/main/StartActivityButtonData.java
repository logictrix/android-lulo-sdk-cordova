package com.demo.app.liveness.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class StartActivityButtonData {
    private String title;
    private boolean isSelfie;
    private IntentProvider extraValuesIntentProvider;
    private Class<? extends Activity> destinationActivity;
    private String[] permissions;
    private int activityForResultRequestCode;
    private OnActivityResultListener onActivityResultListener;
    private View.OnClickListener onClickListener;
    private boolean notActivityForResult;
    private int buttonDecorationDrawable;

    StartActivityButtonData(Builder builder) {
        this.notActivityForResult = builder.notActivityForResult;
        this.extraValuesIntentProvider = builder.extraValuesIntentProvider;
        this.title = builder.title;
        this.isSelfie = builder.isSelfie;
        this.destinationActivity = builder.destinationActivity;
        this.permissions = builder.permissions;
        this.activityForResultRequestCode = builder.activityForResultRequestCode;
        this.onActivityResultListener = builder.onActivityResultListener;
        this.onClickListener = builder.onClickListener;
        this.buttonDecorationDrawable = builder.buttonDecorationDrawable;
    }
//
    //Copy Constructor
    public StartActivityButtonData(StartActivityButtonData startActivityButtonData){
        this.title = startActivityButtonData.title;
        this.extraValuesIntentProvider = startActivityButtonData.extraValuesIntentProvider;
        this.isSelfie = startActivityButtonData.isSelfie;
        this.destinationActivity = startActivityButtonData.destinationActivity;
        this.permissions = startActivityButtonData.permissions;
        this.activityForResultRequestCode = startActivityButtonData.activityForResultRequestCode;
        this.onActivityResultListener = startActivityButtonData.onActivityResultListener;
        this.onClickListener = startActivityButtonData.onClickListener;
    }
    public String getTitle() {
        return title;
    }

    public boolean isSelfie() {
        return isSelfie;
    }

    public Class<? extends Activity> getDestinationActivity() {
        return destinationActivity;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public int getActivityForResultRequestCode() {
        return activityForResultRequestCode;
    }

    public OnActivityResultListener getOnActivityResultListener() {
        return onActivityResultListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public Intent getExtraValuesIntent() {
        return extraValuesIntentProvider.getIntent();
    }

    public boolean isNotActivityForResult() {
        return notActivityForResult;
    }

    public int getButtonDecorationDrawableRes() {
        return buttonDecorationDrawable;
    }

    @SuppressWarnings("WeakerAccess")
    public static class Builder {
        private String title;
        private boolean isSelfie;
        private Class<? extends Activity> destinationActivity;
        private String[] permissions = new String[]{Manifest.permission.CAMERA};
        private int activityForResultRequestCode;
        private OnActivityResultListener onActivityResultListener;
        private IntentProvider extraValuesIntentProvider;
        private View.OnClickListener onClickListener;
        private boolean notActivityForResult;
        private int buttonDecorationDrawable;

        public Builder(){
            onActivityResultListener = new OnActivityResultListener() {
                @Override
                public void onActivityResult(int resultCode, Intent data) {
                }
            };

            extraValuesIntentProvider = new IntentProvider() {
                @Override
                public Intent getIntent() {
                    return new Intent();
                }
            };
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder extraValuesIntentProvider(IntentProvider extraValuesIntentProvider) {
            this.extraValuesIntentProvider = extraValuesIntentProvider;
            return this;
        }

        public Builder isSelfie(boolean isSelfie) {
            this.isSelfie = isSelfie;
            return this;
        }

        public Builder destinationActivity(Class<? extends Activity> destinationActivity) {
            this.destinationActivity = destinationActivity;
            return this;
        }

        public Builder buttonDecorationDrawable(int drawableRes) {
            this.buttonDecorationDrawable = drawableRes;
            return this;
        }

        public Builder permissions(String[] permissions) {
            this.permissions = permissions;
            return this;
        }

        public Builder onClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder activityForResultRequestCode(int activityForResultRequestCode) {
            this.activityForResultRequestCode = activityForResultRequestCode;
            return this;
        }

        public Builder onActivityResultListener(OnActivityResultListener onActivityResultListener) {
            this.onActivityResultListener = onActivityResultListener;
            return this;
        }

        public StartActivityButtonData build() {
            return new StartActivityButtonData(this);
        }

        public Builder notActivityForResult(boolean val) {
            notActivityForResult = val;
            return this;
        }
    }
}