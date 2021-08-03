package dev.team4.portfoliotracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.UserRepository;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.services.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @BeforeEach
    public void setUp() {
        User user = new User("Cody", "Anderson", "c@c.com", "cody", "pass");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user), headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8082/register", request, User.class);
    }

    User user = new User("Cody", "Anderson", "c@c.com", "cody", "pass");

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void registerUserSuccess() {
        user.setUsername("cody2");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user), headers);
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
        User user2 = new User("Cody", "Anderson", "c@c.com", "user2", "pass");
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
        User user2 = new User("Cody", "Anderson", "c@c.com", "cody", "pass2");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user2), headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8082/delete", HttpMethod.DELETE, request, User.class);

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
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8082/username?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI3OTA5NzAyLCJpYXQiOjE2Mjc4NzM3MDJ9.kZQ9wiaQ0TUY7jM5BwwC3-y66z4wjXl-mGD6XmubLNYhuQqk3TtARYpXrYTYjt1g7mYjx6MUV3NyQlI9AZK6dA", User.class);
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
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8082/username?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI3OTA50TUY7jM5BwwC3-y66z4wjXl-mGD6XmubLNYhuQqk3TtARYpXrYTYjt1g7mYjx6MUV3NyQlI9AZK6dA", User.class);
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
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8082/register/cody", User.class);
        System.out.println(request.getBody());
        System.out.println(response.getBody());

        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void deleteSuccess() {
        User user2 = new User("Cody", "Anderson", "c@c.com", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI3OTA3NDAwLCJpYXQiOjE2Mjc4NzE0MDB9.4YkqBitpwR24fngB7OWwtWMAMj_HGMCx50xne6vEdRAL3CAvMdSpk0tmVxi6KmBRE0Yux4J_YyvWMz_dOhELWw", "pass");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user2), headers);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8082/delete", HttpMethod.DELETE, request, User.class);

        assertAll(
                () -> assertEquals(200, response.getStatusCodeValue())
        );
    }
}