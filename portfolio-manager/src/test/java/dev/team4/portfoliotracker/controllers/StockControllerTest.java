package dev.team4.portfoliotracker.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import dev.team4.portfoliotracker.services.UserDetailsService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.services.StockService;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StockControllerTest {
	
	String url = "http://localhost:8082/stocks";
	TestRestTemplate restTemplate = new TestRestTemplate();
	Stock stock = new Stock(1, "AMD", 10);
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
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
