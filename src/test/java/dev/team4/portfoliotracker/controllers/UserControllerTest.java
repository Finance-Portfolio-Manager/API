package dev.team4.portfoliotracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    String jwtToken = "";
    String jwtToken2 = "";


    @BeforeEach
    public void setUp() {
        User user = new User("c@c.com", "u1", "pass");
        UserPrincipal userP = new UserPrincipal(user);
        jwtToken = new JwtUtility().generateToken(userP);
        System.out.println(jwtToken);

        User user2 = new User("c@c.com", "u2", "pass");
        UserPrincipal user2P = new UserPrincipal(user2);
        jwtToken2 = new JwtUtility().generateToken(user2P);

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user), headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8082/users", request, User.class);
    }

    User user = new User("c@c.com", "u1", "pass");
    User user2 = new User("c@c.com", "u2", "pass");

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void registerUserSuccess() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user2), headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8082/register", request, User.class);
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(201, response.getStatusCodeValue())
        );
    }

    @Test
    public void loginSuccess() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user), headers);
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
    public void deleteFailure() {
        User user2 = new User("c@c.com", "u1", "pass2");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user2), headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8082/users", HttpMethod.DELETE, request, User.class);

        assertAll(
                () -> assertEquals(500, response.getStatusCodeValue())
        );
    }

    @Test
    public void getUserFromToken() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8082/users?token="+ jwtToken, User.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void getUserFromTokenFail() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8082/username?token=fakebGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI3OTA50TUY7jM5BwwC3-y66z4wjXl-mGD6XmubLNYhuQqk3TtARYpXrYTYjt1g7mYjx6MUV3NyQlI9AZK6dA", User.class);

        System.out.println(request.getBody());
        System.out.println(response.getBody());

        assertAll(
                () -> assertEquals(500, response.getStatusCodeValue())
        );
    }

    @Test
    public void getUserFromUsername() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8082/register/u1", User.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void deleteSuccess() {
        User user2 = new User("c@c.com", jwtToken, "pass");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user2), headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8082/users", HttpMethod.DELETE, request, User.class);

        assertAll(
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @AfterAll
    public void tearDown() {
        User u1 = new User("c@c.com", jwtToken, "pass");
        User u2 = new User("c@c.com", jwtToken2, "pass");

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
    }
}