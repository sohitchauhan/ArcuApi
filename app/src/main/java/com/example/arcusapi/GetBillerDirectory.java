package com.example.arcusapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.arcusapi.model.BillerDirectModel;
import com.example.arcusapi.model.BillerDirectoryModel;
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

public class GetBillerDirectory extends AppCompatActivity {
    String CURRENT_DATE = "";

    String datee = "";
    ShimmerFrameLayout my_account_shimmerLayout;
    RecyclerView mainLayout;
    String checksum = "";
    LinearLayout toolbar_title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_biller_directory);
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
        ApiClient.getClient().getbillerdirectory("application/vnd.regalii.v3.2+json",
                "APIAuth " + ArcusKey.api_key + checksum, "", datee, "application/json")
                .enqueue(new Callback<BillerDirectoryModel>() {
                    @Override
                    public void onResponse(Call<BillerDirectoryModel> call, retrofit2.Response<BillerDirectoryModel> response) {
                        my_account_shimmerLayout.stopShimmerAnimation();
                        mainLayout.setVisibility(View.VISIBLE);
                        my_account_shimmerLayout.setVisibility(View.GONE);
                        BillerDirectoryModel serverResponseData = response.body();

                        if (serverResponseData != null) {

                            ArrayList<BillerDirectModel> arrayList = serverResponseData.getBillsModels();
                            for (int i = 0; i < arrayList.size(); i++) {
                         RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(arrayList, GetBillerDirectory.this);

                                // below line is to set layout manager for our recycler view.
                                LinearLayoutManager manager = new LinearLayoutManager(GetBillerDirectory.this);

                                // setting layout manager for our recycler view.
                                mainLayout.setLayoutManager(manager);

                                // below line is to set adapter to our recycler view.
                                mainLayout.setAdapter(recyclerViewAdapter);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<BillerDirectoryModel> call, Throwable t) {
                        Log.d("checkarrayt", String.valueOf(t.getMessage()));
                    }
                });
    }

    //recyclerview
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

        // creating a variable for our array list and context.
        private ArrayList<BillerDirectModel> DataArrayList;
        private Context mcontext;

        // creating a constructor class.
        public RecyclerViewAdapter(ArrayList<BillerDirectModel> recyclerDataArrayList, Context mcontext) {
            this.DataArrayList = recyclerDataArrayList;
            this.mcontext = mcontext;
        }

        @NonNull
        @Override
        public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate Layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billerdirectory_recycler, parent, false);
            return new RecyclerViewAdapter.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
            // Set the data to textview from our modal class.
            BillerDirectModel modal = DataArrayList.get(position);
            holder.data_name.setText(modal.getName());
            holder.billclass.setText(modal.getBiller_class());
            holder.billertype.setText(modal.getBiller_type());
            if (modal != null) {

                ArrayList<BillerDirectModel.BillerAddressList> arrayList = modal.getBillsModels();
                for (int i = 0; i < arrayList.size(); i++) {

                    holder.city.setText(""+arrayList.get(i).getCity());
                    holder.statee.setText(""+arrayList.get(i).getState()+" , "+arrayList.get(i).getState());
                }

                }




            holder.bill_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


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
            private TextView statee, data_name, billclass, billertype, city;

            private Button bill_details;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our views with their ids.
                statee = itemView.findViewById(R.id.statee);
                bill_details = itemView.findViewById(R.id.bill_details);
                data_name = itemView.findViewById(R.id.data_name);
                billclass = itemView.findViewById(R.id.billclass);
                billertype = itemView.findViewById(R.id.billertype);
                city = itemView.findViewById(R.id.city);
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
        CURRENT_DATE = "application/json,,/biller_directory," + outputFormat.format(d);
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