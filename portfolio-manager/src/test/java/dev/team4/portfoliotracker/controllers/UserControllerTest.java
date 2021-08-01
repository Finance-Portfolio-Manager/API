package dev.team4.portfoliotracker.controllers;

import com.mashape.unirest.http.ObjectMapper;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.security.UserPrincipal;
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
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void successfulRegister(){

        User user = new User();
        user.setUserId(1);
        user.setUsername("cody");
        user.setPassword("password");
        user.setEmail("c@c.com");
        user.setFirstName("Cody");
        user.setLastName("Anderson");

//        this.mockMvc.perform(post("/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("utf-8")
//                .content());
    }

    @Test
    public void successfulLogin() throws Exception {
        User user = new User();
        user.setUsername("cody");
        user.setPassword("anderson");
        UserPrincipal userP = new UserPrincipal(user);

        doReturn(userP).when(userDetailsService).loadUserByUsername("cody");

        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{ \"username\":\"cody\", \"password\": anderson }"))
                .andExpect(status().isOk());
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

        doReturn("cody").when(jwtUtility).getUsernameFromToken("token");
        doReturn(user).when(userDetailsService).getUserByUsername("cody");

        this.mockMvc.perform(get("/username")
                    .param("String token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content("{ \"token\":\"token }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(status().isOk());
    }
}