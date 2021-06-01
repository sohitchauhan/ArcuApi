package com.example.arcusapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BillResponseData {

    @SerializedName("bills")
    @Expose
    private ArrayList<GetBillsModel> billsModels = null;

    public ArrayList<GetBillsModel> getBillsModels() {

        return billsModels;
    }


}
