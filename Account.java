package assignment6;

import java.io.Serializable;

public class Account implements Serializable {
    String accountName;
    double balance = 0;
    double totalServiceCharge = 0;

    public String getAccountName(){
        return accountName;
    }
    public double getBalance(){
        return balance;
    }
    public void setAccountName(String name){
        accountName = name;
    }
    public Account(String name, double balance){
        accountName = name;
        this.balance = balance;
    }
    public Account(){}
    public String listTrans(){return accountName;}
    public String listChecks(){return accountName;}
    public String listDeposits(){return accountName;}
    public String listServiceCharges(){return accountName;}
    protected void enterTrans(){}
    public double getTotalServiceCharge(){return totalServiceCharge;}
}
