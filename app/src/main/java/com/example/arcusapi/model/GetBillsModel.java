package com.example.arcusapi.model;

public class GetBillsModel {

    String id,uuid,biller_id,biller_uuid,account_number,name_on_account,due_date,balance,status;

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

    public String getBiller_id() {
        return biller_id;
    }

    public void setBiller_id(String biller_id) {
        this.biller_id = biller_id;
    }

    public String getBiller_uuid() {
        return biller_uuid;
    }

    public void setBiller_uuid(String biller_uuid) {
        this.biller_uuid = biller_uuid;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getName_on_account() {
        return name_on_account;
    }

    public void setName_on_account(String name_on_account) {
        this.name_on_account = name_on_account;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetBillsModel(String id, String uuid, String biller_id, String biller_uuid, String account_number, String name_on_account, String due_date, String balance, String status) {
        this.id = id;
        this.uuid = uuid;
        this.biller_id = biller_id;
        this.biller_uuid = biller_uuid;
        this.account_number = account_number;
        this.name_on_account = name_on_account;
        this.due_date = due_date;
        this.balance = balance;
        this.status = status;
    }
}
