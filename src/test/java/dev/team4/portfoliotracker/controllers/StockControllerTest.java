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

import dev.team4.portfoliotracker.models.Stock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StockControllerTest {
	
	private String url = "http://localhost:8082/stocks";
	private TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Test
    public void addStock() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"userId\":1,\"stockSymbol\":\"BRUH\",\"stockQuantity\":2.0}", headers);
        ResponseEntity<Stock> response = restTemplate.postForEntity(url, request, Stock.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
	
	//actually updateStock
	@Test
    public void getAllStock3() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"userId\":1,\"stockId\": 1,\"stockSymbol\":\"BRUH\",\"stockQuantity\":20.0}", headers);
        ResponseEntity<Stock> response = restTemplate.exchange(url, HttpMethod.PUT, request, Stock.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
	
	//actually getStock, renamed for unit testing
	@Test
    public void getAllStock2() {
        ResponseEntity<Stock> response = restTemplate.getForEntity(url + "/1", Stock.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
    
    @Test
    public void getAllStocks() {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/all/1", String.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
    
	@Test
    public void zdeleteStock() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"userId\":1,\"stockId\": 1,\"stockSymbol\":\"BRUH\",\"stockQuantity\":2.0}", headers);
        ResponseEntity<Stock> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Stock.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
	

}
