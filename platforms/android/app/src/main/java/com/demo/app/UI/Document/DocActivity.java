package com.demo.app.UI.Document;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acuant.mobilesdk.AcuantAndroidMobileSDKController;
import com.acuant.mobilesdk.AcuantErrorListener;
import com.acuant.mobilesdk.Card;
import com.acuant.mobilesdk.CardCroppingListener;
import com.acuant.mobilesdk.CardType;
import com.acuant.mobilesdk.DriversLicenseCard;
import com.acuant.mobilesdk.ErrorType;
import com.acuant.mobilesdk.FacialData;
import com.acuant.mobilesdk.FacialRecognitionListener;
import com.acuant.mobilesdk.LicenseDetails;
import com.acuant.mobilesdk.Permission;
import com.acuant.mobilesdk.ProcessImageRequestOptions;
import com.acuant.mobilesdk.Region;
import com.acuant.mobilesdk.WebServiceListener;
import com.acuant.mobilesdk.util.Utils;
import com.demo.app.DocumentTypeUtils;
import com.demo.app.R;
import com.demo.app.ScanovateApp;
import com.demo.app.ScanovateSdk;
import com.demo.app.UI.Log;
import com.demo.app.kyc.AcuantUtil;
import com.demo.app.kyc.ImageConformationActivity;
import com.demo.app.kyc.model.MainActivityModel;
import com.demo.app.kyc.util.ConfirmationListener;
import com.demo.app.kyc.util.DataContext;
import com.demo.app.kyc.util.TempImageStore;
import com.demo.app.kyc.util.Util;
import com.demo.app.model.CloseResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DocActivity extends Activity implements DocContract.View, WebServiceListener, CardCroppingListener, AcuantErrorListener, FacialRecognitionListener {
    public static final int REQUEST_CODE = 22;

    private static final String TAG = DocActivity.class.getName();
    private final String IS_SHOWING_DIALOG_KEY = "isShowingDialog";
    private final String IS_PROCESSING_DIALOG_KEY = "isProcessing";
    private final String IS_CROPPING_DIALOG_KEY = "isCropping";
    private final String IS_VALIDATING_DIALOG_KEY = "isValidating";
    private final String IS_ACTIVATING_DIALOG_KEY = "isActivating";
    private final String IS_SHOWDUPLEXDIALOG_DIALOG_KEY = "isShowDuplexDialog";
    public String sPdf417String = "";
    AcuantAndroidMobileSDKController acuantAndroidMobileSdkControllerInstance = null;
    public MainActivityModel mainActivityModel = null;
    private ProgressDialog progressDialog;
    private AlertDialog showDuplexAlertDialog;
    private AlertDialog alertDialog;
    private boolean isShowErrorAlertDialog;
    private boolean isProcessing;
    private boolean isValidating;
    private boolean isActivating;
    private boolean isCropping;
    private boolean isBackSide;
    private boolean isShowDuplexDialog;
    private boolean isProcessingFacial;
    private boolean inResultMode;
    private DocActivity mainActivity;
    private int cardRegion;
    private Bitmap originalImage;
    private DriversLicenseCard processedCardInformation;
    private FacialData processedFacialData;
    private boolean isFirstLoad = true;
    private static String uid;
    private static String uuidDevice;
    Dialog dialog;
    TextView nameTv;
    TextView idTv;
    TextView message;
    TextView title;
    TextView subTitle;
    TextView card_type;
    TextView textView3;
    TextView textView4;
    ImageView frontCardIb;
    ImageView backCardIb;
    ImageView faceIv;
    ImageView picture;
    ImageView picture2;
    ImageView picture3;
    ImageView picture4;
    ImageView dots;
    Button btnTake;
    Button btnEntendido;
    ProgressBar progressBar;
    DocContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);
        dialog = new Dialog(this);
        //String uid = getIntent().getStringExtra("uid");
        uid = getIntent().getStringExtra("uid");
        uuidDevice = DocumentTypeUtils.getUuiddevice(this);
        setPresenter(new DocPresenter(this, uid, this));

        if (Util.LOG_ENABLED) {
            Utils.appendLog(TAG, "protected void onCreate(Bundle savedInstanceState)");
        }

        // load the model
        if (savedInstanceState == null) {
            if (Util.LOG_ENABLED) {
                Utils.appendLog(TAG, "if (savedInstanceState == null)");
            }
            mainActivityModel = new MainActivityModel();
        } else {
            if (Util.LOG_ENABLED) {
                Utils.appendLog(TAG, "if (savedInstanceState != null)");
            }
            mainActivityModel = DataContext.getInstance().getMainActivityModel();
            // if coming from background_ado and kill the app, restart the model
            if (mainActivityModel == null) {
                mainActivityModel = new MainActivityModel();
            }
        }
        DataContext.getInstance().setContext(getApplicationContext());

        initViews();

        if (Util.isNetworkConnected(this)) {
            alertDialog = new AlertDialog.Builder(this).setMessage(R.string.validating_license).show();
        } else {
            Util.showDialog(this, getResources().getString(R.string.no_connection));
        }

        initializeSDK();

        // update the UI from the model
        driverCardWithFacialButtonPressed(null);
    }

    private void initViews() {
        frontCardIb = findViewById(R.id.ib_ka_front_card);
        frontCardIb.setVisibility(View.INVISIBLE);
        backCardIb = findViewById(R.id.ib_ka_back_card);
        backCardIb.setVisibility(View.INVISIBLE);
        btnTake = findViewById(R.id.btn_takepicture_front);
        btnEntendido = findViewById(R.id.btn_siguiente1);
        title = findViewById(R.id.text_bf_picture);
        //subTitle = findViewById(R.id.text_subtitle);
        nameTv = findViewById(R.id.tv_ka_name);
        idTv = findViewById(R.id.tv_ka_id);
        message = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        //faceIv = findViewById(R.id.iv_ka_face);
        progressBar = findViewById(R.id.prb_afe);
        picture = findViewById(R.id.imageView_picture);
        picture2 = findViewById(R.id.imageView_picture2);
        picture3 = findViewById(R.id.imageView_picture3);
        picture4 = findViewById(R.id.imageView4);
        dots = findViewById(R.id.dots);
        card_type = findViewById(R.id.tv_cardtype2);
        textView4 = findViewById(R.id.textView4);
    }

    private void initializeSDK() {
        //String licenseKey = "431530538E1F";//Set license key here
        String licenseKey = DocumentTypeUtils.getTokenKyc(this);//Set license key here
        // load the controller instance
        Util.lockScreen(this);

        acuantAndroidMobileSdkControllerInstance = AcuantAndroidMobileSDKController.getInstance(this, licenseKey);

        Util.lockScreen(this);
        if (!Util.isTablet(this)) {
            acuantAndroidMobileSdkControllerInstance.setPdf417BarcodeImageDrawable(getResources().getDrawable(R.drawable.barcode));
        }

        acuantAndroidMobileSdkControllerInstance.setWebServiceListener(this);
        acuantAndroidMobileSdkControllerInstance.setWatermarkText(null, 0, 0, 30, 0);
        acuantAndroidMobileSdkControllerInstance.setFacialRecognitionTimeoutInSeconds(20);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int minLength = (int) (Math.min(width, height) * 0.9);
        int maxLength = (int) (minLength * 1.5);
        final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        Typeface currentTypeFace = textPaint.getTypeface();
        Typeface bold = Typeface.create(currentTypeFace, Typeface.BOLD);
        textPaint.setColor(Color.WHITE);
        if (!Util.isTablet(this)) {
            Display display = getWindowManager().getDefaultDisplay();
            if (display.getWidth() < 1000 || display.getHeight() < 1000) {
                textPaint.setTextSize(30);
            } else {
                textPaint.setTextSize(50);
            }

        } else {
            textPaint.setTextSize(30);
        }
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(bold);

        Paint subtextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        subtextPaint.setColor(Color.RED);
        if (!Util.isTablet(this)) {
            Display display = getWindowManager().getDefaultDisplay();
            if (display.getWidth() < 1000 || display.getHeight() < 1000) {
                textPaint.setTextSize(20);
            } else {
                subtextPaint.setTextSize(40);
            }
        } else {
            subtextPaint.setTextSize(25);
        }
        subtextPaint.setTextAlign(Paint.Align.LEFT);
        subtextPaint.setTypeface(Typeface.create(subtextPaint.getTypeface(), Typeface.BOLD));

        final String instrunctionStr = "Get closer until Red Rectangle appears and Blink";
        final String subInstString = "Analyzing...";
        Rect bounds = new Rect();
        textPaint.getTextBounds(instrunctionStr, 0, instrunctionStr.length(), bounds);
        int top = (int) (height * 0.05);
        if (Util.isTablet(this)) {
            top = top - 20;
        }
        int left = (width - bounds.width()) / 2;

        textPaint.getTextBounds(subInstString, 0, subInstString.length(), bounds);
        textPaint.setTextAlign(Paint.Align.LEFT);
        int subLeft = (width - bounds.width()) / 2;

        acuantAndroidMobileSdkControllerInstance.setInstructionText(instrunctionStr, left, top, textPaint);
        if (!Util.isTablet(this)) {
            Display display = getWindowManager().getDefaultDisplay();
            if (display.getWidth() < 1000 || display.getHeight() < 1000) {
                acuantAndroidMobileSdkControllerInstance.setSubInstructionText(subInstString, subLeft, top + 15, subtextPaint);
            } else {
                acuantAndroidMobileSdkControllerInstance.setSubInstructionText(subInstString, subLeft, top + 60, subtextPaint);
            }
        } else {
            acuantAndroidMobileSdkControllerInstance.setSubInstructionText(subInstString, subLeft, top + 30, subtextPaint);
        }


        //acuantAndroidMobileSdkControllerInstance.setShowActionBar(false);
        //acuantAndroidMobileSdkControllerInstance.setShowStatusBar(false);
        acuantAndroidMobileSdkControllerInstance.setFlashlight(false);
        //acuantAndroidMobileSdkControllerInstance.setFlashlight(0,0,50,0);
        //acuantAndroidMobileSdkControllerInstance.setFlashlightImageDrawable(getResources().getDrawable(R.drawable.lighton), getResources().getDrawable(R.drawable.lightoff));
        //acuantAndroidMobileSdkControllerInstance.setShowInitialMessage(true);
        acuantAndroidMobileSdkControllerInstance.setCropBarcode(false);
        acuantAndroidMobileSdkControllerInstance.setCaptureOriginalCapture(false);
        //acuantAndroidMobileSdkControllerInstance.setCropBarcodeOnCancel(true);
        //acuantAndroidMobileSdkControllerInstance.setPdf417BarcodeDialogWaitingBarcode("AcuantAndroidMobileSampleSDK","ALIGN AND TAP", 10, "Try Again", "Yes");
        //acuantAndroidMobileSdkControllerInstance.setCanShowBracketsOnTablet(true);
        // load several member variables

        acuantAndroidMobileSdkControllerInstance.setCardCroppingListener(this);
        acuantAndroidMobileSdkControllerInstance.setAcuantErrorListener(this);
    }

    /**
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //mainActivityModel.clearImages();
        BitmapDrawable front_drawable = (BitmapDrawable) frontCardIb.getDrawable();
        if (front_drawable != null) {
            Bitmap bitmap = front_drawable.getBitmap();
            bitmap.recycle();
            frontCardIb.setImageBitmap(null);
        }

        BitmapDrawable back_drawable = (BitmapDrawable) backCardIb.getDrawable();
        if (back_drawable != null) {
            Bitmap bitmap = back_drawable.getBitmap();
            bitmap.recycle();
            backCardIb.setImageBitmap(null);
        }
        processedCardInformation = null;
        System.gc();
        System.runFinalization();
        if (Util.LOG_ENABLED) {
            Utils.appendLog(TAG, "protected void onActivityResult(int requestCode, int resultCode, Intent data)");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUI();
    }


    @Override
    public void onCardCroppingStart(Activity activity) {
        System.gc();
        System.runFinalization();
        if (Utils.LOG_ENABLED) {
            Utils.appendLog(TAG, "public void onCardCroppingStart(Activity activity)");
        }
        cardRegion = DataContext.getInstance().getCardRegion();
        if (progressDialog != null && progressDialog.isShowing()) {
            Util.dismissDialog(progressDialog);
        }
        Util.lockScreen(this);
        progressDialog = Util.showProgessDialog(activity, "Estamos preparando la imagen...");
        isCropping = true;
    }

    /**
     * Result from the CSSNMobileSDKController.showCameraInterface method when
     * popover == true
     */
    @Override
    public void onCardCroppingFinish(final Bitmap bitmap, int detectedCardType) {
        if (progressDialog != null && progressDialog.isShowing()) {
            Util.dismissDialog(progressDialog);
            progressDialog = null;
        }
        TempImageStore.setBitmapImage(bitmap);
        TempImageStore.setImageConfirmationListener(new ConfirmationListener() {
            @Override
            public void confimed() {
                if (Util.LOG_ENABLED) {
                    Utils.appendLog("appendLog", "public void onCardCroppedFinish(final Bitmap bitmap) - begin");
                }
                if (bitmap != null) {
                    updateModelAndUIFromCroppedCard(bitmap);
                } else {
                    // set an error to be shown in the onResume method.
                    mainActivityModel.setErrorMessage("No hemos podido detectar tu documento, por favor trata de nuevo.");
                    updateModelAndUIFromCroppedCard(originalImage);
                }
                Util.unLockScreen(DocActivity.this);

                if (Util.LOG_ENABLED) {
                    Utils.appendLog("appendLog", "public void onCardCroppedFinish(final Bitmap bitmap) - end");
                }
                isCropping = false;
            }

            @Override
            public void retry() {
                showCameraInterface();
            }
        });

        Intent imageConfirmationIntent = new Intent(this, ImageConformationActivity.class);
        if (bitmap == null) {
            TempImageStore.setCroppingPassed(false);
        } else {
            TempImageStore.setCroppingPassed(true);
        }
        TempImageStore.setCardType(mainActivityModel.getCurrentOptionType());
        startActivity(imageConfirmationIntent);

    }

    /**
     * Result from the CSSNMobileSDKController.showCameraInterface method when
     * popover == true
     */
    @Override
    public void onCardCroppingFinish(final Bitmap bitmap, final boolean scanBackSide, int detectedCardType) {
        //final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.test_sa_dl);
        if (progressDialog != null && progressDialog.isShowing()) {
            Util.dismissDialog(progressDialog);
            progressDialog = null;
        }
        TempImageStore.setBitmapImage(bitmap);
        TempImageStore.setImageConfirmationListener(new ConfirmationListener() {
            @Override
            public void confimed() {
                presentCameraForBackSide(bitmap, scanBackSide);
            }

            @Override
            public void retry() {
                if ((cardRegion == Region.REGION_UNITED_STATES || cardRegion == Region.REGION_CANADA)
                        && mainActivityModel.getCurrentOptionType() == CardType.DRIVERS_LICENSE
                        && isBackSide) {
                    if (isBackSide){
                        acuantAndroidMobileSdkControllerInstance.setInitialMessageDescriptor(R.layout.tap_to_focus);
                        acuantAndroidMobileSdkControllerInstance.showCameraInterfacePDF417(mainActivity, CardType.DRIVERS_LICENSE, cardRegion);
                    }else{
                        acuantAndroidMobileSdkControllerInstance.setInitialMessageDescriptor(R.layout.tap_to_focus_front);
                        acuantAndroidMobileSdkControllerInstance.showCameraInterfacePDF417(mainActivity, CardType.DRIVERS_LICENSE, cardRegion);
                    }
                } else {
                    showCameraInterface();
                }
            }
        });
        Intent imageConfirmationIntent = new Intent(this, ImageConformationActivity.class);
        if (bitmap == null) {
            TempImageStore.setCroppingPassed(false);
        } else {
            TempImageStore.setCroppingPassed(true);
        }
        TempImageStore.setCardType(mainActivityModel.getCurrentOptionType());
        startActivity(imageConfirmationIntent);
    }

    public void presentCameraForBackSide(final Bitmap bitmap, boolean scanBackSide) {
        if (Util.LOG_ENABLED) {
            Utils.appendLog("appendLog", "public void onCardCroppedFinish(final Bitmap bitmap) - begin");
        }
        cardRegion = DataContext.getInstance().getCardRegion();
        if (bitmap != null) {
            isBackSide = scanBackSide;
            if (isBackSide) {
                mainActivityModel.setCardSideSelected(MainActivityModel.CardSide.FRONT);
            } else {
                mainActivityModel.setCardSideSelected(MainActivityModel.CardSide.BACK);
            }

            if (mainActivityModel.getCurrentOptionType() == CardType.DRIVERS_LICENSE && isBackSide) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //showDuplexDialog();
                    }
                }, 100);
            }
            updateModelAndUIFromCroppedCard(bitmap);
        } else {
            // set an error to be shown in the onResume method.
            mainActivityModel.setErrorMessage("No hemos podido detectar tu documento, por favor trata de nuevo.");
            updateModelAndUIFromCroppedCard(originalImage);
        }

        Util.unLockScreen(this);

        if (Util.LOG_ENABLED) {
            Utils.appendLog("appendLog", "public void onCardCroppedFinish(final Bitmap bitmap) - end");
        }
        isCropping = false;
    }

    private void showDuplexDialog() {
        mainActivity = this;
        cardRegion = DataContext.getInstance().getCardRegion();
        Util.dismissDialog(showDuplexAlertDialog);
        Util.dismissDialog(alertDialog);
        showDuplexAlertDialog = new AlertDialog.Builder(this).create();
        showDuplexAlertDialog = Util.showDialog(this, getString(R.string.dl_duplex_dialog), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cardRegion == Region.REGION_UNITED_STATES || cardRegion == Region.REGION_CANADA) {
                    acuantAndroidMobileSdkControllerInstance.setInitialMessageDescriptor(R.layout.tap_to_focus);
                    acuantAndroidMobileSdkControllerInstance.showCameraInterfacePDF417(mainActivity, CardType.DRIVERS_LICENSE, cardRegion);
                } else {
                    acuantAndroidMobileSdkControllerInstance.showManualCameraInterface(mainActivity, CardType.DRIVERS_LICENSE, cardRegion, isBackSide);
                }
                showDuplexAlertDialog.dismiss();
                isShowDuplexDialog = false;
            }
        });
        isShowDuplexDialog = true;
    }


    @Override
    public void onPDF417Finish(String result) {
        sPdf417String = result;
    }

    @Override
    public void onOriginalCapture(Bitmap bitmap) {
        originalImage = bitmap;
    }

    @Override
    public void onCancelCapture(Bitmap croppedImage, Bitmap originalImage) {
        Utils.appendLog("Acuant", "onCancelCapture");
        if (croppedImage != null || originalImage != null) {
            final Bitmap cImage = croppedImage;
            final Bitmap oImage = originalImage;
            if (cImage != null) {
                //mainActivityModel.setBackSideCardImage(cImage);
                backCardIb.setImageBitmap(cImage);
            } else if (oImage != null) {
                //mainActivityModel.setBackSideCardImage(oImage);
                backCardIb.setImageBitmap(oImage);
            }
        }
    }

    @Override
    public void onBarcodeTimeOut(Bitmap croppedImage, Bitmap originalImage) {
        final Bitmap cImage = croppedImage;
        final Bitmap oImage = originalImage;
        acuantAndroidMobileSdkControllerInstance.pauseScanningBarcodeCamera();
        AlertDialog.Builder builder = new AlertDialog.Builder(acuantAndroidMobileSdkControllerInstance.getBarcodeCameraContext());
        // barcode Dialog "ignore" option
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if (cImage != null) {
                    //mainActivityModel.setBackSideCardImage(cImage);
                    backCardIb.setImageBitmap(cImage);
                } else if (oImage != null) {
                    backCardIb.setImageBitmap(oImage);
                    //mainActivityModel.setBackSideCardImage(oImage);
                }
                acuantAndroidMobileSdkControllerInstance.finishScanningBarcodeCamera();
                dialog.dismiss();
            }
        });
        // barcode Dialog "retry" option
        builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                acuantAndroidMobileSdkControllerInstance.resumeScanningBarcodeCamera();
                dialog.dismiss();
            }
        });
        //barcode Dialog title and main message
        builder.setMessage("Unable to scan the barcode?");
        builder.setTitle("AcuantMobileSDK");
        builder.create().show();

    }

    @Override
    public void onCardImageCaptured() {

    }

    public String getDocumentype() {
        String value = "1";

        SharedPreferences odata = getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        if (odata != null) {
            value = odata.getString("documentType", "");
        }

        if (value == ""){value  = "1";}

        return value;
    }

    /**
     * Updates the model, and the ui. Called after acquiring a cropped card.
     */
    private void updateModelAndUIFromCroppedCard(final Bitmap bitmap) {
        switch (mainActivityModel.getCardSideSelected()) {
            case FRONT:
                DocumentTypeUtils.storeFrontDoc(this,false);
                title.setText("Validación de tu identidad");
                new Log().execute("Info", "FrontSideCapturePressed", "Capture Process Front Side successful", uid,DocumentTypeUtils.getUuiddevice(this), "");
                card_type.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);
                btnEntendido.setVisibility(View.INVISIBLE);
                picture.setVisibility(View.INVISIBLE);
                picture2.setVisibility(View.INVISIBLE);
                picture3.setVisibility(View.INVISIBLE);
                picture4.setVisibility(View.VISIBLE);
                message.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                dots.setVisibility(View.INVISIBLE);
                btnTake.setText("Continuar");
                presenter.onFrontImageCaptured(bitmap, getDocumentype(), uuidDevice);
                break;

            case BACK:
                new Log().execute("Info", "BackSideCapturePressed", "Capture Process Back Side successful", uid, DocumentTypeUtils.getUuiddevice(this), "");
                picture4.setVisibility(View.INVISIBLE);
                frontCardIb.setVisibility(View.INVISIBLE);
                backCardIb.setImageBitmap(bitmap);
                btnTake.setVisibility(View.INVISIBLE);
                presenter.onBackImageCaptured(bitmap, uuidDevice);
                break;

            default:
                throw new IllegalStateException("This method is bad implemented, there is not processing for the cardSide '"
                        + mainActivityModel.getCardSideSelected() + "'");
        }
        updateUI();
    }

    /**
     * @param v
     */
    public void frontSideCapturePressed(View v) {
        if (isBackSide) {
            backSideCapturePressed(v);
        } else {
            new Log().execute("Info", "FrontSideCapturePressed", "Start Capture Front Side", uid, DocumentTypeUtils.getUuiddevice(this), "");
            if (Util.LOG_ENABLED) {
                Utils.appendLog(TAG, "public void frontSideCapturePressed(View v)");
            }
            DocumentTypeUtils.storeFrontDoc(this,true);
            isBackSide = false;

            //mainActivityModel.clearImages();

            mainActivityModel.setCardSideSelected(MainActivityModel.CardSide.FRONT);

            showCameraInterface();
        }
    }

    /**
     * @param v
     */
    public void backSideCapturePressed(View v) {
        new Log().execute("Info", "BackSideCapturePressed", "Start Capture Back Side", uid, DocumentTypeUtils.getUuiddevice(this), "");
        mainActivityModel.setCurrentOptionType(CardType.DRIVERS_LICENSE);

        if (Util.LOG_ENABLED) {
            Utils.appendLog(TAG, "public void backSideCapturePressed(View v)");
        }
        isBackSide = true;

        //mainActivityModel.clearBackImage();

        mainActivityModel.setCardSideSelected(MainActivityModel.CardSide.BACK);
        showCameraInterface();
    }

    /**
     *
     */
    private void showCameraInterface() {
        final int currentOptionType = mainActivityModel.getCurrentOptionType();
        cardRegion = DataContext.getInstance().getCardRegion();
        alertDialog = new AlertDialog.Builder(this).create();
        LicenseDetails license_details = DataContext.getInstance().getCssnLicenseDetails();
        if (currentOptionType == CardType.PASSPORT) {
            acuantAndroidMobileSdkControllerInstance.setWidth(AcuantUtil.DEFAULT_CROP_PASSPORT_WIDTH);
        } else if (currentOptionType == CardType.MEDICAL_INSURANCE) {
            acuantAndroidMobileSdkControllerInstance.setWidth(AcuantUtil.DEFAULT_CROP_MEDICAL_INSURANCE);
        } else {
            if (license_details != null && license_details.isAssureIDAllowed()) {
                acuantAndroidMobileSdkControllerInstance.setWidth(AcuantUtil.DEFAULT_CROP_DRIVERS_LICENSE_WIDTH_FOR_AUTHENTICATION);
            } else {
                acuantAndroidMobileSdkControllerInstance.setWidth(AcuantUtil.DEFAULT_CROP_DRIVERS_LICENSE_WIDTH);
            }
            //acuantAndroidMobileSdkControllerInstance.setWidth(2600);
        }

        if (isBackSide){
            acuantAndroidMobileSdkControllerInstance.setInitialMessageDescriptor(R.layout.align_and_tap_back);
            acuantAndroidMobileSdkControllerInstance.setFinalMessageDescriptor(R.layout.hold_steady_back);
        }else{
            acuantAndroidMobileSdkControllerInstance.setInitialMessageDescriptor(R.layout.align_and_tap_front);
            acuantAndroidMobileSdkControllerInstance.setFinalMessageDescriptor(R.layout.hold_steady_front);
        }
        try {
            acuantAndroidMobileSdkControllerInstance.showManualCameraInterface(this, currentOptionType, cardRegion, isBackSide);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called after a tap in the driver's card button.
     *
     * @param v
     */
    public void driverCardWithFacialButtonPressed(View v) {
        // update the model
        processedCardInformation = null;
        switch(getDocumentype()) {
            case "2":
                mainActivityModel.setCurrentOptionType(CardType.PASSPORT);
                break;
            default:
                mainActivityModel.setCurrentOptionType(CardType.DRIVERS_LICENSE);
        }
        //mainActivityModel.setCurrentOptionType(CardType.DRIVERS_LICENSE);
        isProcessing = false;
        isProcessingFacial = false;
        DataContext.getInstance().setCardRegion(Region.REGION_AMERICA);
        updateUI();
    }


    /**
     * calculate the width and height of the front side card image and resize them
     */
    private void resizeImageFrames(int cardType) {
        double aspectRatio = AcuantUtil.getAspectRatio(cardType);

        int height = (int) (frontCardIb.getLayoutParams().width * aspectRatio);
        int width = frontCardIb.getLayoutParams().width;

        frontCardIb.getLayoutParams().height = height;
        frontCardIb.getLayoutParams().width = width;

        frontCardIb.setLayoutParams(frontCardIb.getLayoutParams());

        if (cardType == CardType.MEDICAL_INSURANCE) {
            backCardIb.getLayoutParams().height = height;
            backCardIb.getLayoutParams().width = width;

            backCardIb.setLayoutParams(backCardIb.getLayoutParams());
        }
    }

    /**
     * Updates the card's frame layout, shows/hides the back side card frame,
     * highlights the selected option, and load the card images in the view.
     */
    public void updateUI() {
        if (Utils.LOG_ENABLED) {
            Utils.appendLog(TAG, "private void updateUI()");
        }

        if (mainActivityModel.getErrorMessage() != null) {
            Util.dismissDialog(alertDialog);

            alertDialog = Util.showDialog(this, mainActivityModel.getErrorMessage(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mainActivityModel.setErrorMessage(null);
                    isShowErrorAlertDialog = false;
                }
            });
            isShowErrorAlertDialog = true;
        }

        if (mainActivityModel.getCurrentOptionType() == -1) {
            // do not do any extra processing
            return;
        }

        // calculate the width and height of the front side card image and resize them
//        resizeImageFrames(mainActivityModel.getCurrentOptionType());

        // update card in front image view
        //frontCardIb.setImageBitmap( Util.getRoundedCornerBitmap(mainActivityModel.getFrontSideCardImage(), this.getApplicationContext()));
        BitmapDrawable front_drawable = (BitmapDrawable) frontCardIb.getDrawable();
        if (front_drawable != null) {
            Bitmap bitmap = front_drawable.getBitmap();
        }

        // update card in back image view
        BitmapDrawable back_drawable = (BitmapDrawable) backCardIb.getDrawable();
        if (back_drawable != null) {
            //frontCardIb.setEnabled(false);
            Bitmap bitmap = back_drawable.getBitmap();
        }

        // update the process button
        BitmapDrawable frontDrawable = (BitmapDrawable) frontCardIb.getDrawable();
        BitmapDrawable backDrawable = (BitmapDrawable) backCardIb.getDrawable();
        if (frontDrawable != null && backDrawable != null) {

            if (frontDrawable.getBitmap() != null && backDrawable.getBitmap() != null && !inResultMode) {

                //processCardBtn.setEnabled(true);
            } else {
                //processCardBtn.setEnabled(false);

            }
        }
    }

    /**
     * Called by the process Button
     *
     * @param v
     */
    public void processCard(View v) {

        BitmapDrawable front_drawable = (BitmapDrawable) frontCardIb.getDrawable();
        BitmapDrawable back_drawable = (BitmapDrawable) backCardIb.getDrawable();

        if (front_drawable == null || back_drawable == null) {
            return;
        }

        Bitmap front_bitmap = front_drawable.getBitmap();
        Bitmap back_bitmap = back_drawable.getBitmap();


        if (!isProcessingFacial) {
            if (progressDialog != null && progressDialog.isShowing()) {
                Util.dismissDialog(progressDialog);
            }
            progressDialog = Util.showProgessDialog(DocActivity.this, "Procesando...");
            Util.lockScreen(this);
        }

        if ((!isProcessing && processedCardInformation == null)) {
            isProcessing = true;
            // check for the internet connection
            if (!Utils.isNetworkAvailable(this)) {
                String msg = getString(R.string.no_connection);
                Utils.appendLog(TAG, msg);
                Util.dismissDialog(alertDialog);
                alertDialog = Util.showDialog(this, msg, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isShowErrorAlertDialog = false;
                    }
                });
                isShowErrorAlertDialog = true;
                return;
            }

            // process the card
            //progressDialog = Util.showProgessDialog(DocActivity.this, "Capturing data ...");

            //Util.lockScreen(this);

            ProcessImageRequestOptions options = ProcessImageRequestOptions.getInstance();
            options.autoDetectState = true;
            options.stateID = -1;
            options.reformatImage = true;
            options.reformatImageColor = 0;
            options.DPI = 150;
            options.cropImage = false;
            options.faceDetec = true;
            options.signDetec = true;
            options.iRegion = DataContext.getInstance().getCardRegion();
            options.acuantCardType = mainActivityModel.getCurrentOptionType();
            //options.logTransaction = false;

            acuantAndroidMobileSdkControllerInstance.callProcessImageServices(front_bitmap, back_bitmap, sPdf417String, this, options);
            resetPdf417String();
        }
    }


    /**
     * Called after card processing is over.
     *
     * @param
     */
    public void processImageValidation(Bitmap faceImage, Bitmap idCropedFaceImage) {
        if (processedCardInformation != null) {
            isProcessingFacial = false;
        }
        mainActivityModel.setCurrentOptionType(CardType.FACIAL_RECOGNITION);
        if (!Utils.isNetworkAvailable(this)) {
            String msg = getString(R.string.no_connection);
            Utils.appendLog(TAG, msg);
            Util.dismissDialog(alertDialog);
            alertDialog = Util.showDialog(this, msg, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isShowErrorAlertDialog = false;
                }
            });
            isShowErrorAlertDialog = true;
            return;
        }

        //Util.lockScreen(this);

        ProcessImageRequestOptions options = ProcessImageRequestOptions.getInstance();
        options.acuantCardType = CardType.FACIAL_RECOGNITION;
        //options.logTransaction = true;
        acuantAndroidMobileSdkControllerInstance.callProcessImageServices(faceImage, idCropedFaceImage, null, this, options);
    }

    private void resetPdf417String() {
        sPdf417String = "";
    }

    @Override
    public void validateLicenseKeyCompleted(LicenseDetails details) {

        alertDialog.dismiss();

        frontCardIb.setEnabled(true);

        isFirstLoad = false;
        Util.unLockScreen(DocActivity.this);

        LicenseDetails cssnLicenseDetails = DataContext.getInstance().getCssnLicenseDetails();
        DataContext.getInstance().setCssnLicenseDetails(details);

        // update model
        mainActivityModel.setState(MainActivityModel.State.VALIDATED);
        if (cssnLicenseDetails != null && cssnLicenseDetails.isLicenseKeyActivated()) {
            mainActivityModel.setValidatedStateActivation(MainActivityModel.State.ValidatedStateActivation.ACTIVATED);
        } else {
            mainActivityModel.setValidatedStateActivation(MainActivityModel.State.ValidatedStateActivation.NO_ACTIVATED);
        }
        // message dialogs
        //acuantAndroidMobileSdkControllerInstance.enableLocationAuthentication(this);
        isValidating = false;

        // start capture
//        frontSideCapturePressed(null);
    }


    public void nextScreen(View v) {

        BitmapDrawable frontDrawable = (BitmapDrawable) frontCardIb.getDrawable();
        Bitmap front_bitmap = frontDrawable.getBitmap();

        BitmapDrawable backDrawable = (BitmapDrawable) backCardIb.getDrawable();
        Bitmap back_bitmap = backDrawable.getBitmap();


    }


    @Override
    public void processImageServiceCompleted(Card card) {

        Util.dismissDialog(progressDialog);

        if (Util.LOG_ENABLED) {
            Utils.appendLog(TAG, "public void processImageServiceCompleted(CSSNCard card, int status, String errorMessage)");
        }
        isProcessing = false;

        System.gc();
        showData(card);
    }


    /**
     *
     */
    private void showBackSideCardImage() {

        backCardIb.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean wschanged = sharedPref.getBoolean("WSCHANGED", false);
        if (wschanged && !isFirstLoad) {
            initializeSDK();
        }
        if (Util.LOG_ENABLED) {
            Utils.appendLog(TAG, "protected void onResume()");
        }
        frontCardIb.requestFocus();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //spdf417 = savedInstanceState.get(PDF417_STRING_KEY) != null ? (String) savedInstanceState.get(PDF417_STRING_KEY) : "";
        isShowErrorAlertDialog = savedInstanceState.getBoolean(IS_SHOWING_DIALOG_KEY, false);
        isProcessing = savedInstanceState.getBoolean(IS_PROCESSING_DIALOG_KEY, false);
        isCropping = savedInstanceState.getBoolean(IS_CROPPING_DIALOG_KEY, false);
        isValidating = savedInstanceState.getBoolean(IS_VALIDATING_DIALOG_KEY, false);
        isActivating = savedInstanceState.getBoolean(IS_ACTIVATING_DIALOG_KEY, false);
        isShowDuplexDialog = savedInstanceState.getBoolean(IS_SHOWDUPLEXDIALOG_DIALOG_KEY, false);
        if (progressDialog != null && progressDialog.isShowing()) {
            Util.dismissDialog(progressDialog);
        }
        if (isShowDuplexDialog) {
            showDuplexDialog();
        }
        if (isProcessing) {
            progressDialog = Util.showProgessDialog(DocActivity.this, "Capturing data ...");
        }
        if (isCropping) {
            progressDialog = Util.showProgessDialog(DocActivity.this, "Estamos preparando la imagen...");
        }
        if (isValidating) {
            progressDialog = Util.showProgessDialog(DocActivity.this, "Validating License ..");
        }
        if (isActivating) {
            progressDialog = Util.showProgessDialog(DocActivity.this, "Activating License ..");
        }
        if (isShowErrorAlertDialog) {
            alertDialog.show();
        }
        updateUI();
    }

    /**
     *
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (Util.LOG_ENABLED) {
            Utils.appendLog(TAG, "protected void onSaveInstanceState(Bundle outState)");
        }

        DataContext.getInstance().setMainActivityModel(mainActivityModel);
        //outState.putString(PDF417_STRING_KEY, this.pdf417);
        outState.putBoolean(IS_SHOWING_DIALOG_KEY, isShowErrorAlertDialog);
        outState.putBoolean(IS_PROCESSING_DIALOG_KEY, isProcessing);
        outState.putBoolean(IS_CROPPING_DIALOG_KEY, isCropping);
        outState.putBoolean(IS_ACTIVATING_DIALOG_KEY, isActivating);
        outState.putBoolean(IS_VALIDATING_DIALOG_KEY, isValidating);
        outState.putBoolean(IS_SHOWDUPLEXDIALOG_DIALOG_KEY, isShowDuplexDialog);
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (Utils.LOG_ENABLED) {
            Utils.appendLog(TAG, "protected void onPause()");
        }
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TempImageStore.cleanup();
        acuantAndroidMobileSdkControllerInstance.cleanup();
        if (Util.LOG_ENABLED) {
            Utils.appendLog(TAG, "protected void onDestroy()");
        }
    }

    /**
     * @param bitmap
     * @return
     */
    private boolean saveBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            String formattedDate = df.format(c.getTime());

            File file = new File("sdcard/CSSNCardCropped" + formattedDate + ".png");
            FileOutputStream fOutputStream = null;

            try {
                fOutputStream = new FileOutputStream(file);

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutputStream);

                fOutputStream.flush();
                fOutputStream.close();

                MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            } catch (FileNotFoundException e) {
                if (Util.LOG_ENABLED) {
                    Utils.appendLog(TAG, e.getMessage());
                }
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
                return false;
            } catch (IOException e) {
                if (Util.LOG_ENABLED) {
                    Utils.appendLog(TAG, e.getMessage());
                }
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void didFailWithError(int code, String message) {
        Utils.appendLog("didFailWithError", "didFailWithError:" + code + "message" + message);
        Util.dismissDialog(progressDialog);
        Util.unLockScreen(DocActivity.this);
        String msg = message;
        if (code == ErrorType.AcuantErrorCouldNotReachServer) {
            msg = getString(R.string.no_connection);
        } else if (code == ErrorType.AcuantErrorUnableToCrop) {
            updateModelAndUIFromCroppedCard(originalImage);
        }
        if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog = Util.showDialog(this, msg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isShowErrorAlertDialog = false;
                }
            });
        }
        isShowErrorAlertDialog = true;
        if (Util.LOG_ENABLED) {
            Utils.appendLog(TAG, "didFailWithError:" + message);
        }
        // message dialogs
        isValidating = false;
        isProcessing = false;
        isActivating = false;
    }

    @Override
    public void onFacialRecognitionTimedOut(final Bitmap bitmap) {
        isProcessingFacial = false;
        onFacialRecognitionCompleted(bitmap);
    }

    @Override
    public void onFacialRecognitionCompleted(final Bitmap bitmap) {


    }

    @Override
    public void onFacialRecognitionCanceled() {
        isProcessingFacial = false;
    }


    //Override this only for API 23 and Above
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Permission.PERMISSION_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Log().execute("Info", "onRequestPermissionsResult", "camera access granted ", uid, DocumentTypeUtils.getUuiddevice(this), "");
                    showCameraInterface();

                } else {
                    // permission denied
                    new Log().execute("Warning", "onRequestPermissionsResult", "camera access not granted ", uid, DocumentTypeUtils.getUuiddevice(this), "");
                    Util.showDialog(this, "Denied permission.Please give camera permission to proceed.");
                }
                return;
            }

            /*case Permission.PERMISSION_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Log().execute("Info", "onRequestPermissionsResult", "Location access granted", uid, DocumentTypeUtils.getUuiddevice(this), "");
                    acuantAndroidMobileSdkControllerInstance.enableLocationAuthentication(this);
                } else {
                    // permission denied
                    new Log().execute("Warning", "onRequestPermissionsResult", "Location access not granted", uid, DocumentTypeUtils.getUuiddevice(this), "");
                    Util.showDialog(this, "Denied permission.Please give location permission to proceed.");
                }
                return;
            }*/
        }
    }

    public void showData(Card card) {

        //nextBtn.setEnabled(true);
        String dialogMessage = null;
        try {
            DataContext.getInstance().setCardType(mainActivityModel.getCurrentOptionType());

            if (card == null || card.isEmpty()) {
                dialogMessage = "No data found for this license card!";
            } else {

                switch (mainActivityModel.getCurrentOptionType()) {
                    case CardType.DRIVERS_LICENSE:
                        DataContext.getInstance().setProcessedLicenseCard((DriversLicenseCard) card);
                        break;

                }

                Util.unLockScreen(DocActivity.this);

                DriversLicenseCard dlCard = (DriversLicenseCard) card;
                final String firstName = dlCard.getNameFirst();
                final String lastName = dlCard.getNameLast();
                final String id = dlCard.getLicenceID();
                final Bitmap faceImage = dlCard.getFaceImage();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nameTv.setText(firstName + " " + lastName);
                        idTv.setText(id);
                        faceIv.setImageBitmap(faceImage);
                    }
                });
            }


        } catch (Exception e) {
            Utils.appendLog(TAG, e.getMessage());
            dialogMessage = "Sorry! Internal error has occurred, please contact us!";

        }

        if (dialogMessage != null) {
            Util.dismissDialog(alertDialog);
            alertDialog = Util.showDialog(this, dialogMessage, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isShowErrorAlertDialog = false;
                }
            });
            isShowErrorAlertDialog = true;
        }
    }

    @Override
    public void onWebServiceStart() {

        progressBar.setVisibility(View.VISIBLE);
        btnTake.setVisibility(View.INVISIBLE);
        //message.setVisibility(View.INVISIBLE);
    }

    @Override
    public void finishFlow(boolean result, CloseResponse response, Integer statuscode) {
        btnTake.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);
        if (result) {
            ScanovateSdk.getHandler().onSuccess(response, statuscode, uuidDevice);
        } else {
            CloseResponse responseError = new CloseResponse();
            CloseResponse.Extras extrasError = new CloseResponse.Extras();
            responseError.setExtras(extrasError);
            if(statuscode == 400) {
                responseError.getExtras().setIdState("15");
                responseError.getExtras().setStateName("Error");
                responseError.getExtras().setAdditionalProp1("Los datos proporcionados no corresponden con los criterios esperados");
            }else if(statuscode == 401) {
                responseError.getExtras().setIdState("15");
                responseError.getExtras().setStateName("Error");
                responseError.getExtras().setAdditionalProp1("El proceso de autorización no fue exitoso. Valide el codigo de proyecto y/o el API Key");
            }else if(statuscode == 404){
                responseError.getExtras().setIdState("15");
                responseError.getExtras().setStateName("Error");
                responseError.getExtras().setAdditionalProp1("El codigo de proyecto y/o el UID especificado no existe");
            }else{
                responseError.getExtras().setIdState("15");
                responseError.getExtras().setStateName("Error");
                responseError.getExtras().setAdditionalProp1("Ha ocurrido un error, valide el número de id entregado para obtener más detalles");
            }
            ScanovateSdk.getHandler().onFailure(responseError);
        }
        finish();
    }

    public void setPresenter(DocContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @Override
    public void onWebServiceStop() {
        progressBar.setVisibility(View.INVISIBLE);
        btnTake.setVisibility(View.VISIBLE);
        //message.setVisibility(View.VISIBLE);

    }

    @Override
    public void onWebServiceResume() {
        subTitle.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void continueFlow(boolean result, CloseResponse response, Integer code) {

        //Intent intent = new Intent(this, GetDataActivity.class);
        //intent.putExtra("uid", uid);
        //intent.putExtra("uuidDevice", uuidDevice);
        //intent.putExtra("cedulaNumber", response.getIdNumber());
        //intent.putExtra("dactilarCode", response.getDactilarCode());
        presenter.close(uid);
        //startActivityForResult(intent, GetDataActivity.REQUEST_CODE);
        finish();
    }

    @Override
    public void onNoConnection() {
        Button btnContinue;
        dialog.setContentView(R.layout.no_connection);
        btnContinue = (Button) dialog.findViewById(R.id.tv_retry);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //presenter.getConfig(RetrofitClient.getApiKey_Sdk());
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}