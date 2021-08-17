package dev.team4.portfoliotracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChangePasswordControllerTest {

    String jwtToken = "";
    String URLUsers = "http://localhost:8082/users";
    String URLPassword = "http://localhost:8082/password";
    User user = new User("test@gmail.com", "test_user_change_password", "pass");

    @BeforeEach
    public void setUp() {
        UserPrincipal userP = new UserPrincipal(user);
        jwtToken = new JwtUtility().generateToken(userP);
        System.out.println(jwtToken);

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user), headers);
        ResponseEntity<User> response = restTemplate.postForEntity(URLUsers, request, User.class);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGenerateCode() {
        String email = "test@gmail.com";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                URLPassword + "?email=" + email,
                String.class);
        System.out.println(response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(201, response.getStatusCodeValue())
        );
    }

    @Test
    public void testChangePassword() {
        String email = "test@gmail.com";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                URLPassword + "?email=" + email,
                String.class);
        String code = response.getBody();
        user.setCode(code);
        ResponseEntity<String> response2 = restTemplate.postForEntity(
                URLPassword, user,
                String.class);
        assertAll(
                () -> assertEquals(200, response2.getStatusCodeValue())
        );
    }

    @AfterEach
    public void tearDown() {
        User user2 = new User("revature.team.3@gmail.com", jwtToken, "pass");
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(asJsonString(user2), headers);
        ResponseEntity<User> response = restTemplate.exchange(URLUsers, HttpMethod.DELETE, request, User.class);
    }
}
