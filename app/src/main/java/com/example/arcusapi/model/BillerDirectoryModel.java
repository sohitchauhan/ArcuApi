package com.example.arcusapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BillerDirectoryModel {

    @SerializedName("rpps_billers")
    @Expose
    private ArrayList<BillerDirectModel> billsModels = null;

    public ArrayList<BillerDirectModel> getBillsModels() {

        return billsModels;
    }


}
