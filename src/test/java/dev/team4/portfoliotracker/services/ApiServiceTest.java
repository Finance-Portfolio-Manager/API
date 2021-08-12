package dev.team4.portfoliotracker.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ApiServiceTest {

    @Autowired
    private ApiService apiService;

    @Test
    public void mappedSymbolPriceIsBigDecimalTest(){

        String[] testArray = {"MSFT","GOOG"};

        Map<String, BigDecimal> testMap = apiService.getSymbolPrices(testArray);

        assertEquals(BigDecimal.class,testMap.get("MSFT").getClass());
    }

    @Test
    public void mappedSymbolPnlIsBigDecimalTest(){

        String[] testArray = {"MSFT","GOOG"};

        Map<String,BigDecimal> testMap = apiService.getSymbolPnl(testArray);

        assertEquals(BigDecimal.class,testMap.get("MSFT").getClass());
    }

    @Test
    public void incorrectSymbolIsNullTest(){

        String[] testArray = {"M.SFT","GOOG"};
        Map<String,BigDecimal> testMap = apiService.getSymbolPnl(testArray);

        assertNull(testMap.get("M.SFT"));

    }

    @Test
    public void getPriceNullTest(){

        String[] testArray = {""};

        assertNull(apiService.getSymbolPrices(testArray));
    }

    @Test
    public void getPnlNullTest(){

        String[] testArray = {""};

        assertNull(apiService.getSymbolPnl(testArray));
    }
}
