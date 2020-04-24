package com.demo.app.kyc;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acuant.mobilesdk.CardType;
import com.demo.app.R;
import com.demo.app.kyc.util.TempImageStore;


public class DataConfirmationActivity extends Activity {

    Bitmap image;
    ImageView cropImageViewer;
    TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.data_confirmation);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        cropImageViewer = (ImageView) findViewById(R.id.cropImage);
        messageView = (TextView)findViewById(R.id.messageTextView);
        image = TempImageStore.getBitmapImage();
        if(TempImageStore.isCroppingPassed()) {
            cropImageViewer.setImageBitmap(image);

            ImageView titleImg = (ImageView)  findViewById(R.id.titleImg);
            titleImg.setVisibility(View.GONE);

        }else{
            Button confirmButton = (Button) findViewById(R.id.buttonConfirm);
            confirmButton.setVisibility(View.GONE);
            cropImageViewer.getLayoutParams().height = height*5/10;
            cropImageViewer.getLayoutParams().width = width*8/10;
            cropImageViewer.setImageResource(R.drawable.frontid3x);

            ImageView titleImg = (ImageView)  findViewById(R.id.titleImg);
            titleImg.setVisibility(View.VISIBLE);
            titleImg.getLayoutParams().width = width*8/10;
            titleImg.setImageResource(R.drawable.backid3x);

        }
        messageView.setText(getMessage());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        image = null;
        cropImageViewer = null;
        messageView = null;
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();

    }

    public void retryButtonPressed(View v) {
        if(TempImageStore.getImageConfirmationListener()!=null) {
            TempImageStore.getImageConfirmationListener().retry();
        }
        finish();
    }

    public void confirmButtonPressed(View v) {
        if(TempImageStore.getImageConfirmationListener()!=null) {
            TempImageStore.getImageConfirmationListener().confimed();
        }
        finish();
    }

    public String getMessage(){
        String retString = "¿Se puede leer con claridad la información de tu cédula?";
        if(TempImageStore.isCroppingPassed()){
            if(TempImageStore.getCardType()== CardType.DRIVERS_LICENSE){
                retString = "¿Se puede leer con claridad la información de tu cédula?";
            }else if(TempImageStore.getCardType()== CardType.PASSPORT){
                retString = "¿Se puede leer con claridad la información de tu cédula?";
            }else if(TempImageStore.getCardType()== CardType.MEDICAL_INSURANCE){
                retString = "¿Se puede leer con claridad la información de tu cédula?";
            }

        }else{
            if(TempImageStore.getCardType()== CardType.DRIVERS_LICENSE){
                retString = "No hemos podido detectar un documento, por favor reintenta.";
            }else if(TempImageStore.getCardType()== CardType.PASSPORT){
                retString = "No hemos podido detectar un documento, por favor reintenta.";
            }else if(TempImageStore.getCardType()== CardType.MEDICAL_INSURANCE){
                retString = "No hemos podido detectar un documento, por favor reintenta.";
            }
        }
        return retString;
    }

    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
