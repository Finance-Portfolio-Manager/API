package dev.team4.portfoliotracker.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class YahooUtil {
    public static String getStock(List<String> stockSymbols) {
        HttpResponse<String> response = null;
        StringBuilder uri = new StringBuilder("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=");
        for (String stockSymbol : stockSymbols) {
            uri.append(stockSymbol + "%2C");
        }
        uri.setLength(uri.length()-3);
        try {
            response = Unirest.get(uri.toString())
                    .header("x-rapidapi-key", "c22847e6f9mshc44c33d17be6a0bp17fc66jsne4e700e30b54")
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println(response.getBody());
        return response.getBody();

    }
}
