package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static dev.team4.portfoliotracker.controllers.UserControllerTest.asJsonString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionControllerTest {

    private String url = "http://localhost:8082/transactions";
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    public void setUp() {
        User user = new User("Cody", "Anderson", "c@c.com", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyNzkwMDk5OCwiaWF0IjoxNjI3ODY0OTk4fQ.c0tO21C-Wl1vdhyEeTo1iJ1i1doxhm9_4ElA6VPKDGZvVVM-r0ULszdug5LJDRRM_ogaPycOyv5DkUJfCSjEBQ", "pass");
        user.setUsername("admin");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request2 = new HttpEntity<>(asJsonString(user), headers);
        ResponseEntity<User> response2 = restTemplate.postForEntity("http://localhost:8082/register", request2, User.class);
        System.out.println(response2.getBody());
    }

//    @Test
//    void getAllTransactionsNotLoggedInShouldFail() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<Transaction> response = restTemplate.getForEntity(url, Transaction.class);
//
//        assertAll(
//                () -> assertNotNull(response.getBody()),
//                () -> assertEquals(403, response.getStatusCodeValue())
//        );
//    }

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
    void getAllTransactionsByUserId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8082/transactions?userId=1", String.class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    void getTransactionById() {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyNzkwMDk5OCwiaWF0IjoxNjI3ODY0OTk4fQ.c0tO21C-Wl1vdhyEeTo1iJ1i1doxhm9_4ElA6VPKDGZvVVM-r0ULszdug5LJDRRM_ogaPycOyv5DkUJfCSjEBQ");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/transactions/36", HttpMethod.GET, request, String.class);
        assertAll(
                () -> assertNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

//    @Test
//    void addTransaction() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyNzkwMDk5OCwiaWF0IjoxNjI3ODY0OTk4fQ.c0tO21C-Wl1vdhyEeTo1iJ1i1doxhm9_4ElA6VPKDGZvVVM-r0ULszdug5LJDRRM_ogaPycOyv5DkUJfCSjEBQ");
//        HttpEntity<String> request = new HttpEntity<>("{\"userId\":1,\"ticker\":\"BRUH\",\"shareAmount\":2.0,\"sharePrice\":\"420,\"note\":\"blah ablah balh\",\"isBuy\":\"true\"}", headers);
//        ResponseEntity<Transaction> response = restTemplate.postForEntity(url + "/new", request, Transaction.class);
//        System.out.println(response.getBody());
//
//        assertAll(
//                () -> assertNotNull(response.getBody()),
//                () -> assertEquals(200, response.getStatusCodeValue())
//        );
//    }

    @Test
    void updateTransaction() {
        Transaction txn1 = new Transaction(201, "TSLA", 5.32, 690.32, "test1", true);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(txn1), headers);
    }

    @Test
    void deleteTransaction() {
        Transaction txn1 = new Transaction(201, "TSLA", 5.32, 690.32, "test1", true);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(txn1), headers);
    }
}
