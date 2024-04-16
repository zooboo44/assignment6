package assignment4;

public class Checking extends Transaction{
    int checkNumber;

    Checking(int transNumber, int transID, double transAmt, int checkNumber){
        super(transNumber, transID, transAmt);
        this.checkNumber = checkNumber;
    };
}
