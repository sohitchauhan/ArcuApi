package com.example.arcusapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BillerDirectModel {

    String id,name,biller_type,biller_class;

    @SerializedName("addresses")
    @Expose
    private ArrayList<BillerAddressList> billsModels = null;
    public BillerDirectModel(String id, String name, String biller_type, String biller_class,ArrayList<BillerAddressList>  billerAddressList) {
        this.id = id;
        this.name = name;
        this.biller_type = biller_type;
        this.biller_class = biller_class;
        this.billsModels=billerAddressList;

    }

    public ArrayList<BillerAddressList> getBillsModels() {
        return billsModels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiller_type() {
        return biller_type;
    }

    public void setBiller_type(String biller_type) {
        this.biller_type = biller_type;
    }

    public String getBiller_class() {
        return biller_class;
    }

    public void setBiller_class(String biller_class) {
        this.biller_class = biller_class;
    }


    public class BillerAddressList {
        String city, address1, state, postal_code, address_type;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostal_code() {
            return postal_code;
        }

        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        public String getAddress_type() {
            return address_type;
        }

        public void setAddress_type(String address_type) {
            this.address_type = address_type;
        }

        public BillerAddressList(String city, String address1, String state, String postal_code, String address_type) {
            this.city = city;
            this.address1 = address1;
            this.state = state;
            this.postal_code = postal_code;
            this.address_type = address_type;
        }
    }
}
