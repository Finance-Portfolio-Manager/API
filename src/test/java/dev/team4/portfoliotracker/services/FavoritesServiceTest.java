package dev.team4.portfoliotracker.services;


import dev.team4.portfoliotracker.models.Favorites;
import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.FavoritesRepository;
import dev.team4.portfoliotracker.repositories.PortfolioRepository;
import dev.team4.portfoliotracker.repositories.UserRepository;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class FavoritesServiceTest {

    @InjectMocks
    FavoritesService favoritesService;

    @Mock
    PortfolioService portfolioService;

    @Mock
    FavoritesRepository favoritesRepository;

    @Mock
    PortfolioRepository portfolioRepository;

    Favorites favorites;

    User user = new User("c@c.com", "cody", "pass");
    List<Favorites> favs = new ArrayList<>();
    List<Transaction> transactions = new ArrayList<>();
    Portfolio portfolio = new Portfolio();

    @BeforeEach
    public void setUp() {



        portfolio.setName("TestP");
        portfolio.setPublic(true);
        portfolio.setUser(new User("c@c.com", "cody", "pass"));
        portfolio.setPortfolioId(1);
        Transaction transaction = new Transaction("TSLA", 126.45, new BigDecimal(156.55));
        Transaction transaction2 = new Transaction("AAPL", 12.45, new BigDecimal(15.55));
        transactions.add(transaction);
        transactions.add(transaction2);
        portfolio.setTransactions(transactions);
        favorites = new Favorites (user.getUserId(), portfolio.getPortfolioId());
        favs.add(favorites);

    }



    @Test
    public void addFavoriteTest() throws Exception {
        doReturn(favorites).when(favoritesRepository).save(favorites);

        assertEquals(favoritesService.addFavorite(favorites), favorites);
    }

    @Test
    public void deleteFavoriteTest() throws Exception {
        doNothing().when(favoritesRepository).delete(favorites);
        favoritesService.deleteFavorite(favorites);
        verify(favoritesRepository, times(1)).delete(favorites);
//        assert(favoritesService.deleteFavorite(favorites));
    }

//    @Test
//    public void getFavoritesNullTest() throws Exception {
//        doReturn(favs).when(favoritesRepository).getFavoritesByUser(user);
////        doReturn(portfolio).when(portfolioService).getPortfolioByPortfolioId(1);
//        favoritesService.getFavoritesByUser(user);
//
//        verify(favoritesRepository, times(1)).getFavoritesByUser(user);
//    }

//    @Test
//    public void getPortfolioByPublicTest() throws Exception {
//        List<Portfolio> portfolios = new ArrayList<>();
//        portfolios.add(portfolio);
//        doReturn(portfolios).when(portfolioRepository).getPortfoliosByIsPublic(true);
//
//        assertNotNull(portfolioService.getPortfoliosByIsPublic(true));
//    }
//
//
//
//    @Test
//    public void createPortfolioTest() throws Exception {
//        doReturn(portfolio).when(portfolioRepository).save(portfolio);
//
//        assertEquals(portfolioService.createNewPortfolio(portfolio).getPortfolioId(), portfolio.getPortfolioId());
//    }
//
//    @Test
//    public void deletePortfolioTest() throws Exception {
//        doNothing().when(portfolioRepository).delete(portfolio);
//
//        assertEquals(portfolioService.deletePortfolio(portfolio).getPortfolioId(), portfolio.getPortfolioId());
//    }

}
