package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PortfolioControllerTest {

    private String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI4OTA5NzQwLCJpYXQiOjE2Mjg4NzM3NDB9.P7KYQArjVHxTr8E6veIhMpuvKJTVUjuoVIH74Eo_2swxLLw4LxhcogZmIR8pGZYrESGp1fVFsLJvOr8UpVI1Ow";
    //This is generated via a login request and expires after 10 hours, if you get a 403 response, renew this token.

    @Test
    public void getPublicPortfoliosReturnsListOfPublicPortfolios() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/portfolios/public",
                HttpMethod.GET,
                request,
                String.class);

        System.out.println("Request body: "+request.getBody());
        System.out.println("Response body: "+response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void getPortfolioByUserReturnsUsersPortfolios() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/portfolios/1",
                HttpMethod.GET,
                request,
                String.class);

        System.out.println("Request body: "+request.getBody());
        System.out.println("Response body: "+response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void getPortfoliosFromUsersFavoriteList() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/portfolios/2/favorites",
                HttpMethod.GET,
                request,
                String.class);

        System.out.println("Request body: "+request.getBody());
        System.out.println("Response body: "+response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void createNewPortfolioAddsNewPortfolio() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        User user = new User(1, "testuser1@gmail.com", "TestUser1", "pass123");
        Portfolio portfolio = new Portfolio(user, "TestAddPortfolio", false);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Portfolio> request = new HttpEntity<>(portfolio, headers);

        ResponseEntity<Portfolio> response = restTemplate.exchange(
                "http://localhost:8082/portfolios",
                HttpMethod.POST,
                request,
                Portfolio.class);

        System.out.println("Request body: "+request.getBody());
        System.out.println("Response body: "+response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(201, response.getStatusCodeValue())
        );

        Portfolio portfolioDel = response.getBody();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", jwt);
        headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Portfolio> request2 = new HttpEntity<>(portfolioDel, headers2);

        ResponseEntity<String> response2 = restTemplate.exchange(
                "http://localhost:8082/portfolios",
                HttpMethod.DELETE,
                request2,
                String.class);

    }

    @Test
    public void deletePortfolioRemovesFromDatabase() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        User user = new User(1, "testuser1@gmail.com", "TestUser1", "pass123");
        Portfolio portfolio = new Portfolio(user, "TestDeletePortfolio", false);

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", jwt);
        headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Portfolio> request2 = new HttpEntity<>(portfolio, headers2);

        ResponseEntity<Portfolio> response2 = restTemplate.exchange(
                "http://localhost:8082/portfolios",
                HttpMethod.POST,
                request2,
                Portfolio.class);

        Portfolio portfolioDel = response2.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Portfolio> request = new HttpEntity<>(portfolioDel, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8082/portfolios",
                HttpMethod.DELETE,
                request,
                String.class);

        System.out.println("Request body: "+request.getBody());
        System.out.println("Response body: "+response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(202, response.getStatusCodeValue())
        );
    }

}
