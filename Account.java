package assignment6;

import java.io.Serializable;

public class Account implements Serializable {
    String accountName;
    double balance = 0;

    public String getAccountName(){
        return accountName;
    }
    public double getBalance(){
        return balance;
    }
    public void setAccountName(String name){
        accountName = name;
    }
}
