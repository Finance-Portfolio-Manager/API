// Author: David Garcia
package dev.team4.portfoliotracker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.NewsApiResponse;
import dev.team4.portfoliotracker.repositories.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ApiService {

    @Autowired
    ApiRepository apiRepository;

    @Value("${NEWS_API_KEY}")
    private String newsApiKey;

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
        String targetUrl = "https://newsapi.org/v2/everything?q=stocks&from=2021-08-09&to=2021-08-09&sortBy=popularity&domains=forbes.com&apiKey=" + newsApiKey;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Accept","application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper2 = new ObjectMapper();
        try {
            NewsApiResponse resp = objectMapper2.readValue(response.body(), NewsApiResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
