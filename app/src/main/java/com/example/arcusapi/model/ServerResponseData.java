package com.example.arcusapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServerResponseData {

    @SerializedName("billers")
    @Expose
    private ArrayList<DataBillerModel> carsArray = null;

    public ArrayList<DataBillerModel> getCarsArray() {
        return carsArray;
    }

    public void setCarsArray(ArrayList<DataBillerModel> carsArray) {
        this.carsArray = carsArray;
    }
}
