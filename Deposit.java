package assignment6;

import java.io.Serializable;

public class Deposit extends Transaction implements Serializable {
    Deposit(int transNumber, int transID, double transAmt){
        super(transNumber, transID, transAmt);
    }
}
