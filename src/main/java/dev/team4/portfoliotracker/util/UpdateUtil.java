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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class UpdateUtil {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    StockService stockService;

    public void update(){
        System.out.println("hello");
        List<User> users = userDetailsService.checkAllUser();


        for(User user: users){
            System.out.println(user);
            List<Stock> stocks = stockService.getAllStocks(user.getUserId());
            System.out.println(stocks.size());
            System.out.println(stocks);
            if(stocks.size()>0){
                List<String> stockSymbolList = new ArrayList<>();
                for(Stock stock: stocks){
                    stockSymbolList.add(stock.getStockSymbol());
                }
                String jsonString = YahooUtil.getStock(stockSymbolList);
                System.out.println(jsonString);
                Object file = JSONValue.parse(jsonString);
                JSONObject jsonObjectdecode = (JSONObject)file;
                System.out.println(jsonObjectdecode);
                JSONObject jsonChildObject = (JSONObject)jsonObjectdecode.get("quoteResponse");
                System.out.println(jsonChildObject);
                JSONArray results = (JSONArray)jsonChildObject.get("result");
                System.out.println(results);
                HashMap<String, Double> priceMap = new HashMap<>();
                for(int i=0; i<stocks.size(); i++){
                    JSONObject result =  (JSONObject)results.get(i);
                    double regularMarketPrice = (Double)result.get("regularMarketPrice");
                    priceMap.put(stocks.get(i).getStockSymbol(), regularMarketPrice);

                }
                System.out.println(priceMap);
                EmailUtil.sendEmailAboutStock(user, stocks, priceMap);
//                String subject = "This is the stock information";
//                String text = priceMap.toString();
//                EmailUtil.sendEmail(user.getEmail(), subject, text);


            }
        }//end users loop
    }
}
