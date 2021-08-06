package dev.team4.portfoliotracker.util;

import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.StockService;
import dev.team4.portfoliotracker.services.StockServiceImpl;
import dev.team4.portfoliotracker.services.UserDetailsService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

@Component
public class UpdateUtil {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    StockServiceImpl stockService;

    public void notifyPriceChange() {
        List<Stock> stocks = stockService.getAllStocksForAllUsers();
        for (Stock stock : stocks) {
            yahoofinance.Stock yahooStock = null;
            try {
                yahooStock = YahooFinance.get(stock.getStockSymbol());
            } catch (IOException e) {
                e.printStackTrace();
            }

            double price = yahooStock.getQuote().getPrice().doubleValue();
            double change = yahooStock.getQuote().getChangeInPercent().doubleValue();

            System.out.println(change);
            if(abs(change) >= 0.5){
                User user = userDetailsService.getUserByUserId(stock.getUserId());
                //User user = userDetailsService.getUserByUserId(4);
                String subject = "User: " + user.getUsername() + ". One of your stock has significant price change";
                String text = "\nStock Name: " + yahooStock.getName() + "\nChange In Percent: " + change + "\nCurrent Price: " + price;
                text += "\nYou currently have " + stock.getStockQuantity() + " shares";

                long currentTimestamp = System.currentTimeMillis();
                System.out.println(currentTimestamp);
                if( (currentTimestamp - stock.getLastEmailEpochTime()) > 100){ //the number is for interval
                    EmailUtil.sendEmail(user.getEmail(),subject, text);
                    stock.setLastEmailEpochTime(currentTimestamp);
                    stockService.addStock(stock);
                }
            }
        }
    }

    public void sendUpdateStockInfo(){
        List<User> users = userDetailsService.checkAllUser();
        for(User user: users){
            List<Stock> stocks = stockService.getAllStocks(user.getUserId());
            if(stocks.size()>0){
                String[] symbols = new String[stocks.size()] ;
                for(int i=0; i<stocks.size(); i++){
                    symbols[i] = stocks.get(i).getStockSymbol();
                }
                Map<String, yahoofinance.Stock> yahooStocks = null;
                try {
                    yahooStocks = YahooFinance.get(symbols);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                HashMap<String, Double> priceMap = new HashMap<>();
                double balance = 0;
                for(int i=0; i<stocks.size(); i++){
                    yahoofinance.Stock yahooStock = yahooStocks.get(stocks.get(i).getStockSymbol());
                    double price = yahooStock.getQuote().getPrice().doubleValue();
                    balance += price * stocks.get(i).getStockQuantity();
                    priceMap.put(stocks.get(i).getStockSymbol(), price);

                }
                System.out.println(priceMap);
                EmailUtil.sendEmailAboutStock(user, stocks, balance, priceMap);


            }
        }//end users loop
    }

//    public void update(){
//        System.out.println("hello");
//        List<User> users = userDetailsService.checkAllUser();
//
//
//        for(User user: users){
//            System.out.println(user);
//            List<Stock> stocks = stockService.getAllStocks(user.getUserId());
//            System.out.println(stocks.size());
//            System.out.println(stocks);
//            if(stocks.size()>0){
//                List<String> stockSymbolList = new ArrayList<>();
//                for(Stock stock: stocks){
//                    stockSymbolList.add(stock.getStockSymbol());
//                }
//                String jsonString = YahooUtil.getStock(stockSymbolList);
//                System.out.println(jsonString);
//                Object file = JSONValue.parse(jsonString);
//                JSONObject jsonObjectdecode = (JSONObject)file;
//                System.out.println(jsonObjectdecode);
//                JSONObject jsonChildObject = (JSONObject)jsonObjectdecode.get("quoteResponse");
//                System.out.println(jsonChildObject);
//                JSONArray results = (JSONArray)jsonChildObject.get("result");
//                System.out.println(results);
//                HashMap<String, Double> priceMap = new HashMap<>();
//                for(int i=0; i<stocks.size(); i++){
//                    JSONObject result =  (JSONObject)results.get(i);
//                    double regularMarketPrice = (Double)result.get("regularMarketPrice");
//                    priceMap.put(stocks.get(i).getStockSymbol(), regularMarketPrice);
//
//                }
//                System.out.println(priceMap);
//                EmailUtil.sendEmailAboutStock(user, stocks, priceMap);
//
//            }
//        }//end users loop
//    }
}
