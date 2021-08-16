package dev.team4.portfoliotracker.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiControllerTest {
	
	private String url = "http://localhost:8082/api";
	
	@Test
    public void getSymbolPricesReturnsPriceForArrayOfSymbols() {
        TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

        String[] requestSymbols = new String[]{"AMZN", "TSLA"};
        HttpEntity<String[]> request = new HttpEntity<>(requestSymbols, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url + "/get-symbol-prices",
                HttpMethod.POST,
                request,
                String.class);

        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void getProfitAndLossForArrayOfStockSymbols() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String[] requestSymbols = new String[]{"AMZN", "TSLA"};
        HttpEntity<String[]> request = new HttpEntity<>(requestSymbols, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url + "/get-symbol-pnl",
                HttpMethod.POST,
                request,
                String.class);

        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

}

