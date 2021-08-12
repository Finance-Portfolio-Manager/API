package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.Favorites;
import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.FavoritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasItems;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FavoritesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FavoritesService favoritesService;

    @Autowired
    FavoritesController favoritesController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(favoritesController).build();
    }

    @Test
    public void getFavoritesByUser() throws Exception {
        Portfolio portfolio = new Portfolio();

        User user = new User();
        user.setUserId(1);
        user.setUsername("Marc");
        user.setPassword("1234");
        user.setEmail("Marc@marc.test");


        portfolio.setUser(user);
        portfolio.setName("Marc's Test Portfolio");
        portfolio.setPublic(false);


        List<Favorites> listId = Arrays.asList(
                new Favorites(user, portfolio)
        );
        doReturn(listId).when(favoritesService).getFavoritesByUser(user);

        this.mockMvc.perform(get("/accounts/3/favorites").header("Authorization","auth"))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
