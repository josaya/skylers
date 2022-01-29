package com.example.skylers.modules;

public class MemberAccounts {

    String accountName, accountNumber, accountAmount;

    public MemberAccounts(String name, String number, String amount){
        this.accountName    =   name;
        this.accountNumber  =   number;
        this.accountAmount  =   amount;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountAmount() {
        return accountAmount;
    }
}
