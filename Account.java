package assignment4;

public class Account {
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
