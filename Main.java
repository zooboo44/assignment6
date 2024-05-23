package assignment6;

import javax.swing.*;
import java.text.DecimalFormat;

public class Main {
    public static final String PATTERN1 = "$#,##0.00;($#,##0.00)";
    public static void main(String[] args){
        int again = 0, transactionCode;
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern(PATTERN1);
        CheckingAccount checking = new CheckingAccount(df);
        MyFrame transButtons = new MyFrame(checking, df);
        do {
            if(checking.getIsFirstTimeAccount()){
                checking.setAccountName(JOptionPane.showInputDialog("Enter Account Name: "));
                checking.setBalance(Double.parseDouble(JOptionPane.showInputDialog("Enter Initial Balance: ")));
            }
            transButtons.setVisible(true);
        } while (again != JOptionPane.NO_OPTION || again != JOptionPane.CANCEL_OPTION);
    }
}