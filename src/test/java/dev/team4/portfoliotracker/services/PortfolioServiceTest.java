package dev.team4.portfoliotracker.services;


import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.Transaction;
import dev.team4.portfoliotracker.models.User;
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
public class PortfolioServiceTest {

    @InjectMocks
    PortfolioService portfolioService;

    @Mock
    PortfolioRepository portfolioRepository;

    Portfolio portfolio = new Portfolio();

    @BeforeEach
    public void setUp() {
        List<Transaction> transactions = new ArrayList<>();
        portfolio.setName("TestP");
        portfolio.setPublic(true);
        portfolio.setUser(new User("c@c.com", "cody", "pass"));
        portfolio.setPortfolioId(1);
        Transaction transaction = new Transaction("TSLA", 126.45, new BigDecimal(156.55));
        Transaction transaction2 = new Transaction("AAPL", 12.45, new BigDecimal(15.55));
        transactions.add(transaction);
        transactions.add(transaction2);
        portfolio.setTransactions(transactions);
    }

    @Test
    public void getPortfolioByIdTest() throws Exception {
        doReturn(portfolio).when(portfolioRepository).getPortfolioByPortfolioId(1);

        assertEquals(portfolioService.getPortfolioByPortfolioId(1).getName(), portfolio.getName());
    }

    @Test
    public void getPortfolioByPublicTest() throws Exception {
        List<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(portfolio);
        doReturn(portfolios).when(portfolioRepository).getPortfoliosByIsPublic(true);

        assertNotNull(portfolioService.getPortfoliosByIsPublic(true));
    }

    @Test
    public void getPortfolioByUserTest() throws Exception {
        List<Portfolio> portfolios = new ArrayList<>();
        User user = new User("c@c.com", "cody", "password");
        portfolios.add(portfolio);
        doReturn(portfolios).when(portfolioRepository).getPortfoliosByUser(user);

        assertNotNull(portfolioService.getPortfoliosByUser(user));
    }

    @Test
    public void createPortfolioTest() throws Exception {
        doReturn(portfolio).when(portfolioRepository).save(portfolio);

        assertEquals(portfolioService.createNewPortfolio(portfolio).getPortfolioId(), portfolio.getPortfolioId());
    }

    @Test
    public void deletePortfolioTest() throws Exception {
        doNothing().when(portfolioRepository).delete(portfolio);

        assertEquals(portfolioService.deletePortfolio(portfolio).getPortfolioId(), portfolio.getPortfolioId());
    }

}
