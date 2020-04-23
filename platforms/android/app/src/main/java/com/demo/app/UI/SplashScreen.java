package com.demo.app.UI;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.demo.app.R;
import com.demo.app.ScanovateHandler;
import com.demo.app.ScanovateSdk;
import com.demo.app.model.CloseResponse;


public class SplashScreen extends AppCompatActivity {
    private static Integer Code;
    private static String Uid;
    private static String UuidDevice;
    private static String TransactionId;
    private static String IdState;
    private static String NameState;
    private static String FirstName;
    private static String SecondName;
    private static String FirstSurname;
    private static String SecondSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        new Handler().postDelayed(() -> ScanovateSdk.start(
                SplashScreen.this,
                "1",
                1,
                false,
                "credibancoqa",
                "db92efc69991",
                "https://adocolumbia.ado-tech.com/credibancoqa/api/",
                "",
                false,
                new ScanovateHandler() {
                    @Override
                    public void onSuccess(CloseResponse response, int code, String uuidDevice) {
                        //CloseResponse myReponse = response;
                        Code = code;
                        Uid = response.getUid();
                        UuidDevice = uuidDevice;
                        IdState = response.getExtras().getIdState();
                        NameState = response.getExtras().getStateName();
                        FirstName = response.getFirstName();
                        SecondName = response.getSecondName();
                        FirstSurname = response.getFirstSurname();
                        SecondSurname = response.getSecondSurname();
                        TransactionId = response.getTransactionId();
                        String responseExtras = response.getExtras().getStateName() + response.getExtras().getIdState();
                    }

                    @Override
                    public void onFailure(CloseResponse response) {
                        //Toast.makeText(SplashScreen.this, "Resultado: failure" + "-- Front" + DataContext.getInstance().getResponseServers("FrontSide") + "Back--" + DataContext.getInstance().getResponseServers("BackSide"), Toast.LENGTH_LONG).show();
                    }

                }), 1500);

    }
}
