// Author: David Garcia
package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.repositories.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ApiService {

    @Autowired
    ApiRepository apiRepository;

    /**
     * Author: David Garcia
     * @param symbolArray
     * @return Returns map in which key is symbol as a String, and value as current stock price of type BigDecimal
     */
    public Map<String,BigDecimal> getSymbolPrices(String[] symbolArray){
        try {
            Map<String, Stock> yahooFinanceMap = YahooFinance.get(symbolArray);

            return yahooFinanceMap
                    .entrySet()
                    .stream()
                    .collect(Collectors
                            .toMap(e-> (String) e.getKey(),
                                    e-> (BigDecimal) e.getValue().getQuote().getPrice()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Author: David Garcia
     * @param symbolArray
     * @return calculates profit and loss (PNL) from current stock price based on previous close. Returns
     * map with stock symbol as string, and pnl as BigDecimal.
     */
    public Map<String,BigDecimal> getSymbolPnl(String[] symbolArray){
        try{
            Map<String, Stock> yahooFinanceMap = YahooFinance.get(symbolArray);

            return yahooFinanceMap
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(e-> (String) e.getKey(),
                            e -> (((BigDecimal) e.getValue().getQuote().getPrice().setScale(4,RoundingMode.HALF_UP)
                                    .subtract((BigDecimal) e.getValue().getQuote().getPreviousClose().setScale(4,RoundingMode.HALF_UP)))
                                    .divide((BigDecimal) e.getValue().getQuote().getPreviousClose(),RoundingMode.HALF_UP)
                                    .multiply(BigDecimal.valueOf(100))
                                    .setScale(2,RoundingMode.HALF_UP)
                            )));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Set<News> getDailyNews(){


        return null;
    }
}
