package com.demo.app.UI.Face;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.app.DocumentTypeUtils;
import com.demo.app.R;
import com.demo.app.ScanovateApp;
import com.demo.app.ScanovateSdk;
import com.demo.app.UI.LocationGeo;
import com.demo.app.UI.Log;
import com.demo.app.UI.base.BaseActivity;
import com.demo.app.model.CloseResponse;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FaceActivity extends BaseActivity implements FaceContract.View {
    private SharedPreferences sharedPreferences;
    private static Context context;
    public static final int CAMERA_REQUEST_CODE = 1;
    FaceContract.Presenter presenter;

    Dialog dialog;
    TextView statusTv;
//    TextView textGuide;
    TextView title;
    //TextView subTitle;
    TextView annotation;
    TextView tv_cardType;
    TextView tv_tittle;
    ImageView faceIb;
    ImageView camera;
    ImageView dots;
    ProgressBar progressBar;
    Button firstButton;
    Button nextBtn;
    Button retake;
    Integer CountLiveness = 0;
    Double latitude = 0.0;
    Double longitude = 0.0;
    String uid;

    private static String uuidDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        context = this;
        uuidDevice = DocumentTypeUtils.getUuiddevice(this);
        dialog = new Dialog(this);
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.gilroy_semibold);
        setPresenter(new FacePresenter(this,this));
        localizacion();
        initViews();
    }

    private void localizacion() {
        try {
            LocationGeo finder;
            finder = new LocationGeo(this);
            if (finder.canGetLocation()) {
                latitude = finder.getLatitude();
                longitude = finder.getLongitude();
                //Toast.makeText(this, “lat-lng “ + latitude + “ — “ + longitude, Toast.LENGTH_LONG).show();
            } else {
                finder.showSettingsAlert();
            }
        } catch (Exception e) {
        }
    }

    void initViews() {

        statusTv = findViewById(R.id.tv_fea_status);
        dots = findViewById(R.id.dots);
        tv_tittle = findViewById(R.id.tv_tool);
        tv_cardType = findViewById(R.id.tv_cardtype);
//        textGuide = findViewById(R.id.text_bf_picture);
        //subTitle = findViewById(R.id.text_subtitle);
        annotation = findViewById(R.id.annotation);
        camera = findViewById(R.id.imageViewCamera);
        title = findViewById(R.id.tv_afe_take_picture_title);
        //faceIb = findViewById(R.id.view_phone_shape);
        faceIb = findViewById(R.id.imageViewFace);
        faceIb.setVisibility(View.INVISIBLE);
        firstButton = findViewById(R.id.btn_siguiente1);
        progressBar = findViewById(R.id.prb_afe);
        retake = findViewById(R.id.btn_retake_picture);
        retake.setVisibility(View.INVISIBLE);
        nextBtn = findViewById(R.id.btn_siguiente);
        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (latitude == 0.0 && longitude == 0.0)
                    localizacion();
                if (!isEmulator()) {
//                    capture(null);
                } else {
                    if (DocumentTypeUtils.getVerification(ScanovateApp.getAppContext())){
                        CloseResponse closeResponse = new Gson().fromJson("{\"Uid\":\"b23086ff-dd2b-4f8c-9e4a-8e69c9cb023f\",\"StartingDate\":\"2020-03-09T20:29:10.819378-05:00\",\"CreationDate\":\"2020-03-10T08:32:13.0187583-05:00\",\"CreationIP\":\"198.143.41.3\",\"DocumentType\":1,\"IdNumber\":\"1000336704\",\"FirstName\":\"MARLON\",\"SecondName\":\"ANDRES\",\"FirstSurname\":\"MARIN\",\"SecondSurname\":\"BELTRAN\",\"Gender\":\"\",\"BirthDate\":null,\"Street\":null,\"CedulateCondition\":null,\"Spouse\":null,\"Home\":null,\"MaritalStatus\":null,\"DateOfIdentification\":null,\"DateOfDeath\":null,\"MarriageDate\":null,\"Instruction\":null,\"PlaceBirth\":null,\"Nationality\":null,\"MotherName\":null,\"FatherName\":null,\"HouseNumber\":null,\"Profession\":null,\"IssueDate\":\"00FECA0000\",\"BarcodeText\":\"\",\"OcrTextSideOne\":\"\",\"OcrTextSideTwo\":\"\",\"SideOneWrongAttempts\":0,\"SideTwoWrongAttempts\":0,\"FoundOnAdoAlert\":false,\"AdoProjectId\":\"127\",\"TransactionId\":\"360\",\"ProductId\":\"1\",\"ComparationFacesSuccesful\":true,\"FaceFound\":true,\"FaceDocumentFrontFound\":false,\"BarcodeFound\":true,\"ResultComparationFaces\":0,\"ComparationFacesAproved\":false,\"Extras\":{\"IdState\":\"2\",\"StateName\":\"Persona registrada previamente\"},\"NumberPhone\":\"\",\"CodFingerprint\":\"\",\"ResultQRCode\":\"\",\"DactilarCode\":null,\"ControlListMatch\":[],\"Images\":null,\"Scores\":null}", CloseResponse.class);
                        closeResponse.setIdNumber(String.valueOf(DocumentTypeUtils.getNumberId(ScanovateApp.getAppContext())));
                        ScanovateSdk.getHandler().onSuccess(closeResponse, 200, closeResponse.getUid());
                        finish();
                    }else {
                        CloseResponse closeResponse = new Gson().fromJson("{\"Uid\":\"b23086ff-dd2b-4f8c-9e4a-8e69c9cb023f\",\"StartingDate\":\"2020-03-09T20:29:10.819378-05:00\",\"CreationDate\":\"2020-03-10T08:32:13.0187583-05:00\",\"CreationIP\":\"198.143.41.3\",\"DocumentType\":1,\"IdNumber\":\"1000336704\",\"FirstName\":\"MARLON\",\"SecondName\":\"ANDRES\",\"FirstSurname\":\"MARIN\",\"SecondSurname\":\"BELTRAN\",\"Gender\":\"\",\"BirthDate\":null,\"Street\":null,\"CedulateCondition\":null,\"Spouse\":null,\"Home\":null,\"MaritalStatus\":null,\"DateOfIdentification\":null,\"DateOfDeath\":null,\"MarriageDate\":null,\"Instruction\":null,\"PlaceBirth\":null,\"Nationality\":null,\"MotherName\":null,\"FatherName\":null,\"HouseNumber\":null,\"Profession\":null,\"IssueDate\":\"00FECA0000\",\"BarcodeText\":\"\",\"OcrTextSideOne\":\"\",\"OcrTextSideTwo\":\"\",\"SideOneWrongAttempts\":0,\"SideTwoWrongAttempts\":0,\"FoundOnAdoAlert\":false,\"AdoProjectId\":\"127\",\"TransactionId\":\"360\",\"ProductId\":\"1\",\"ComparationFacesSuccesful\":true,\"FaceFound\":true,\"FaceDocumentFrontFound\":false,\"BarcodeFound\":true,\"ResultComparationFaces\":0,\"ComparationFacesAproved\":false,\"Extras\":{\"IdState\":\"2\",\"StateName\":\"Proceso satisfactorio\"},\"NumberPhone\":\"\",\"CodFingerprint\":\"\",\"ResultQRCode\":\"\",\"DactilarCode\":null,\"ControlListMatch\":[],\"Images\":null,\"Scores\":null}", CloseResponse.class);
                        closeResponse.setIdNumber(String.valueOf(generateRandomDigits(10)));
                        ScanovateSdk.getHandler().onSuccess(closeResponse, 200, closeResponse.getUid());
                        finish();
                    }
                }
            }
        });
    }
    private static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

    private static boolean isEmulator() {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator");
    }


//    public void capture(View v) {
//        //dismissDialog();
//        //if (checkPermission()) {
//        //  URL url;
//        // try {
//        //   url = new URL(DocumentTypeUtils.getUrlServicesLiveness(this));
//        //} catch (MalformedURLException e) {
//        //  e.printStackTrace();
//        // return;
//        //}
//
//        //new Log().execute("Info", "CaptureLiveness", " start capture liveness", "" , DocumentTypeUtils.getUuiddevice(this), Integer.toString(CountLiveness));
//        //new SNLiveness
//        //      .Builder(this, url, DocumentTypeUtils.getUuiddevice(this)+"-"+CountLiveness)
//        //    .showLevelerUI(DocumentTypeUtils.getLevelerUIValue(this))
//        //  .start();
//        // }
//
//
//        if (checkPermission()) {
//            URL url;
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//            boolean secureComm = false;
//            boolean toastData = false;
//            boolean devMode = false;
//            try {
//                url = new URL(DocumentTypeUtils.getUrlServicesLiveness(this));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return;
//            }
//
//            SNLivenessUICustomization uiCustomization = new SNLivenessUICustomization();
//            uiCustomization.setDisplayBackCamera(false);
//            uiCustomization.setInstructionsPosition(SNLivenessUICustomization.SNPosition.BOTTOM);
//            uiCustomization.setDirectionSignShape(SNLivenessUICustomization.SNDirectionSignShape.ARROW);
//
//            int backArrowColorResource = uiCustomization.getBackArrowColor(); //Flecha superior izquierda oara devolcer al activity anteror
//            int underlineColorResource = Color.parseColor("#00000000"); //Línea debajo del texto
//            int loaderColorResource = Color.parseColor("#92C83D"); //Loader vacio post captura
//            int successSignBackgroundColor = Color.parseColor("#92C83D"); //Loader o reloj durante la captura del liveness
//            int successSignColor = Color.parseColor("#92C83D");;// Visto bueno dentro del loader de confirmación de captura satifactoria delliveness
//            int directingArrowsColor = Color.parseColor("#92C83D");;
//            uiCustomization.setInstructionsUnderlineColor(underlineColorResource);
//            uiCustomization.setLoaderColor(loaderColorResource);
//            uiCustomization.setBackArrowColor(backArrowColorResource);
//            uiCustomization.setDirectionSignColor(directingArrowsColor);
//            uiCustomization.setSuccessSignColor(successSignColor);
//            uiCustomization.setSuccessSignBackgroundColor(successSignBackgroundColor);
//            uiCustomization.setInstructionsFont(new SNLivenessUICustomization.SNFont(getCustomizationsFont(sharedPreferences), uiCustomization.getInstructionsFont().getTextSize()));
//
//            uiCustomization.setLookLeftText("Gira tu cara hacia la izquierda sin salirte del recuadro.");
//            uiCustomization.setLookRightText("Gira tu cara hacia la derecha sin salirte del recuadro.");
//            uiCustomization.setLookAtCenterText("No muevas tu celular hasta que se rellene el círculo amarillo del centro");
//            uiCustomization.setInitialAlignFaceText("¡No te vemos! Ubica tu cara dentro del ovalo");
//            uiCustomization.setOngoingAlignFaceText("¡No te vemos! Ubica tu cara dentro del ovalo");
//            uiCustomization.setMultipleFacesFoundText("Solo debe haber un rostro en el ovalo. ¡El tuyo!");
//            uiCustomization.setGetFurtherText("Estás muy cerca. Aléjate un poco");
//            uiCustomization.setComeCloserText("Estás muy lejos. Acércate un poco");
//            uiCustomization.setProcessingDataText("Espera un momento");
//            uiCustomization.setSessionEndedSuccessfullyText("Espera un momento");
//            setBackArrowShape(uiCustomization, sharedPreferences);
//            setInstructionsViewPosition(uiCustomization, sharedPreferences);
//            setDirectionArrowShape(uiCustomization, sharedPreferences);
//            setCustomizationsSide(uiCustomization, sharedPreferences);
//            SNLivenessUICustomization.SNPosition leo = uiCustomization.getInstructionsPosition();
//            new Log().execute("Info", "CaptureLiveness", " start capture liveness", "" , DocumentTypeUtils.getUuiddevice(this), Integer.toString(CountLiveness));
//            new SNLiveness.Builder(this, url, DocumentTypeUtils.getUuiddevice(this)+"-"+CountLiveness)
//                    .saveLogsToFile(true)
//                    .devMode(devMode)
//                    .uiCustomization(uiCustomization)
//                    .start()
//            ;
//        }
//    }



    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // ask permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, CAMERA_REQUEST_CODE);
            return false;
        }
        return true;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//
//        switch (requestCode) {
//            case Permission.PERMISSION_CAMERA: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    new Log().execute("Info", "onRequestPermissionsResult", " Front camera access granted", "",DocumentTypeUtils.getUuiddevice(this), Integer.toString(CountLiveness));
//                    capture(null);
//
//                } else {
//                    // permission denied
//                    new Log().execute("Warning", "onRequestPermissionsResult", " Front camera access not granted","", DocumentTypeUtils.getUuiddevice(this), Integer.toString(CountLiveness));
//                    //Util.showDialog(this, "Denied permission.Please give camera permission to proceed.");
//                }
//
//            }
//
//            case Permission.PERMISSION_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    new Log().execute("Info", "onRequestPermissionsResult", "Location access granted", "",DocumentTypeUtils.getUuiddevice(this), Integer.toString(CountLiveness));
//                } else {
//                    // permission denied
//                    new Log().execute("Warning", "onRequestPermissionsResult", "Location access not granted", "",DocumentTypeUtils.getUuiddevice(this), Integer.toString(CountLiveness));
//                    Util.showDialog(this, "Denied permission.Please give location permission to proceed.");
//                }
//            }
//        }
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 4948) {
//
//            //SNLivenessActivity.SNCancellationRationale cancellationRationale = (SNLivenessActivity.SNCancellationRationale) Objects.requireNonNull(data).getSerializableExtra(SNLivenessActivity.kSNLIVENESS_CANCELLATION_RATIONALE);
//
//            if (resultCode == RESULT_OK && data != null) {
//                new Log().execute("Info", "CaptureLiveness", " liveness finish successful", "", DocumentTypeUtils.getUuiddevice(this), Integer.toString(CountLiveness));
//                showLivenessSuccessResults(data);
//            } else {
//                SNLivenessActivity.SNCancellationRationale cancellationRationale = (SNLivenessActivity.SNCancellationRationale) Objects.requireNonNull(data).getSerializableExtra(SNLivenessActivity.kSNLIVENESS_CANCELLATION_RATIONALE);
//                new Log().execute("Error", "CaptureLiveness", " Error capture liveness :" + cancellationRationale.toString(), "", DocumentTypeUtils.getUuiddevice(this), Integer.toString(CountLiveness));
//                if (cancellationRationale.toString().equals("SNCancellationRationaleConnectionError")) {
//                    showPopupConnection();
//                } else {
//                    Integer var = DocumentTypeUtils.getTryLiveness(this);
//                    if (CountLiveness < DocumentTypeUtils.getTryLiveness(this) && cancellationRationale.toString() != "SNCancellationRationaleUserCanceled") {
//                        CountLiveness = CountLiveness + 1;
//                        showCountAttemptsFailed();
//                        //capture(null);
//                    } else {
//                        if (cancellationRationale.toString() == "SNCancellationRationaleUserCanceled") {
//                            //Acá que debemos presentar al usuario cuando cancela el Liveness, un popup que confirme el cierre de SDK?
//                        }
//                        else{
//                            showPopupMaxAttemptsFailed();
//                            finish();
//                        }
//                    }
//                }
//            }
//        } else if (requestCode == DocActivity.REQUEST_CODE) {
//            finish();
//        }
//
//
//        if (data != null) {
//            if (resultCode == RESULT_OK) {
//                showLivenessSuccessResults(data);
//            } else if (resultCode == RESULT_CANCELED) {
//                SNLivenessActivity.SNCancellationRationale cancellationRationale = (SNLivenessActivity.SNCancellationRationale) data.getSerializableExtra(SNLivenessActivity.kSNLIVENESS_CANCELLATION_RATIONALE);
//
//                @Nullable String optionalErrorDescription = data.getStringExtra(SNLivenessActivity.kSNLIVENESS_OPTIONAL_ERROR_DESCRIPTION);
//                String errorDescriptionForPresentation = "";
//                if (optionalErrorDescription != null && !optionalErrorDescription.isEmpty()) {
//                    errorDescriptionForPresentation = "\n" + optionalErrorDescription;
//                }
//
//                List<SNDisplayableResult> scanResults = new ArrayList<>();
//                if (cancellationRationale != null) {
//                    scanResults.add(new SNDisplayableResult("Cancellation Rationale", cancellationRationale.toString() + " " + errorDescriptionForPresentation));
//                    //showResultsDialog("Session Aborted", new Pair<Bitmap, List<SNDisplayableResult>>(null, scanResults));
//                    //Toast.makeText(this, cancellationRationale.toString() + " " + errorDescriptionForPresentation, Toast.LENGTH_LONG).show();
//                }
//
//            }
//        } else {
//            //android.util.Log.e(TAG, "scan result - Result data is null");
//        }
//
//    }


//    private void showLivenessSuccessResults(Intent data) {
//        byte[] faceByteArray = data.getByteArrayExtra(SNLivenessActivity.kSNLIVENESS_FACE_IMAGE_BYTE_ARRAY);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(faceByteArray, 0, faceByteArray.length);
//        Resources res = getResources();
//        tv_tittle.setText("Validación de tu identidad");
//        firstButton.setVisibility(View.INVISIBLE);
//        tv_cardType.setVisibility(View.VISIBLE);
//        dots.setVisibility(View.INVISIBLE);
//        nextBtn.setVisibility(View.VISIBLE);
//        nextBtn.setText("Confirmar foto");
//        //message.setVisibility(View.INVISIBLE);
//        camera.setVisibility(View.INVISIBLE);
//        //title.setVisibility(View.INVISIBLE);
//        annotation.setVisibility(View.INVISIBLE);
//        //subTitle.setVisibility(View.VISIBLE);
//        faceIb.setVisibility(View.VISIBLE);
//        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res , bitmap);
//        dr.setCornerRadius(4.0f);
//        faceIb.setImageDrawable(dr);
//        retake.setVisibility(View.VISIBLE);
//        statusTv.setText("");
//        //subtractFace.setVisibility(View.VISIBLE);
//        retake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                capture(null);
//            }
//        });
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                retake.setVisibility(View.INVISIBLE);
//                //subTitle.setVisibility(View.INVISIBLE);
//                nextBtn.setVisibility(View.INVISIBLE);
//                title.setVisibility(View.VISIBLE);
//                title.setTextColor(Color.WHITE);
//                //message.setVisibility(View.INVISIBLE);
//                title.setText("Ya casi terminamos...");
//                if (DocumentTypeUtils.getVerification(ScanovateApp.getAppContext())){
//                    presenter.verificationCustomer(DocumentTypeUtils.getDocumentType(ScanovateApp.getAppContext()),DocumentTypeUtils.getNumberId(ScanovateApp.getAppContext()),bitmap,null, latitude == 0.0 ? "" : String.valueOf(latitude), longitude == 0.0 ? "" : String.valueOf(longitude));
//                }else
//                    presenter.onLivenessSuccess(bitmap, latitude == 0.0 ? "" : String.valueOf(latitude), longitude == 0.0 ? "" : String.valueOf(longitude),uuidDevice);
//            }
//        });
//
//    }

    @Override
    public void onWebServiceSuccess() {

    }

    @Override
    public void onWebServiceFailed(String error, int statuscode) {

    }

    @Override
    public void onWebServiceStart() {

    }

    @Override
    public void finishFlow(boolean result, CloseResponse response, Integer code) {

    }

    @Override
    public void continueFlow(boolean result, CloseResponse response, int code) {

    }

    @Override
    public void setPresenter(FaceContract.Presenter presenter) {

    }

    @Override
    public void onNoConnection() {

    }
}
