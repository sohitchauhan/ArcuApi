package com.example.arcusapi.model;

public class AccountInfoModel {

    String name,balance,minimum_balance,currency;

    public AccountInfoModel(String name, String balance, String minimum_balance, String currency) {
        this.name = name;
        this.balance = balance;
        this.minimum_balance = minimum_balance;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMinimum_balance() {
        return minimum_balance;
    }

    public void setMinimum_balance(String minimum_balance) {
        this.minimum_balance = minimum_balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
