package assignment4;

public class Check extends Transaction{
    int checkNumber;

    Check(int transNumber, int transID, double transAmt, int checkNumber){
        super(transNumber, transID, transAmt);
        this.checkNumber = checkNumber;
    };
}
