package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.*;
import dev.team4.portfoliotracker.services.NewsService;
import dev.team4.portfoliotracker.services.PortfolioService;
import dev.team4.portfoliotracker.services.TransactionService;
import dev.team4.portfoliotracker.services.UserDetailsService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
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

    @Autowired
    NewsService newsService;

//    @Autowired
//    StockServiceImpl stockService;

    public int sendUpdateNews(){
//        //This only works for David DB
//        List<News> newsList = newsService.getNewsList();
//        if(newsList.size() >0){
//            News news = newsList.get(newsList.size()-1)
//        }

        //Testing data
        final News news = new News(1,"Facebook Ranked Among Today's Trending Stocks","Q.ai runs daily factor models to get the most up-to-date reading on stocks and ETFs. Today, our deep-learning algorithms have identified Facebook among others.","https://www.forbes.com/sites/qai/2021/08/09/facebook-ranked-among-todays-trending-stocks/", "https://thumbor.forbes.com/thumbor/fit-in/1200x0/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F61112c3daa5906c61c419c4e%2F0x0.png");
        final News news2 = new News(2,"test Title","test Description","Test URL", "Test IMG URL");


        List<User> users = userDetailsService.checkAllUser();
        int count = 0;
        for(User user: users) {
            EmailUtil.sendEmailAboutNews(user, news);
            count += 1;
        }
        return count;
    }

    public List<Stock> convertTransactionsToStock(List<Transaction> transactions){
        List<Stock> stocks = new ArrayList<>();
        for(Transaction transaction: transactions){
            System.out.println(transaction);
            boolean isExisted = false;
            for(int i =0; i<stocks.size(); i++){
                if(stocks.get(i).getSymbol().equals(transaction.getStockSymbol())){
                    isExisted = true;
                    double quantity = stocks.get(i).getQuantity() + transaction.getTransactionQuantity();
                    System.out.println(quantity);
                    System.out.println(stocks);
                    if(quantity > 0){
                        // new method: setQuantityNormal
                        stocks.get(i).setQuantityNormal(quantity);
                    }else{
                        stocks.remove(i);
                    }
                }
            }
            if(isExisted == false){
                Stock newStock = new Stock(transaction);
                newStock.setQuantityNormal(transaction.getTransactionQuantity());
                stocks.add(newStock);
            }
        }
        return stocks;
    }

    public int sendUpdateStockInfo(){
        System.out.println("sending email about portfolio update");
        int count = 0;
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
                    EmailUtil.sendEmailAboutPortfolio(user, stocks, balance, priceMap, portfolio.getName());
                    count += 1;
                }
            }
        }//end users loop
        return count;
    }

    public int notifyPriceChange(Double bound, int intervalInSec) {
        int count = 0;
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
                    if(abs(change) >= bound) {
                        String subject = "User: " + user.getUsername() + ". One of your stock has significant price change";
                        String text = "\nStock Name: " + yahooStock.getName() + "\nChange In Percent: " + change + "\nCurrent Price: " + price;
                        text += "\nYou currently have " + stock.getQuantity() + " shares";

                        long currentTimestamp = System.currentTimeMillis();
                        System.out.println(currentTimestamp);
                        if(user.getLastEmailEpochTime() != null){
                            if( (currentTimestamp - user.getLastEmailEpochTime()) > intervalInSec) { //the number is for interval
                                EmailUtil.sendEmail(user.getEmail(), subject, text);
                                count += 1;
                            }
                        }else{ // when user.getLastEmailEpochTime() is  null
                            EmailUtil.sendEmail(user.getEmail(), subject, text);
                            count += 1;
                        }
                        user.setLastEmailEpochTime(currentTimestamp);
                        userDetailsService.createUser(user);
                    }
                }
            }
        }
        return count;
    }
}


