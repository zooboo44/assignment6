package assignment6;

import java.io.Serializable;

public class Check extends Transaction implements Serializable {
    int checkNumber;

    Check(int transNumber, int transID, double transAmt, int checkNumber){
        super(transNumber, transID, transAmt);
        this.checkNumber = checkNumber;
    };
}
