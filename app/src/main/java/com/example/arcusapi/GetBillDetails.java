package com.example.arcusapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arcusapi.model.BillDetailModel;
import com.example.arcusapi.model.BillResponseData;
import com.example.arcusapi.model.GetBillsModel;
import com.example.arcusapi.service.ApiClient;
import com.example.arcusapi.service.ArcusKey;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;

public class GetBillDetails extends AppCompatActivity {
    String CURRENT_DATE="";
    private TextView balance, data_name, data_account, data_id,duedate;

    String datee = "";
    ShimmerFrameLayout my_account_shimmerLayout;
    CardView mainLayout;
    String checksum="";
    LinearLayout toolbar_title_back;
    String bill_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bill_details);

        toolbar_title_back = findViewById(R.id.toolbar_title_back);

        toolbar_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle !=null){
            bill_id=bundle.getString("bill_id");


            Log.d("checkid",bill_id);
        }

        getIdSet();
        //getCurrent GMT
        getCurrentGMT();
        my_account_shimmerLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        my_account_shimmerLayout.startShimmerAnimation();
    }
    private void getIdSet() {
        mainLayout = findViewById(R.id.cardLayout);
        my_account_shimmerLayout = findViewById(R.id.my_account_shimmerLayout);

        balance = findViewById(R.id.balance);
        data_name = findViewById(R.id.data_name);
        data_account =findViewById(R.id.data_account);
        data_id = findViewById(R.id.data_id);
        duedate=findViewById(R.id.duedate);


    }

    private void cal_billdetail_info_api() {
        my_account_shimmerLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        my_account_shimmerLayout.startShimmerAnimation();
        ApiClient.getClient().getBillDetail(bill_id,"application/vnd.regalii.v3.2+json",
                "APIAuth " + ArcusKey.api_key + checksum, "", datee, "application/json")
                .enqueue(new Callback<BillDetailModel>() {
                    @Override
                    public void onResponse(Call<BillDetailModel> call, retrofit2.Response<BillDetailModel> response) {
                        my_account_shimmerLayout.stopShimmerAnimation();
                        mainLayout.setVisibility(View.VISIBLE);
                        my_account_shimmerLayout.setVisibility(View.GONE);
                        BillDetailModel billDetailModel = response.body();
                  balance.setText(""+billDetailModel.getBalance());
                data_name.setText(""+billDetailModel.getName_on_account());
                      data_account.setText(""+billDetailModel.getAccount_number());
                       data_id.setText(""+billDetailModel.getId());
                       duedate.setText(""+billDetailModel.getDue_date());


                    }

                    @Override
                    public void onFailure(Call<BillDetailModel> call, Throwable t) {
                        Log.d("checkarrayt", String.valueOf(t.getMessage()));
                    }
                });
    }

    //GMT date
    private void getCurrentGMT() {

        Date now = new Date();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
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
        datee = outputFormat.format(d);
        CURRENT_DATE = "application/json,,/bills/"+bill_id+"," + outputFormat.format(d);
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
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), mac.getAlgorithm());
        mac.init(secret);
        byte[] digest = mac.doFinal(baseString.getBytes());
        byte[] result = Base64.encodeBase64(digest);

        checksum = new String(result);

       cal_billdetail_info_api();
        return new String(result);
    }

}