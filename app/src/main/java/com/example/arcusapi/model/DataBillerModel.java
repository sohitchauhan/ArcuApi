package com.example.arcusapi.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataBillerModel  {



    private String id;


    private String uuid;

    private String bill_type;


    private String name;



    private String country;


    private String currency;

    private String biller_type;

    public DataBillerModel(String id, String uuid, String bill_type, String name, String country, String currency, String biller_type) {
        this.id = id;
        this.uuid = uuid;
        this.bill_type = bill_type;
        this.name = name;
        this.country = country;
        this.currency = currency;
        this.biller_type = biller_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return bill_type;
    }

    public void setType(String type) {
        this.bill_type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBiller_type() {
        return biller_type;
    }

    public void setBiller_type(String biller_type) {
        this.biller_type = biller_type;
    }
}
