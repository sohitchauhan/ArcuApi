package com.example.arcusapi.model;

public class BillDetailModel {

    String id, type,uuid, biller_id, biller_uuid, account_number, name_on_account, due_date, balance, balance_currency, balance_updated_at,
    status, migrated_at, external_user_id;

    public BillDetailModel(String id, String type, String uuid, String biller_id, String biller_uuid, String account_number, String name_on_account, String due_date, String balance, String balance_currency, String balance_updated_at, String status, String migrated_at, String external_user_id) {
        this.id = id;
        this.type = type;
        this.uuid = uuid;
        this.biller_id = biller_id;
        this.biller_uuid = biller_uuid;
        this.account_number = account_number;
        this.name_on_account = name_on_account;
        this.due_date = due_date;
        this.balance = balance;
        this.balance_currency = balance_currency;
        this.balance_updated_at = balance_updated_at;
        this.status = status;
        this.migrated_at = migrated_at;
        this.external_user_id = external_user_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getBalance_currency() {
        return balance_currency;
    }

    public void setBalance_currency(String balance_currency) {
        this.balance_currency = balance_currency;
    }

    public String getBalance_updated_at() {
        return balance_updated_at;
    }

    public void setBalance_updated_at(String balance_updated_at) {
        this.balance_updated_at = balance_updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMigrated_at() {
        return migrated_at;
    }

    public void setMigrated_at(String migrated_at) {
        this.migrated_at = migrated_at;
    }

    public String getExternal_user_id() {
        return external_user_id;
    }

    public void setExternal_user_id(String external_user_id) {
        this.external_user_id = external_user_id;
    }
}
