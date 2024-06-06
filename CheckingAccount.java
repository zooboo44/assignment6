package assignment6;

import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CheckingAccount extends Account implements Serializable{
    //Constructor
    CheckingAccount(DecimalFormat df){
        this.df = df;
    }
    CheckingAccount(String name, Double amt, DecimalFormat df){
        this.accountName = name;
        this.balance = amt;
        this.df = df;
    }
    //Variables
    private boolean first500 = true;
    private String summary = "";
    private final DecimalFormat df;
    public ArrayList<Transaction> transList = new ArrayList<>();

    //Get
    @Override
    public double getTotalServiceCharge(){
        return totalServiceCharge;
    }
    public String getSummary(){
        summary = summary.concat("Name: " + getAccountName()) + "\n";
        summary = summary.concat("Total balance: " + df.format(getBalance()) + "\n");
        summary = summary.concat("Total service charge amount: " + df.format(getTotalServiceCharge()) + "\n");
        if(balance < 50){
            summary = summary.concat("Warning, balance below $50" + "\n");
        }
        return summary;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void addToTotalServiceCharge(double ServiceCharge) {
        this.totalServiceCharge += ServiceCharge;
    }
    public void withdraw(int transNumber, double amount, int checkNumber){
        transList.add(new Check(transNumber, Transaction.getNextCount(), amount, checkNumber));
        balance -= amount;
        transList.add(new Transaction(4, Transaction.getNextCount(), 0.15));
        addToTotalServiceCharge(0.15);
        summary = summary.concat("Transaction: Check #" + checkNumber + " in Amount of: " + df.format(amount) + "\n");
        summary = summary.concat("Service Charge: Check --- charge " + df.format(0.15) + "\n");
        if(balance < 500 && first500){
            transList.add(new Transaction(4, Transaction.getNextCount(), 5));
            addToTotalServiceCharge(5);
            summary = summary.concat("Service Charge: Below $500 --- charge " + df.format(5.00) + "\n");
            first500 = false;
        }
        if(balance < 0){
            transList.add(new Transaction(4, Transaction.getNextCount(), 10));
            addToTotalServiceCharge(10.0);
            summary = summary.concat("Service Charge: Below $0 --- charge " + df.format(10.0)) + "\n";
        }
    }
    public void deposit(int transNumber, double cashAmount, double checkAmount){
        if(cashAmount > 0){
            transList.add(new Deposit(transNumber, Transaction.getNextCount(), cashAmount));
            transList.add(new Transaction(4, Transaction.getNextCount(), 0.10));
            addToTotalServiceCharge(0.10);
        }
        if(checkAmount > 0){
            transList.add(new Deposit(transNumber, Transaction.getNextCount(), checkAmount));
            transList.add(new Transaction(4, Transaction.getNextCount(), 0.10));
            addToTotalServiceCharge(0.10);
        }
        double totalDepositAmount = cashAmount + checkAmount;
        balance += totalDepositAmount;
        summary = summary.concat("Transaction: Deposit in Amount of " + df.format(totalDepositAmount) + "\n");
        summary = summary.concat("Total Balance: " + balance + "\n");
        summary = summary.concat("Service Charge: Check " + df.format(0.10)) + "\n";
    }
    @Override
    public void enterTrans(){
        int transCode = Integer.parseInt(JOptionPane.showInputDialog("Enter Transaction Number: "));
        if(transCode >= 0 && transCode <= 2){
            switch(transCode){
                case 0:
                    endTransaction();
                    JOptionPane.showMessageDialog(null, getSummary());
                    clearSummary();
                    break;
                case 1:
                    int checkNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter Check Number"));
                    withdraw(transCode, Double.parseDouble(JOptionPane.showInputDialog("Enter Check Amount")), checkNumber);
                    JOptionPane.showMessageDialog(null, getSummary());
                    clearSummary();
                    break;
                case 2:
                    JTextField cashField = new JTextField(10);
                    JTextField checkField = new JTextField(10);
                    JPanel newPanel = new JPanel();
                    newPanel.add(new JLabel("Cash Amount: "));
                    newPanel.add(cashField);
                    newPanel.add(Box.createVerticalStrut(15));
                    newPanel.add(new JLabel("Check Amount: "));
                    newPanel.add(checkField);
                    JOptionPane.showConfirmDialog(null, newPanel, "Enter deposit amount", JOptionPane.OK_CANCEL_OPTION);
                    deposit(transCode, Double.parseDouble(cashField.getText()), Double.parseDouble(checkField.getText()));
                    JOptionPane.showMessageDialog(null, getSummary());
                    clearSummary();
                    break;
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Invalid Transaction Code");
        }
    }
    public void clearSummary(){
        summary = "";
    }
    public void subTotalServiceCharge(){
        balance -= totalServiceCharge;
    }
    public void endTransaction(){
        subTotalServiceCharge();
        summary = summary.concat("Transaction: End" + "\n");
    }
    public ArrayList getTransList(){
        return transList;
    }

    @Override
    public void setAccountName(String name) {
        super.setAccountName(name);
        summary = "";
    }

    public String listTrans(){
        String message = "List all Transactions:\n" + getSummary() + "ID\t Type\t Amount\n";
        for (Transaction transaction : transList) {
            message = message.concat(transaction.getTransId() + "    ");
            if(transaction.getTransNumber() == 1){
                message = message.concat("Check    ");
            }
            if(transaction.getTransNumber() == 2){
                message = message.concat("Deposit    ");
            }
            if(transaction.getTransNumber() == 4){
                message = message.concat("Svc. Crg.    ");
            }
            message = message.concat(df.format(transaction.getTransAmt()) + "\n");
        }
        return message;
    }

    public String listChecks(){
        String message = "List all Checks \n" + getSummary();
        for(Transaction transaction : transList){
            if(transaction.getTransNumber() == 1){
                message = message.concat(transaction.getTransId() + "    " + df.format(transaction.getTransAmt()));
            }
        }
        return message;
    }

    public String listDeposits(){
        String message ="List all Deposits \n" + getSummary();
        for (Transaction transaction : transList) {
            if (transaction.getTransNumber() == 2) {
                message = message.concat(transaction.getTransId() + "    " + df.format(transaction.getTransAmt()));
            }
        }
        return message;
    }

    public String listServiceCharges(){
        String message = "List all Service Charges \n" + getSummary();
        for (Transaction transaction : transList) {
            if (transaction.getTransNumber() == 4) {
                message = message.concat(transaction.getTransId() + "    " + df.format(transaction.getTransAmt()));
            }
        }
        return message;
    }

}
