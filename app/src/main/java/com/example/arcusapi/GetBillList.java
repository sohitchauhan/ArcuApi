package com.example.arcusapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arcusapi.model.BillResponseData;
import com.example.arcusapi.model.DataBillerModel;
import com.example.arcusapi.model.GetBillsModel;
import com.example.arcusapi.model.ServerResponseData;
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

public class GetBillList extends AppCompatActivity {
    String CURRENT_DATE="";
    String datee = "";
    ShimmerFrameLayout my_account_shimmerLayout;
    RecyclerView mainLayout;
    String checksum="";
    LinearLayout toolbar_title_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_bill_list);
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
        ApiClient.getClient().getBill("application/vnd.regalii.v3.2+json",
                "APIAuth " + ArcusKey.api_key + checksum, "", datee, "application/json")
                .enqueue(new Callback<BillResponseData>() {
                    @Override
                    public void onResponse(Call<BillResponseData> call, retrofit2.Response<BillResponseData> response) {
                        my_account_shimmerLayout.stopShimmerAnimation();
                        mainLayout.setVisibility(View.VISIBLE);
                        my_account_shimmerLayout.setVisibility(View.GONE);
                        BillResponseData serverResponseData = response.body();

                        if (serverResponseData != null) {

                            ArrayList<GetBillsModel> arrayList = serverResponseData.getBillsModels();
                            for (int i = 0; i < arrayList.size(); i++) {
RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(arrayList, GetBillList.this);

                                // below line is to set layout manager for our recycler view.
                                LinearLayoutManager manager = new LinearLayoutManager(GetBillList.this);

                                // setting layout manager for our recycler view.
                                mainLayout.setLayoutManager(manager);

                                // below line is to set adapter to our recycler view.
                                mainLayout.setAdapter(recyclerViewAdapter);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<BillResponseData> call, Throwable t) {
                        Log.d("checkarrayt", String.valueOf(t.getMessage()));
                    }
                });
    }

    //recyclerview
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

        // creating a variable for our array list and context.
        private ArrayList<GetBillsModel> DataArrayList;
        private Context mcontext;

        // creating a constructor class.
        public RecyclerViewAdapter(ArrayList<GetBillsModel> recyclerDataArrayList, Context mcontext) {
            this.DataArrayList = recyclerDataArrayList;
            this.mcontext = mcontext;
        }

        @NonNull
        @Override
        public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate Layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_recycler, parent, false);
            return new RecyclerViewAdapter.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
            // Set the data to textview from our modal class.
            GetBillsModel modal = DataArrayList.get(position);
            holder.balance.setText(modal.getBalance());
            holder.data_name.setText(modal.getName_on_account());
            holder.data_account.setText(modal.getAccount_number());
            holder.data_id.setText(modal.getId());
            holder.duedate.setText(modal.getDue_date());

            holder.bill_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(),GetBillDetails.class);
                    intent.putExtra("bill_id",modal.getUuid());
                    startActivity(intent);
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
            private TextView balance, data_name, data_account, data_id,duedate;

            private Button bill_details;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our views with their ids.
                balance = itemView.findViewById(R.id.balance);
                bill_details=itemView.findViewById(R.id.bill_details);
                data_name = itemView.findViewById(R.id.data_name);
                data_account = itemView.findViewById(R.id.data_account);
                data_id = itemView.findViewById(R.id.data_id);
                duedate=itemView.findViewById(R.id.duedate);
            }
        }
    }

    //
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
        Log.d("checktimee", String.valueOf(outputFormat.format(d)));
        datee = outputFormat.format(d);
        CURRENT_DATE = "application/json,,/bills," + outputFormat.format(d);
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

        cal_account_info_api();
        Log.d("checktimee", new String(result));
        return new String(result);
    }
}