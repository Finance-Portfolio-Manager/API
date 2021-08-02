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
public class ApiControllerTest {
	
	private String url = "http://localhost:8082/api";
	private TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Test
    public void fetchAllQuotes() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("[]", headers);
        ResponseEntity<String> response = restTemplate.exchange(url + "/all?symbol=AMD", HttpMethod.GET, request, String.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

}
