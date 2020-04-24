package com.demo.app.UI;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.app.R;
import com.demo.app.ScanovateApp;
import com.demo.app.ScanovateSdk;
import com.demo.app.UI.base.BaseActivity;
import com.demo.app.model.CloseResponse;
import com.google.gson.Gson;

public class MaxAttemptFailed extends BaseActivity {
    public static final int REQUEST_CODE = 20;
    private static Context context;
    Button nextScreen;
    public static void setContext(Context context) {
        MaxAttemptFailed.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_attempt_failed);
        context = this;
        initViews();

    }

    void initViews() {
        nextScreen = findViewById(R.id.btn_siguiente);
        nextScreen.setOnClickListener(v -> {
            CloseResponse closeResponse = new Gson().fromJson("{" +
                            "\"Uid\":\"b23086ff-dd2b-4f8c-9e4a-8e69c9cb023f\"," +
                            "\"StartingDate\":\"2020-03-09T20:29:10.819378-05:00\"," +
                            "\"CreationDate\":\"2020-03-10T08:32:13.0187583-05:00\"," +
                            "\"CreationIP\":\"198.143.41.3\"," +
                            "\"DocumentType\":null," +
                            "\"IdNumber\":null," +
                            "\"FirstName\":null," +
                            "\"SecondName\":null," +
                            "\"FirstSurname\":null," +
                            "\"SecondSurname\":null," +
                            "\"Gender\":null," +
                            "\"BirthDate\":null," +
                            "\"Street\":null," +
                            "\"CedulateCondition\":null," +
                            "\"Spouse\":null,\"Home\":null," +
                            "\"MaritalStatus\":null," +
                            "\"DateOfIdentification\":null," +
                            "\"DateOfDeath\":null," +
                            "\"MarriageDate\":null," +
                            "\"Instruction\":null," +
                            "\"PlaceBirth\":null," +
                            "\"Nationality\":null," +
                            "\"MotherName\":null," +
                            "\"FatherName\":null," +
                            "\"HouseNumber\":null," +
                            "\"Profession\":null," +
                            "\"IssueDate\":null," +
                            "\"BarcodeText\":\"\"," +
                            "\"OcrTextSideOne\":\"\"," +
                            "\"OcrTextSideTwo\":\"\"," +
                            "\"SideOneWrongAttempts\":0," +
                            "\"SideTwoWrongAttempts\":0," +
                            "\"FoundOnAdoAlert\":false," +
                            "\"AdoProjectId\":\"0\"," +
                            "\"TransactionId\":\"0\"," +
                            "\"ProductId\":\"1\"," +
                            "\"ComparationFacesSuccesful\":true," +
                            "\"FaceFound\":true," +
                            "\"FaceDocumentFrontFound\":false," +
                            "\"BarcodeFound\":true," +
                            "\"ResultComparationFaces\":0," +
                            "\"ComparationFacesAproved\":false," +
                            "\"Extras\":{\"IdState\":\"15\"," +
                            "\"StateName\":\"Error\"}," +
                            "\"NumberPhone\":\"\"," +
                            "\"CodFingerprint\":\"\"," +
                            "\"ResultQRCode\":\"\"," +
                            "\"DactilarCode\":null," +
                            "\"ControlListMatch\":[]," +
                            "\"Images\":null," +
                            "\"Scores\":null}",
                    CloseResponse.class);
            ScanovateSdk.getHandler().onSuccess(closeResponse, 200, closeResponse.getUid());
            finish();
        });
    }
}