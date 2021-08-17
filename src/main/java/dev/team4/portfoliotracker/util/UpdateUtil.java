package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.PortfolioService;
import dev.team4.portfoliotracker.services.TransactionService;
import dev.team4.portfoliotracker.services.UserDetailsService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Scheduled;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

@Component
public class UpdateUtil {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    PortfolioService portfolioService;

//    @Autowired
//    StockServiceImpl stockService;

    public List<Stock> convertTransactionsToStock(List<Transaction> transactions){
        List<Stock> stocks = new ArrayList<>();
        for(Transaction transaction: transactions){
            boolean isExisted = false;
            for(int i =0; i<stocks.size(); i++){
                if(stocks.get(i).getSymbol().equals(transaction.getStockSymbol())){
                    isExisted = true;
                    // new method: setQuantityNormal
                    double quantity = stocks.get(i).getQuantity() + transaction.getTransactionQuantity();
                    if(quantity > 0){
                        stocks.get(i).setQuantityNormal(quantity);
                    }else{
                        stocks.remove(i);
                    }

                }
            }
            if(isExisted == false){
                Stock newStock = new Stock(transaction);
                stocks.add(newStock);
            }
        }
        return stocks;
    }

    public void sendUpdateStockInfo(){
        List<User> users = userDetailsService.checkAllUser();
        for(User user: users){

            List<Portfolio> portfolios = portfolioService.getPortfoliosByUser(user);
            List<Stock> stocks = new ArrayList<>();
            for(Portfolio portfolio: portfolios){
                List<Transaction> transactions = transactionService.getAllTransactionsByPortfolio(portfolio);
                stocks = convertTransactionsToStock(transactions);
                System.out.println(stocks);
                if(stocks.size()>0) {
                    String[] symbols = new String[stocks.size()];
                    for (int i = 0; i < stocks.size(); i++) {
                        symbols[i] = stocks.get(i).getSymbol();
                    }
                    Map<String, yahoofinance.Stock> yahooStocks = null;
                    try {
                        yahooStocks = YahooFinance.get(symbols);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    HashMap<String, Double> priceMap = new HashMap<>();
                    double balance = 0;
                    for (int i = 0; i < stocks.size(); i++) {
                        yahoofinance.Stock yahooStock = yahooStocks.get(stocks.get(i).getSymbol());
                        double price = yahooStock.getQuote().getPrice().doubleValue();
                        balance += price * stocks.get(i).getQuantity();
                        priceMap.put(stocks.get(i).getSymbol(), price);

                    }
                    System.out.println(priceMap);
                    EmailUtil.sendEmailAboutStock(user, stocks, balance, priceMap, portfolio.getName());
                }


            }



        }//end users loop
    }

    public void notifyPriceChange() {
        List<User> users = userDetailsService.checkAllUser();
        for(User user: users){

            List<Portfolio> portfolios = portfolioService.getPortfoliosByUser(user);
            List<Stock> stocks = new ArrayList<>();
            for(Portfolio portfolio: portfolios){
                List<Transaction> transactions = transactionService.getAllTransactionsByPortfolio(portfolio);
                stocks = convertTransactionsToStock(transactions);

                for (Stock stock : stocks) {
                    yahoofinance.Stock yahooStock = null;
                    try {
                        yahooStock = YahooFinance.get(stock.getSymbol());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    double price = yahooStock.getQuote().getPrice().doubleValue();
                    double change = yahooStock.getQuote().getChangeInPercent().doubleValue();

                    System.out.println(change);
                    if(abs(change) >= 0.5) {
                        String subject = "User: " + user.getUsername() + ". One of your stock has significant price change";
                        String text = "\nStock Name: " + yahooStock.getName() + "\nChange In Percent: " + change + "\nCurrent Price: " + price;
                        text += "\nYou currently have " + stock.getQuantity() + " shares";

                        long currentTimestamp = System.currentTimeMillis();
                        System.out.println(currentTimestamp);
                        if(user.getLastEmailEpochTime() != null){
                            if( (currentTimestamp - user.getLastEmailEpochTime()) > 86400) { //the number is for interval
                                EmailUtil.sendEmail(user.getEmail(), subject, text);
                            }
                        }else{ // when user.getLastEmailEpochTime() is  null
                            EmailUtil.sendEmail(user.getEmail(), subject, text);
                        }
                        user.setLastEmailEpochTime(currentTimestamp);
                        userDetailsService.createUser(user);


                    }
                }
            }
        }
    }
}

