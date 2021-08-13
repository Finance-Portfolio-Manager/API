package dev.team4.portfoliotracker.controllers;
import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionControllerTest {

    private String url = "http://localhost:8082/transactions";
    private TestRestTemplate restTemplate = new TestRestTemplate();

    String jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI4OTA5NzQwLCJpYXQiOjE2Mjg4NzM3NDB9.P7KYQArjVHxTr8E6veIhMpuvKJTVUjuoVIH74Eo_2swxLLw4LxhcogZmIR8pGZYrESGp1fVFsLJvOr8UpVI1Ow";

    @Test
    void getAllTransactions() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8082/transactions", String.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    void getTransactionById() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtToken);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/transactions/1", HttpMethod.GET, request, String.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    void addTransaction() {
        User user = new User(30, "test@email.com", "testuser_ap", "pass");
        LocalDateTime dateTime = LocalDateTime.now();
        Portfolio p = new Portfolio(10, user, "test", false);
        Transaction txn1 = new Transaction(p, "TSLA", 5.32, BigDecimal.valueOf(690.32), dateTime);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI4OTA5NzQwLCJpYXQiOjE2Mjg4NzM3NDB9.P7KYQArjVHxTr8E6veIhMpuvKJTVUjuoVIH74Eo_2swxLLw4LxhcogZmIR8pGZYrESGp1fVFsLJvOr8UpVI1Ow");
        HttpEntity<Transaction> request = new HttpEntity<>(txn1, headers);
        ResponseEntity<Transaction> response = restTemplate.postForEntity(url, request, Transaction.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(201, response.getStatusCodeValue())
        );
    }

    Transaction addTransactionforUpdate() {
        User user = new User(30, "test@email.com", "testuser_ap", "pass");
        LocalDateTime dateTime = LocalDateTime.now();
        Portfolio p = new Portfolio(10, user, "test", false);
        Transaction txn1 = new Transaction(p, "TSLA", 5.32, BigDecimal.valueOf(690.32), dateTime);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI4OTA5NzQwLCJpYXQiOjE2Mjg4NzM3NDB9.P7KYQArjVHxTr8E6veIhMpuvKJTVUjuoVIH74Eo_2swxLLw4LxhcogZmIR8pGZYrESGp1fVFsLJvOr8UpVI1Ow");
        HttpEntity<Transaction> request = new HttpEntity<>(txn1, headers);
        ResponseEntity<Transaction> response = restTemplate.postForEntity(url, request, Transaction.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(201, response.getStatusCodeValue())
        );
        return response.getBody();
    }


    @Test
    void updateTransaction() {
        Transaction txn2 = addTransactionforUpdate();
        int id= txn2.getTransactionId();
        User user = new User(30, "test@email.com", "testuser_ap", "pass");
        LocalDateTime dateTime = LocalDateTime.now();
        Portfolio p = new Portfolio(10, user, "test", false);
        Transaction txn1 = new Transaction(id , p, "MSFT", 10.32, BigDecimal.valueOf(690.32), dateTime);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", jwtToken);
        HttpEntity<Transaction> request = new HttpEntity<>(txn1, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PUT, request, Map.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    void deleteTransaction() {
        User user = new User(30, "test@email.com", "testuser_ap", "pass");
        LocalDateTime dateTime = LocalDateTime.now();
        Portfolio p = new Portfolio(10, user, "test", false);
        Transaction txn1 = new Transaction(6 , p, "MSFT", 10.32, BigDecimal.valueOf(690.32), dateTime);
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);
        headers2.add("Authorization", jwtToken);
        HttpEntity<Transaction> request2 = new HttpEntity<>(txn1, headers2);
        ResponseEntity<Map> response2 = restTemplate.exchange(url, HttpMethod.DELETE, request2, Map.class);
        System.out.println(response2.getBody());

        assertAll(
                () -> assertNotNull(response2.getBody()),
                () -> assertEquals(200, response2.getStatusCodeValue())
        );
    }
}
