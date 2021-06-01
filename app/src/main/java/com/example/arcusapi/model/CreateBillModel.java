package com.example.arcusapi.model;

public class CreateBillModel {

    String message;

    public CreateBillModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
