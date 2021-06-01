package com.example.arcusapi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arcusapi.model.AccountInfoModel;
import com.example.arcusapi.service.ApiClient;
import com.example.arcusapi.service.ArcusKey;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;

public class AccountInformation extends AppCompatActivity {
    String CURRENT_DATE="";
    String datee = "";
    ShimmerFrameLayout my_account_shimmerLayout;
    CardView mainLayout;
    String checksum="";
    LinearLayout toolbar_title_back;
    TextView accountName,accountBalance,accountminiBalance,accountcurrency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        toolbar_title_back = findViewById(R.id.toolbar_title_back);

        toolbar_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           onBackPressed();
            }
        });

        getIdSet();

        //getCurrent GMT
        getCurrentGMT();
        my_account_shimmerLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        my_account_shimmerLayout.startShimmerAnimation();
    }

    private void getIdSet() {
        mainLayout = findViewById(R.id.mainLayout);
        my_account_shimmerLayout = findViewById(R.id.my_account_shimmerLayout);
        accountName=findViewById(R.id.accountName);
        accountBalance=findViewById(R.id.accountBalance);
        accountminiBalance=findViewById(R.id.accountminiBalance);
        accountcurrency=findViewById(R.id.accountcurrency);
    }
///account info api

    private void cal_account_info_api() {
        my_account_shimmerLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        my_account_shimmerLayout.startShimmerAnimation();
        ApiClient.getClient().getaccountinfo("application/vnd.regalii.v3.2+json",
                "APIAuth "+ArcusKey.api_key+checksum,"",datee,"application/json").enqueue(new Callback<AccountInfoModel>() {
            @Override
            public void onResponse(Call<AccountInfoModel> call, retrofit2.Response<AccountInfoModel> response) {
                my_account_shimmerLayout.stopShimmerAnimation();
                mainLayout.setVisibility(View.VISIBLE);
                my_account_shimmerLayout.setVisibility(View.GONE);
                AccountInfoModel information = response.body();
                Log.d("cherckresponse","success "+information.getBalance()+"__"+information.getName());
                accountName.setText(""+information.getName());
                accountBalance.setText(""+information.getBalance());
                accountminiBalance.setText(""+information.getMinimum_balance());
                accountcurrency.setText(""+information.getCurrency());
            }

            @Override
            public void onFailure(Call<AccountInfoModel> call, Throwable t) {

            }
        });
    }
    //GMT date
    private void getCurrentGMT() {

        Date now = new Date();
        TimeZone.setDefault( TimeZone.getTimeZone("GMT"));
        getChangeformat(now);
    }
    //GMT format chasnge according to ARCUS
    private void getChangeformat(Date now) {
        DateFormat inputFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy");
        Date d = null;
        try {
            d = inputFormat.parse(String.valueOf(now));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat outputFormat = new SimpleDateFormat("EE, dd MMM yyy HH:mm:ss zz");
        Log.d("checktimee", String.valueOf(outputFormat.format(d)));
        datee=outputFormat.format(d);
        CURRENT_DATE="application/json,,/account,"+outputFormat.format(d);
        try {
            //calculate checksum acccording to ARCUS
            computeHmac(CURRENT_DATE, ArcusKey.secret);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
//calculate checksum
    public String computeHmac(String baseString, String key)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException,  UnsupportedEncodingException
    {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), mac.getAlgorithm());
        mac.init(secret);
        byte[] digest = mac.doFinal(baseString.getBytes());
        byte[] result=Base64.encodeBase64(digest);

        checksum =new String(result);

        cal_account_info_api();
        Log.d("checktimee", new String(result));
        return new String(result);
    }
}