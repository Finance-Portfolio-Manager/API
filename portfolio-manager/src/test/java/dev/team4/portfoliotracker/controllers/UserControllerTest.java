package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.services.StockService;
import dev.team4.portfoliotracker.services.UserDetailsService;
import javafx.application.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    UserController userController;

    @MockBean
    JwtUtility jwtUtility;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void returnUserFromToken() throws Exception {

        User user = new User();
        user.setUserId(1);
        user.setUsername("cody");
        user.setPassword("password");
        user.setEmail("c@c.com");
        user.setFirstName("Cody");
        user.setLastName("Anderson");
        when(jwtUtility.getUsernameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI3NzE3MjgwLCJpYXQiOjE2Mjc2ODEyODB9.jqauc00_9CoBiTp8XP_mb0uv8egyeQKoL8WIvRXZ_GAAgn6Km7QbUxa3avjutOieR2vNMmOuHhTbzw7aFnsqZA")).thenReturn("cody");
        when(userDetailsService.getUserByUsername("cody")).thenReturn(user);

//        userDetailsService.loadUserByUsername("")
//        jwtUtility.generateToken()

        this.mockMvc.perform(get("/username")
                    .content("{ \"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb2R5IiwiZXhwIjoxNjI3NzE3MjgwLCJpYXQiOjE2Mjc2ODEyODB9.jqauc00_9CoBiTp8XP_mb0uv8egyeQKoL8WIvRXZ_GAAgn6Km7QbUxa3avjutOieR2vNMmOuHhTbzw7aFnsqZA"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.password").value(hasItems("password")));
    }



}
