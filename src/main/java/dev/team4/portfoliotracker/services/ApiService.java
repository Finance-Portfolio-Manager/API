// Author: David Garcia
package dev.team4.portfoliotracker.services;

import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ApiService {

    public Map getSymbolPrices(String[] symbolArray){
        try {
            Map<String, Stock> yahooFinanceMap = YahooFinance.get(symbolArray);

            return yahooFinanceMap.entrySet().stream().collect(Collectors.toMap(e-> (String) e.getKey(), e-> (BigDecimal) e.getValue().getQuote().getPrice()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
