package com.example.arcusapi.service;

import com.example.arcusapi.model.AccountInfoModel;
import com.example.arcusapi.model.BillDetailModel;
import com.example.arcusapi.model.BillResponseData;
import com.example.arcusapi.model.BillerDirectoryModel;
import com.example.arcusapi.model.CreateBillModel;
import com.example.arcusapi.model.ServerResponseData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {


    String BASEURL = "https://apix.staging.arcusapi.com/";

    @GET("account")
    Call<AccountInfoModel> getaccountinfo(@Header("accept") String accept_value , @Header("Authorization") String authorization,
                                          @Header("Content-MD5") String contentmd5, @Header("X-Date") String date, @Header("Content-Type") String type);


    @GET("data_billers")
    Call<ServerResponseData> getdatabillers(@Header("accept") String accept_value , @Header("Authorization") String authorization,
                                            @Header("Content-MD5") String contentmd5, @Header("X-Date") String date, @Header("Content-Type") String type);


    @GET("bills")
    Call<BillResponseData> getBill(@Header("accept") String accept_value , @Header("Authorization") String authorization,
                                          @Header("Content-MD5") String contentmd5, @Header("X-Date") String date, @Header("Content-Type") String type);

    @GET("bills/{id}")
    Call<BillDetailModel> getBillDetail(@Path ("id") String id , @Header("accept") String accept_value , @Header("Authorization") String authorization,
                                  @Header("Content-MD5") String contentmd5, @Header("X-Date") String date, @Header("Content-Type") String type);




    @POST("bills")
    Call<CreateBillModel> createBillmodel(@Body String jsonObject, @Header("accept") String accept_value , @Header("Authorization") String authorization,
                                          @Header("Content-MD5") String contentmd5, @Header("X-Date") String date, @Header("Content-Type") String type);



    @GET("biller_directory")
    Call<BillerDirectoryModel> getbillerdirectory(@Header("accept") String accept_value , @Header("Authorization") String authorization,
                                       @Header("Content-MD5") String contentmd5, @Header("X-Date") String date, @Header("Content-Type") String type);

}
