package assignment4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyFrame extends JFrame implements ActionListener {

    JRadioButton enterNewTransaction;
    JRadioButton listAllTransactions;
    JRadioButton listAllChecks;
    JRadioButton listAllDeposits;
    CheckingAccount acct;

    DecimalFormat df;

    public MyFrame(CheckingAccount acct, DecimalFormat df){
        this.acct = acct;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.df = df;

        enterNewTransaction = new JRadioButton("Enter Transaction Number");
        listAllTransactions = new JRadioButton("List All Transactions");
        listAllChecks = new JRadioButton("List All Checks");
        listAllDeposits = new JRadioButton("List All Deposits");

        ButtonGroup group = new ButtonGroup();
        group.add(enterNewTransaction);
        group.add(listAllTransactions);
        group.add(listAllChecks);
        group.add(listAllDeposits);

        this.add(enterNewTransaction);
        this.add(listAllTransactions);
        this.add(listAllChecks);
        this.add(listAllDeposits);

        enterNewTransaction.addActionListener(this);
        listAllTransactions.addActionListener(this);
        listAllChecks.addActionListener(this);
        listAllDeposits.addActionListener(this);


        this.pack();
        this.setVisible(false);
        setPreferredSize(new Dimension(400,400));
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == enterNewTransaction){
            this.setVisible(false);
            int transCode = Integer.parseInt(JOptionPane.showInputDialog("Enter Transaction Number: "));
            if(transCode >= 0 && transCode <= 2){
                switch(transCode){
                    case 0:
                        acct.endTransaction();
                        JOptionPane.showMessageDialog(null, acct.getSummary());
                        acct.clearSummary();
                        break;
                    case 1:
                        this.setVisible(false);
                        int checkNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter Check Number"));
                        acct.withdraw(transCode, Double.parseDouble(JOptionPane.showInputDialog("Enter Check Amount")), checkNumber);
                        JOptionPane.showMessageDialog(null, acct.getSummary());
                        acct.clearSummary();
                        break;
                    case 2:
                        this.setVisible(false);
                        acct.deposit(transCode, Double.parseDouble(JOptionPane.showInputDialog("Enter Deposit Amount: ")));
                        JOptionPane.showMessageDialog(null, acct.getSummary());
                        acct.clearSummary();
                        this.setVisible(true);
                        break;
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Invalid Transaction Code");
            }
        }
        if(e.getSource() == listAllTransactions){
            ArrayList<Transaction> temp = acct.getTransList();
            String message = "List all Transactions:\n" + acct.getAccountName() + "'s Account \n" + "ID\t Type\t Amount\n";
            for (Transaction transaction : temp) {
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
            JOptionPane.showMessageDialog(null, message);
        }
        if(e.getSource() == listAllChecks){
            ArrayList<Transaction> temp = acct.getTransList();
            String message = acct.getAccountName() + "\n List all Checks \n";
            for(Transaction transaction : temp){
                if(transaction.getTransNumber() == 1){
                    message = message.concat(transaction.getTransId() + "    " + df.format(transaction.getTransAmt()));
                }
            }
            JOptionPane.showMessageDialog(null, message);
        }
        if(e.getSource() == listAllDeposits) {
            ArrayList<Transaction> temp = acct.getTransList();
            String message = acct.getAccountName() + "\n List all Deposits \n";
            for (Transaction transaction : temp) {
                if (transaction.getTransNumber() == 2) {
                    message = message.concat(transaction.getTransId() + "    " + df.format(transaction.getTransAmt()));
                }
            }
            JOptionPane.showMessageDialog(null, message);
        }
    }
}
