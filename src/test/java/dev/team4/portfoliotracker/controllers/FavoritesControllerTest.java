package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Favorites;
import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FavoritesControllerTest {

    private static String jwt = "";

    static {
        User user = new User("c@c.com", "cody", "pass");
        UserPrincipal userP = new UserPrincipal(user);
        jwt = new JwtUtility().generateToken(userP);
    }

//    private String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI4OTA5NzQwLCJpYXQiOjE2Mjg4NzM3NDB9.P7KYQArjVHxTr8E6veIhMpuvKJTVUjuoVIH74Eo_2swxLLw4LxhcogZmIR8pGZYrESGp1fVFsLJvOr8UpVI1Ow";
    //This is generated via a login request and expires after 10 hours, if you get a 403 response, renew this token.

    @Test
    public void addFavoritesAddsPortfolioToUsersFavorites() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        Favorites favorite = new Favorites(2, 4);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Favorites> request = new HttpEntity<>(favorite, headers);

        ResponseEntity<Favorites> response = restTemplate.exchange(
                "http://localhost:8082/favorites",
                HttpMethod.POST,
                request,
                Favorites.class);

        System.out.println("Request body: "+request.getBody());
        System.out.println("Response body: "+response.getBody());
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(201, response.getStatusCodeValue())
        );

        Favorites favDel = response.getBody();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", jwt);
        headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Favorites> request2 = new HttpEntity<>(favDel, headers2);

        ResponseEntity<String> response2 = restTemplate.exchange(
                "http://localhost:8082/portfolios",
                HttpMethod.DELETE,
                request2,
                String.class);
    }

    @Test
    public void deleteFavoritesRemovesFromUsersFavoritesList() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        Favorites favorite = new Favorites(2, 4);

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", jwt);
        headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Favorites> request2 = new HttpEntity<>(favorite, headers2);

        ResponseEntity<Favorites> response2 = restTemplate.exchange(
                "http://localhost:8082/portfolios",
                HttpMethod.POST,
                request2,
                Favorites.class);

        Favorites favDel = response2.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Favorites> request = new HttpEntity<>(favDel, headers);

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
