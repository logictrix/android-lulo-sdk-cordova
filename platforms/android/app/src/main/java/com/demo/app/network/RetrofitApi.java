package com.demo.app.network;

import com.demo.app.model.CloseRegistroRequest;
import com.demo.app.model.CloseRequest;
import com.demo.app.model.CloseResponse;
import com.demo.app.model.DocRequest;
import com.demo.app.model.DocRequestFront;
import com.demo.app.model.FaceRequest;
import com.demo.app.model.GetConfigResponse;
import com.demo.app.model.LogRequest;
import com.demo.app.model.LogResponse;
import com.demo.app.model.ScoreResponse;
import com.demo.app.model.UserAuthenticationRequest;
import com.demo.app.model.UserAuthenticationResponse;
import com.demo.app.model.VerificationIdentityRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {

    @POST("New")
    Call<CloseResponse> postFace(@Body FaceRequest request);

    @POST("Images/DocumentFrontSide")
    Call<ResponseBody> postDocFront(@Body DocRequestFront request);

    @POST("Images/DocumentBackSide")
    Call<CloseResponse> postDocBack(@Body DocRequest request);

    @POST("Close")
    Call<CloseResponse> close(@Body CloseRequest request);

    @GET("Validation/{txId}")
    Call<ScoreResponse> validateTransaction(@Path("txId") String var2, @Query("returnImages") Boolean var3);


    @POST("RegisterLog")
    Call<LogResponse> RegisterLog(@Body LogRequest request);

    @POST("CloseRegistro")
    Call<CloseResponse> CloseRegistro(@Body CloseRegistroRequest request);

    @POST("UserAuthentication")
    Call<UserAuthenticationResponse> UserAuthentication(@Body UserAuthenticationRequest request);

    @POST("GetConfig")
    Call<GetConfigResponse> GetConfig(@Query("Message") String request);

    @POST("CustomerVerification")
    Call<CloseResponse> customerVerification(@Body VerificationIdentityRequest request);


}