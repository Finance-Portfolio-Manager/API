package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BalancesControllerTest {

    private String url = "http://localhost:8082/balances";
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private static String jwtToken = "";

    static {
        User user = new User("c@c.com", "cody", "pass");
        UserPrincipal userP = new UserPrincipal(user);
        jwtToken = new JwtUtility().generateToken(userP);
    }

    // umm this one is failing because it's actually getting a 200...
    @Test
    void getDailyBalancesByPortfolioIdNoJWTShouldFail() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/daily/1", String.class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(401, response.getStatusCodeValue())
        );
    }

    @Test
    void getDailyBalancesByPortfolioId() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", jwtToken);
        HttpEntity<Integer> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.getForEntity(url + "/daily/1", String.class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
}