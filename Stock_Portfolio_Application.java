package stock_portfolio_application;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

/*
 ** Class Name: IFT210
 ** Author: Stephen Carey
 ** Date Created: 10/4/24
 ** Purpose: Final Course Project
 */

public class Stock_Portfolio_Application
{
    private static ArrayList<TransactionHistory> portfolioList = new ArrayList<TransactionHistory>();
    private static Scanner scnr = new Scanner(System.in);
    private static double cash;
        
    public static void main(String[] args) {
        int menuNum;
        menuNum = menuInput(scnr);

        while (menuNum != 0) {
            switch (menuNum) {
                case 1:
                    depositCash();
                    break;

                case 2:
                    int canWithdraw = withdrawCash();

                    switch (canWithdraw) {
                        case 0:
                            menuNum = menuInput(scnr);
                            continue;
                        case 1:
                            break;
                    }
                    break;

                case 3:
                    int canBuy = buyStock();

                    switch (canBuy) {
                        case 0:
                            menuNum = menuInput(scnr);
                            continue;
                        case 1:
                            break;
                    }
                    break;

                case 4:
                    int canSell = sellStock();
                    
                    switch (canSell) {
                        case 0:
                            menuNum = menuInput(scnr);
                            continue;
                        case 1:
                            break;
                    }
                    break;

                case 5:
                    showTransactionHistory();
                    break;

                case 6:
                    showPortfolio();
                    break;

                default:
                    System.out.println("ERROR: Invalid input! Enter a number 0-6.\n");
                    break;
            }
            
            menuNum = menuInput(scnr);
        }
        
        System.out.println("\nGoodbye!");
    }
    
    public static int menuInput (Scanner scnr) {
        System.out.println("Stephen Carey\'s Brokerage Account\n" +
                            "\n" +
                            "0 - Exit\n" +
                            "1 - Deposit Cash\n" +
                            "2 - Withdraw Cash\n" +
                            "3 - Buy Stock\n" +
                            "4 - Sell Stock\n" +
                            "5 - Display Transaction History\n" +
                            "6 - Display Portfolio\n" +
                            "\n" +
                            "Enter option (0 to 6):");
        
        int num;
        try {
            num = scnr.nextInt();
            
        }
        catch (InputMismatchException ex) {
            num = 7;
            scnr.next();
        }
        
        return num;
    }
    
    private static void depositCash() {
        System.out.print("Enter deposit date (##/##/####): ");
        String date = scnr.next();
        System.out.print("How much cash would you like to deposit? ");
        double amount = scnr.nextDouble();
        cash += amount;
        TransactionHistory transaction = new TransactionHistory("CASH",date,"DEPOSIT",amount,1.0);
        portfolioList.add(transaction);
                
        System.out.println();
    }
    
    private static int withdrawCash() {
        System.out.print("Enter withdrawal date (##/##/####): ");
        String date = scnr.next();
        System.out.print("How much cash would you like to withdraw? ");
        double amount = scnr.nextDouble();
        if (amount > cash) {
            System.out.println("ERROR: You cannot withdraw more than the amount in your account.");
            return 0;
        }
        cash -= amount;
        TransactionHistory transaction = new TransactionHistory("CASH",date,"WITHDRAW",-amount,1.0);
        portfolioList.add(transaction);
                
        System.out.println();
        return 1;
    }
    
    private static int buyStock() {
        System.out.print("Enter purchase date (##/##/####): ");
        String date = scnr.next();
        System.out.print("Enter stock ticker: ");
        String ticker = scnr.next();
        ticker = ticker.toUpperCase();
        System.out.print("Enter quantity of stock: ");
        double amount = scnr.nextDouble();
        System.out.print("Enter the cost per stock: ");
        double basis = scnr.nextDouble();
                
        if ((amount *  basis)> cash) {
            System.out.println("ERROR: You cannot spend more than you have in your account.");
            return 0;
        }
                
        cash -= (amount*basis);
                
        TransactionHistory transaction = new TransactionHistory(ticker,date,"BUY",amount,basis);
        portfolioList.add(transaction);
                
        transaction = new TransactionHistory("CASH",date,"WITHDRAW",-(amount*basis),1.0);
        portfolioList.add(transaction);
                
        System.out.println();
        return 1;
    }
    
    private static int sellStock() {
        System.out.print("Enter sell date (##/##/####): ");
        String date = scnr.next();
        System.out.print("Enter stock ticker: ");
        String ticker = scnr.next();
        ticker = ticker.toUpperCase();
        System.out.print("Enter quantity of stock: ");
        double amount = scnr.nextDouble();
        System.out.print("Enter the price per stock: ");
        double basis = scnr.nextDouble();
        
        double stockCount = 0.0;
        
        for (int i = 0; i < portfolioList.size(); i++) {
            if (portfolioList.get(i).getTicker().equals(ticker)) {
                if (portfolioList.get(i).getTransType().equals("BUY")) {
                    stockCount += portfolioList.get(i).getQty();
                }
                if (portfolioList.get(i).getTransType().equals("SELL")) {
                    stockCount -= portfolioList.get(i).getQty();
                }
            }
        }
        
        if (stockCount < amount || stockCount == 0.0) {
            System.out.println("\nERROR: You cannot sell stock that you do not own.\n");
            return 0;
        }
            
                
        cash += (amount*basis);
                
        TransactionHistory transaction = new TransactionHistory(ticker,date,"SELL",amount,basis);
        portfolioList.add(transaction);
                
        transaction = new TransactionHistory("CASH",date,"DEPOSIT",(amount*basis),1.0);
        portfolioList.add(transaction);
                
        System.out.println();
        return 1;
    }
    
    private static void showTransactionHistory () {
        System.out.println("\nStephen Carey\'s Brokerage Account");
        System.out.println("=================================\n");
        System.out.printf("%-16s%-10s%8s%15s     %s\n","Date","Ticker","Quantity","Cost Basis","Trans Type");
        System.out.println("=================================================================");
        for (int i = 0; i < portfolioList.size(); i++) {
            String dollarBasis = String.format("$%.2f", portfolioList.get(i).getCostBasis());
            System.out.printf("%-16s%-10s%8.0f%15s     %s\n", portfolioList.get(i).getTransDate(),portfolioList.get(i).getTicker(),
                    portfolioList.get(i).getQty(), dollarBasis, 
                    portfolioList.get(i).getTransType());
        }
        System.out.println();
    }
    
    private static void showPortfolio() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println("\nPortfolio as of: " + dtf.format(now));
        System.out.println("====================================\n");
        System.out.printf("%-10s%s\n","Ticker",  "Quantity");
        System.out.println("================");
        System.out.printf("%-10s%.2f\n","CASH",  cash);
                
        ArrayList<String> activeTickers = new ArrayList<String>();
                
        for (int i = 0; i < portfolioList.size(); i++) {
            if (activeTickers.contains(portfolioList.get(i).getTicker()) == false ) {
                activeTickers.add(portfolioList.get(i).getTicker());
            }
        }
                
        activeTickers.remove("CASH");
                
        for (int j = 0; j < activeTickers.size(); j++) {
            double stockAmount = 0.0;
            
            for (int h = 0; h < portfolioList.size(); h++) {
                if (portfolioList.get(h).getTicker().equals(activeTickers.get(j))) {
                    if (portfolioList.get(h).getTransType().equals("BUY")) {
                        stockAmount += portfolioList.get(h).getQty();
                    }
                    if (portfolioList.get(h).getTransType().equals("SELL")) {
                        stockAmount -= portfolioList.get(h).getQty();
                    }
                }
            }
            
            if (stockAmount != 0.0) {
                System.out.printf("%-10s%.0f\n",activeTickers.get(j),  stockAmount);
            }
        }
                
        System.out.println();
    }
    
}