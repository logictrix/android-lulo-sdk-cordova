package com.demo.app.kyc;

/**
 * Created by leoOvalle on 12/15/19.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acuant.mobilesdk.CardType;
import com.demo.app.DocumentTypeUtils;
import com.demo.app.R;
import com.demo.app.kyc.util.TempImageStore;


public class ImageConformationActivity extends Activity {
    Bitmap image;
    ImageView cropImageViewer;
    //TextView messageView;
    TextView messageUnderPicture;
    TextView subtittle;
    TextView tv_cardtype;
    TextView text3;
    Button btn_takeAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_confirmation);
        initViews();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        image = TempImageStore.getBitmapImage();
        if(TempImageStore.isCroppingPassed()) {
            cropImageViewer.setImageBitmap(image);
            tv_cardtype.setVisibility(View.VISIBLE);
            text3.setVisibility(View.VISIBLE);
            //messageView.setVisibility(View.VISIBLE);
            messageUnderPicture.setVisibility(View.INVISIBLE);
            subtittle.setVisibility(View.INVISIBLE);
            ImageView titleImg = (ImageView)  findViewById(R.id.titleImg);
            titleImg.setVisibility(View.GONE);

        }else{
            tv_cardtype.setVisibility(View.INVISIBLE);
            text3.setVisibility(View.INVISIBLE);
            //messageView.setVisibility(View.INVISIBLE);
            messageUnderPicture.setVisibility(View.VISIBLE);
            subtittle.setVisibility(View.VISIBLE);
            btn_takeAgain.setBackgroundColor(Color.parseColor("#92C83D"));
            btn_takeAgain.setTextColor(Color.parseColor("#FFFFFF"));
            btn_takeAgain.setTextSize(16);
            btn_takeAgain.setText("Reintentar");
            Button confirmButton = (Button) findViewById(R.id.buttonConfirm);
            confirmButton.setVisibility(View.GONE);
            cropImageViewer.setVisibility(View.INVISIBLE);
            //cropImageViewer.getLayoutParams().height = height*5/10;
            //cropImageViewer.getLayoutParams().width = width*8/10;
            ImageView titleImg = (ImageView)  findViewById(R.id.titleImg);
            titleImg.setVisibility(View.VISIBLE);
            titleImg.getLayoutParams().width = width*8/10;
            if (DocumentTypeUtils.getFrontDoc(this)){
                //cropImageViewer.setImageResource(R.drawable.front_doc_failed);
                titleImg.setImageResource(R.drawable.bad_check);
            }else{
                //cropImageViewer.setImageResource(R.drawable.back_doc_failed);
                titleImg.setImageResource(R.drawable.bad_check);
            }

        }
        //messageView.setText(getMessage());
    }

    private void initViews(){
        messageUnderPicture = findViewById(R.id.message_under_picture);
        cropImageViewer = (ImageView)findViewById(R.id.cropImage);
        //messageView = (TextView)findViewById(R.id.messageTextView);
        subtittle = findViewById(R.id.subtittle);
        btn_takeAgain = findViewById(R.id.buttonRetry);
        tv_cardtype = findViewById(R.id.tv_cardtype);
        text3 = findViewById(R.id.textView3);
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
        //messageView = null;
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();

    }

    public void retryButtonPressed(View v) {
        if(TempImageStore.getImageConfirmationListener()!=null) {
            TempImageStore.getImageConfirmationListener().retry();
        }
        messageUnderPicture.setVisibility(View.INVISIBLE);
        subtittle.setVisibility(View.INVISIBLE);
        finish();
    }

    public void confirmButtonPressed(View v) {
        if(TempImageStore.getImageConfirmationListener()!=null) {
            TempImageStore.getImageConfirmationListener().confimed();
        }
        messageUnderPicture.setVisibility(View.INVISIBLE);
        subtittle.setVisibility(View.INVISIBLE);
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
