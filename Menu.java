package assignment6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Menu extends JFrame implements ActionListener {
    JMenuBar mb;
    JMenu file, accounts, transactions;
    JMenuItem openFile, saveFile, newAcct, listTrans, listChecks, listDeposits, listServiceCharges, findAcct, listAcct, enterTrans;
    CheckingAccount acct;
    DecimalFormat df;
    public static JTextArea ta;
    ArrayList<CheckingAccount> allAccounts = new ArrayList<>();
    boolean saved = false;
    String filePath = "C:\\Users\\ziobr\\Documents\\file.dat";

    public Menu(DecimalFormat df){
        this.acct = null;
        this.df = df;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        ta = new JTextArea(10,50);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        this.getContentPane().add(ta);
        this.pack();
        this.setVisible(true);


        mb = new JMenuBar();
        this.setJMenuBar(mb);

        file = new JMenu("File");
        accounts = new JMenu("Accounts");
        transactions = new JMenu("Transactions");

        openFile = new JMenuItem("Open File");
        saveFile = new JMenuItem("Save File");
        newAcct = new JMenuItem("Create new Account");
        listTrans = new JMenuItem("List all Transactions");
        listChecks = new JMenuItem("List all Check");
        listDeposits = new JMenuItem("List all Deposits");
        listServiceCharges = new JMenuItem("List all Service Charges");
        findAcct = new JMenuItem("Find Account");
        listAcct = new JMenuItem("List all Accounts");
        enterTrans = new JMenuItem("Enter Transaction");

        mb.add(file);
        mb.add(accounts);
        mb.add(transactions);
        file.add(openFile);
        file.add(saveFile);
        accounts.add(newAcct);
        accounts.add(listTrans);
        accounts.add(listChecks);
        accounts.add(listDeposits);
        accounts.add(listServiceCharges);
        accounts.add(findAcct);
        accounts.add(listAcct);
        transactions.add(enterTrans);

        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        newAcct.addActionListener(this);
        listTrans.addActionListener(this);
        listChecks.addActionListener(this);
        listDeposits.addActionListener(this);
        listServiceCharges.addActionListener(this);
        findAcct.addActionListener(this);
        listAcct.addActionListener(this);
        enterTrans.addActionListener(this);
        this.setVisible(true);

        FrameListener listener = new FrameListener();
        addWindowListener(listener);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == findAcct){
            String message = "";
            clearTextBox();
            String selectedAcctname = JOptionPane.showInputDialog(null, "Enter Account Name");
            for(CheckingAccount temp : allAccounts) {
                if (temp.getAccountName().equals(selectedAcctname)) {
                    message = message.concat("Account found for " + temp.getAccountName() + "\n");
                    acct = temp;
                    ta.setText(message);
                    return;
                }
            }
            message = message.concat("Account not found for " + selectedAcctname + "\n");
            ta.setText(message);
            return;
        }
        if(e.getSource() == listAcct){
            clearTextBox();
            String message = "All accounts: " + "\n" + "\n";
            for(Account temp : allAccounts){
                message = message.concat(temp.getAccountName() + "\n" + "Balance: " + temp.getBalance() + "\n" + "Total Service Charge: " + temp.getTotalServiceCharge() + "\n" + "\n");
            }
            ta.setText(message);
            return;
        }
        if(e.getSource() == newAcct){
            String message = "New account added for ";
            String name = JOptionPane.showInputDialog("Enter name: ");
            CheckingAccount newAcct = new CheckingAccount(name, Double.parseDouble(JOptionPane.showInputDialog("Enter Amount")), df);
            allAccounts.add(newAcct);
            message = message.concat(name);
            ta.setText(message);
            acct = newAcct;
            System.out.println("test");
            return;
        }
        if(e.getSource() == openFile){
            clearTextBox();
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
                allAccounts = (ArrayList<CheckingAccount>) in.readObject();
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
            return;
        }
        if(e.getSource() == saveFile){
            clearTextBox();
            chooseFile(1);
            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(allAccounts);
                out.close();

            }
            catch (IOException error){
                System.out.println(error);
            }
            return;
        }
        if(acct == null){
            JOptionPane.showMessageDialog(null, "Must select an existing account or create a new one");
            return;
        }
        if(e.getSource() == listTrans){
            clearTextBox();
            ta.setText(acct.listTrans());
            acct.clearSummary();
        }
        if(e.getSource() == listChecks){
            clearTextBox();
           ta.setText(acct.listChecks());
           acct.clearSummary();
        }
        if(e.getSource() == listDeposits){
            clearTextBox();
            ta.setText(acct.listDeposits());
            acct.clearSummary();
        }
        if(e.getSource() == listServiceCharges) {
            ta.setText(acct.listServiceCharges());
            acct.clearSummary();
        }
        if(e.getSource() == enterTrans){
            acct.enterTrans();
            acct.clearSummary();
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
    public void clearTextBox(){
        ta.setText("");
    }
    public void selectAcct(CheckingAccount acct){
        this.acct = acct;
    }

    private class FrameListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int confirm;
            if (!saved) {
                String message = "The data in the application is not saved.\n" +
                        "Would you like to save it before exiting the application?";
                confirm = JOptionPane.showConfirmDialog(null, message);
                if (confirm == JOptionPane.YES_OPTION)
                    chooseFile(2);
            }
            System.exit(0);
        }
    }
}
