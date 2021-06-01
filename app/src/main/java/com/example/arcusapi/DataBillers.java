package com.example.arcusapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arcusapi.model.AccountInfoModel;
import com.example.arcusapi.model.BillDetailModel;
import com.example.arcusapi.model.CreateBillModel;
import com.example.arcusapi.model.DataBillerModel;
import com.example.arcusapi.model.ServerResponseData;
import com.example.arcusapi.service.ApiClient;
import com.example.arcusapi.service.ArcusKey;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBillers extends AppCompatActivity {
    String CURRENT_DATE="";
    String datee = "";
 String dateeff = "";
    ShimmerFrameLayout my_account_shimmerLayout;
    RecyclerView mainLayout;
    String checksum="";

    LinearLayout toolbar_title_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_billers);
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

    }
    private void cal_account_info_api() {
        my_account_shimmerLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        my_account_shimmerLayout.startShimmerAnimation();
        ApiClient.getClient().getdatabillers("application/vnd.regalii.v3.2+json",
                "APIAuth "+ArcusKey.api_key+checksum,"",datee,"application/json")
                .enqueue(new Callback<ServerResponseData>() {
            @Override
            public void onResponse(Call<ServerResponseData> call, retrofit2.Response<ServerResponseData> response) {
                my_account_shimmerLayout.stopShimmerAnimation();
                mainLayout.setVisibility(View.VISIBLE);
                my_account_shimmerLayout.setVisibility(View.GONE);
                Log.d("checkarrayt", "success"+String.valueOf(response.body()));
          ServerResponseData serverResponseData = response.body();

          if (serverResponseData != null){

              ArrayList<DataBillerModel>arrayList=serverResponseData.getCarsArray();

             Log.d("checkarrayt", String.valueOf(arrayList));
              for (int i = 0; i <arrayList.size(); i++) {

                  RecyclerViewAdapter   recyclerViewAdapter = new RecyclerViewAdapter(arrayList, DataBillers.this);

                  // below line is to set layout manager for our recycler view.
                  LinearLayoutManager manager = new LinearLayoutManager(DataBillers.this);

                  // setting layout manager for our recycler view.
                  mainLayout.setLayoutManager(manager);

                  // below line is to set adapter to our recycler view.
                  mainLayout.setAdapter(recyclerViewAdapter);
              }
          }



            }

            @Override
            public void onFailure(Call<ServerResponseData> call, Throwable t) {
                Log.d("checkarrayt", String.valueOf(t.getMessage()));
            }
        });
    }

    //recyclerview
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

        // creating a variable for our array list and context.
        private ArrayList<DataBillerModel> DataArrayList;
        private Context mcontext;

        // creating a constructor class.
        public RecyclerViewAdapter(ArrayList<DataBillerModel> recyclerDataArrayList, Context mcontext) {
            this.DataArrayList = recyclerDataArrayList;
            this.mcontext = mcontext;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate Layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_billers_recycler, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            // Set the data to textview from our modal class.
            DataBillerModel modal = DataArrayList.get(position);
            holder.datatype.setText(modal.getType());
            holder.data_name.setText(modal.getName());
            holder.data_billertype.setText(modal.getBiller_type());
            holder.data_id.setText(modal.getId());


            holder.createbill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
getCurrentGMT1(modal.getUuid());



                }
            });

        }

        @Override
        public int getItemCount() {
            // this method returns the size of recyclerview
            return DataArrayList.size();
        }

        // View Holder Class to handle Recycler View.
        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            // creating variables for our views.
            private TextView datatype, data_name, data_billertype,data_id;

            Button createbill;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our views with their ids.
                datatype = itemView.findViewById(R.id.datatype);
                data_name = itemView.findViewById(R.id.data_name);
                data_billertype = itemView.findViewById(R.id.data_billertype);
                data_id = itemView.findViewById(R.id.data_id);
                createbill=itemView.findViewById(R.id.createbill);
            }
        }
    }
    //create bill checksum

    private void getCurrentGMT1(String biller_id) {

        Date now = new Date();
        TimeZone.setDefault( TimeZone.getTimeZone("GMT"));
        getChangeformat1(now,biller_id);
    }
    //GMT format chasnge according to ARCUS
    private void getChangeformat1(Date now,String biller_id) {
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
        CURRENT_DATE="application/json,,/bills,"+outputFormat.format(d);
        try {
            //calculate checksum acccording to ARCUS
            computeHmac1(CURRENT_DATE, ArcusKey.secret,biller_id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    //calculate checksum
    public String computeHmac1(String baseString, String key,String biller_id)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException,  UnsupportedEncodingException
    {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), mac.getAlgorithm());
        mac.init(secret);
        byte[] digest = mac.doFinal(baseString.getBytes());
        byte[] result= Base64.encodeBase64(digest);

        checksum =new String(result);

        cal_create_bill_api(biller_id);
        Log.d("checktimee", new String(result));
        return new String(result);
    }

    private void cal_create_bill_api(final  String biller_id) {

        JSONObject paramObject = new JSONObject();
        try {

            paramObject.put("login", "developer@paypase.com");
            paramObject.put("password", "1234");
            paramObject.put("biller_id", biller_id);
            paramObject.put("external_user_id", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiClient.getClient().createBillmodel(paramObject.toString(),"application/vnd.regalii.v3.2+json",
                "APIAuth " + ArcusKey.api_key + checksum, "", datee, "application/json")
                .enqueue(new Callback<CreateBillModel>() {
                             @Override
                             public void onResponse(Call<CreateBillModel> call, retrofit2.Response<CreateBillModel> response) {

                                 Log.d("checkpostvalyue", "success"+response.toString()+" json"+paramObject);
CreateBillModel createBillModel = response.body();
                                 Log.d("checkpostvalyue", "successmessage"+createBillModel.getMessage());


                             }

                             @Override
                             public void onFailure(Call<CreateBillModel> call, Throwable t) {
                                 Log.d("checkpostvalyue", String.valueOf(t.getMessage()));
                             }
                });
    }


    //
    //
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
        CURRENT_DATE="application/json,,/data_billers,"+outputFormat.format(d);
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
        byte[] result= Base64.encodeBase64(digest);

        checksum =new String(result);

        cal_account_info_api();
        Log.d("checktimee", new String(result));
        return new String(result);
    }
}