package dev.team4.portfoliotracker.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerChangePasswordTest {

    String jwtToken = "";
    String jwtToken2 = "";

    @BeforeEach
    public void setUp() {
        User user = new User("revature.team.3@gmail.com", "testChangePasswordWord", "password");
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @AfterEach
    public void tearDown() {
        User u1 = new User("revature.team.3@gmail.com", "testChangePasswordWord", "password");
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
