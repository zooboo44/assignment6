package assignment6;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Main {
    public static final String PATTERN1 = "$#,##0.00;($#,##0.00)";
    public static JTextArea ta;
    public static void main(String[] args){
        int again = 0, transactionCode;
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern(PATTERN1);
        CheckingAccount checking = new CheckingAccount(df);
        Menu menu = new Menu(df);
        do {
        } while (again != JOptionPane.NO_OPTION || again != JOptionPane.CANCEL_OPTION);
    }
}