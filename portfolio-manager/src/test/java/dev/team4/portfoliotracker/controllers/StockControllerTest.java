package dev.team4.portfoliotracker.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("userId", "1");
        map.add("stockSymbol", "AMD");
        map.add("stockQuantity", "10");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        System.out.println(asJsonString(stock));
        System.out.println(request.getBody());
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
	
	@Test
    public void deleteStock() {
        ResponseEntity<Stock> response = restTemplate.getForEntity(url + "/9", Stock.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
	
	@Test
    public void updateStock() {
        ResponseEntity<Stock> response = restTemplate.getForEntity(url + "/9", Stock.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
	
	@Test
    public void getStock() {
        ResponseEntity<Stock> response = restTemplate.getForEntity(url + "/9", Stock.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
	
    @Test
    public void getAllStocks() {
        ResponseEntity<Stock> response = restTemplate.getForEntity(url + "/9", Stock.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

}
