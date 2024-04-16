package assignment4;

public class Transaction {
    private int transNumber;
    private int transId = 0;
    private double transAmt;
    private static int count = 0;
    Transaction(int transNumber, int transId, double transAmt){
        this.transNumber = transNumber;
        this.transId = transId;
        this.transAmt = transAmt;
    }
    public double getTransAmt() {
        return transAmt;
    }
    public int getTransId() {
        return transId;
    }
    public int getTransNumber() {
        return transNumber;
    }
    public static int getNextCount(){
        if(Transaction.count == 0){
            Transaction.count++;
            return 0;
        }
        Transaction.count ++;
        return Transaction.count-1;
    }

    public void setTransAmt(double transAmt) {
        this.transAmt = transAmt;
    }
    public void setTransId(int transId) {
        this.transId = transId;
    }
    public void setTransNumber(int transNumber) {
        this.transNumber = transNumber;
    }

    @Override
    public String toString() {
        String message = "";
        return message = message.concat(transId + "     " + transNumber + "     " + transAmt);
    }
}
