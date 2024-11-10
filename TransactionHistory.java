package stock_portfolio_application;

/*
 ** Class Name: IFT210
 ** Author: Stephen Carey
 ** Date Created: 10/4/24
 ** Purpose: Final Course Project
 */

public class TransactionHistory
{
    /*
      * String ticker - Will store ticker of the Stock or CASH
      * String transDate - Date when the transaction occurred
      * String transType - Type of transaction BUY/SELL for stock, DEPOSIT/WITHDRAW for CASH
      * double qty - Quantity for the transaction
      * double costBasis - Cost Basis of stock. For CASH this will be 1.00    
    */
    private String ticker;
    private String transDate;
    private String transType;
    private double qty;
    private double costBasis;
    
    public TransactionHistory() {
        ticker = "";
        transDate = "";
        transType = "";
        qty = 0.0;
        costBasis = 0.0;
    }
    
    public TransactionHistory(String tick, String date, String type, double quantity, double basis) {
        ticker = tick;
        transDate = date;
        transType = type;
        qty = quantity;
        costBasis = basis;
    }
    

    public void setTicker(String tick) {
        ticker = tick;
    }
            
    public String getTicker() {
        return ticker;
    }
    
    public void setTransDate(String date) {
        transDate = date;
    }
            
    public String getTransDate() {
        return transDate;
    }
    
    public void setTransType(String type) {
        transType = type;
    }
            
    public String getTransType() {
        return transType;
    }
    
    public void setQty(double quant) {
        qty = quant;
    }
            
    public double getQty() {
        return qty;
    }
    
    public void setCostBasis(double basis) {
        costBasis = basis;
    }
            
    public double getCostBasis() {
        return costBasis;
    }
    
    
    
}
