package assignment6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyFrame extends JFrame implements ActionListener {

    JRadioButton enterNewTransaction;
    JRadioButton listAllTransactions;
    JRadioButton listAllChecks;
    JRadioButton listAllDeposits;
    JRadioButton saveFile;
    JRadioButton openFile;
    CheckingAccount acct;
    String filePath;
    boolean saved;

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
        saveFile = new JRadioButton("Save filesss");
        openFile = new JRadioButton("Open File");

        ButtonGroup group = new ButtonGroup();
        group.add(enterNewTransaction);
        group.add(listAllTransactions);
        group.add(listAllChecks);
        group.add(listAllDeposits);
        group.add(saveFile);
        group.add(openFile);

        this.add(enterNewTransaction);
        this.add(listAllTransactions);
        this.add(listAllChecks);
        this.add(listAllDeposits);
        this.add(saveFile);
        this.add(openFile);

        enterNewTransaction.addActionListener(this);
        listAllTransactions.addActionListener(this);
        listAllChecks.addActionListener(this);
        listAllDeposits.addActionListener(this);
        saveFile.addActionListener(this);
        openFile.addActionListener(this);



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
                        JTextField cashField = new JTextField(10);
                        JTextField checkField = new JTextField(10);
                        JPanel newPanel = new JPanel();
                        newPanel.add(new JLabel("Cash Amount: "));
                        newPanel.add(cashField);
                        newPanel.add(Box.createVerticalStrut(15));
                        newPanel.add(new JLabel("Check Amount: "));
                        newPanel.add(checkField);
                        JOptionPane.showConfirmDialog(null, newPanel, "Enter deposit amount", JOptionPane.OK_CANCEL_OPTION);
                        acct.deposit(transCode, Double.parseDouble(cashField.getText()), Double.parseDouble(checkField.getText()));
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
            String message = "List all Transactions:\n" + "ID\t Type\t Amount\n";
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
            String message = "\n List all Checks \n";
            for(Transaction transaction : temp){
                if(transaction.getTransNumber() == 1){
                    message = message.concat(transaction.getTransId() + "    " + df.format(transaction.getTransAmt()));
                }
            }
            JOptionPane.showMessageDialog(null, message);
        }
        if(e.getSource() == listAllDeposits) {
            ArrayList<Transaction> temp = acct.getTransList();
            String message = "\n List all Deposits \n";
            for (Transaction transaction : temp) {
                if (transaction.getTransNumber() == 2) {
                    message = message.concat(transaction.getTransId() + "    " + df.format(transaction.getTransAmt()));
                }
            }
            JOptionPane.showMessageDialog(null, message);
        }
        if(e.getSource() == saveFile){
            chooseFile(1);
            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(acct);
                out.close();
                saved = true;
            }
            catch (IOException error){
                System.out.println(error);
            }
        }
        if(e.getSource() == openFile){
            int confirm;
            if (!saved)
            {
                String  message = "The data in the application is not saved.\n"+
                        "would you like to save it before reading the new file in?";
                confirm = JOptionPane.showConfirmDialog (null, message);
                if (confirm == JOptionPane.YES_OPTION)
                    chooseFile(2);
            }
            chooseFile(1);
            try
            {
                FileInputStream fis = new
                        FileInputStream(filePath);
                ObjectInputStream in = new
                        ObjectInputStream(fis);
                acct = (CheckingAccount) in.readObject();
                in.close();
                saved = true;
            }
            catch(ClassNotFoundException exception)
            {
                System.out.println(exception);
            }
            catch (IOException exception)
            {
                System.out.println(exception);
            }

        }
    }
    public void chooseFile(int ioOption){
        int status, confirm;

        String  message = "Would you like to use the current default file: \n" +
                filePath;
        confirm = JOptionPane.showConfirmDialog (null, message);
        if (confirm == JOptionPane.YES_OPTION)
            return;
        JFileChooser chooser = new JFileChooser();
        if (ioOption == 1)
            status = chooser.showOpenDialog (null);
        else
            status = chooser.showSaveDialog (null);
        if (status == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            filePath = file.getPath();
        }
    }
}
