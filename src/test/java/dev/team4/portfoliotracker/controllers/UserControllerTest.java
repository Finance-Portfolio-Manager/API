package dev.team4.portfoliotracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {


    @BeforeEach
    public void setUp() {
        User user = new User("c@c.com", "u1", "pass");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user), headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8082/users", request, User.class);

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void registerUserSuccess() {
        User user2 = new User("c@c.com", "u3", "pass");

        TestRestTemplate restTemplate2 = new TestRestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request2 = new HttpEntity<>(asJsonString(user2), headers2);
        ResponseEntity<User> response2 = restTemplate2.postForEntity("http://localhost:8082/users", request2, User.class);

        assertAll(
                () -> assertNotNull(response2.getBody()),
                () -> assertEquals(201, response2.getStatusCodeValue())
        );
    }

    @Test
    public void loginSuccess() {
        User log = new User("c@c.com", "u1", "pass");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(log), headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8082/login", request, User.class);

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void loginFailure() {
        User user2 = new User("c@c.com", "user2", "pass");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user2), headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8082/login", request, User.class);

        assertAll(
                () -> assertEquals(500, response.getStatusCodeValue())
        );
    }

    @Test
    public void getUserFromToken() {
        String jwtToken = "";
        User user = new User("c@c.com", "u1", "pass");
        UserPrincipal userP = new UserPrincipal(user);
        jwtToken = new JwtUtility().generateToken(userP);

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8082/login?token="+ jwtToken, User.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody())
        );
    }

    @Test
    public void deleteSuccess() {
        User user2 = new User("c@c.com", "u1", "pass");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user2), headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8082/users", HttpMethod.DELETE, request, User.class);

        assertAll(
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @AfterEach
    public void tearDown() {
        User u1 = new User("c@c.com", "u1", "pass");
        User u2 = new User("c@c.com", "u2", "pass");
        User u3 = new User("c@c.com", "u3", "pass");

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(asJsonString(u1), headers);
        restTemplate.exchange("http://localhost:8082/delete", HttpMethod.DELETE, request, User.class);

        TestRestTemplate restTemplate2 = new TestRestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request2 = new HttpEntity<>(asJsonString(u2), headers2);
        restTemplate2.exchange("http://localhost:8082/delete", HttpMethod.DELETE, request2, User.class);

        TestRestTemplate restTemplate3 = new TestRestTemplate();
        HttpHeaders headers3 = new HttpHeaders();
        headers3.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request3 = new HttpEntity<>(asJsonString(u3), headers3);
        restTemplate3.exchange("http://localhost:8082/delete", HttpMethod.DELETE, request3, User.class);
    }
}