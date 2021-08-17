package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.exceptions.BadRequestException;
import dev.team4.portfoliotracker.models.Balances;
import dev.team4.portfoliotracker.repositories.BalancesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;

@SpringBootTest
class BalancesServiceImplTest {

    @InjectMocks
    BalancesServiceImpl balancesService;

    @Mock
    BalancesRepository balancesRepository;

    List<Balances> list = new ArrayList<>();
    Balances bal = new Balances(new BigDecimal(123.45), LocalDateTime.now(), 1, 2, "i");

    @BeforeEach
    public void setUp() {
        Balances bal1 = new Balances(new BigDecimal(123.45), LocalDateTime.now(), 1, 2, "i");
        Balances bal2 = new Balances(new BigDecimal(234.56), LocalDateTime.now(), 1, 2, "c");

        list.add(bal1);
        list.add(bal2);
    }

    @Test
    void getAllBalances() {
        doReturn(list).when(balancesRepository).findAll();
        assertEquals(balancesService.getAllBalances(), list);
    }

    @Test
    void getAllBalancesByUserId() {
        doReturn(list).when(balancesRepository).findAllBalancesByUserId(1);
        assertEquals(balancesService.getAllBalancesByUserId(1), list);
    }

    @Test
    void getAllBalancesByUserIdInvalidUserShouldFail() {
        doThrow(BadRequestException.class).when(balancesRepository).findAllBalancesByUserId(-1);
        assertThrows(BadRequestException.class, () -> {
           balancesService.getAllBalancesByUserId(-1);
        });
    }

    @Test
    void getAllBalancesByPortfolioId() {
        doReturn(list).when(balancesRepository).findAllBalancesByPortfolioIdOrderByDateDesc(2);
        assertEquals(balancesService.getAllBalancesByPortfolioId(2), list);
    }

    @Test
    void getAllBalancesByUserIdInvalidPortfolioShouldFail() {
        doThrow(BadRequestException.class).when(balancesRepository).findAllBalancesByPortfolioIdOrderByDateDesc(-1);
        assertThrows(BadRequestException.class, () -> {
            balancesService.getAllBalancesByPortfolioId(-1);
        });
    }

    @Test
    void addBalance() {
        doReturn(bal).when(balancesRepository).save(bal);
        assertEquals(balancesService.addBalance(bal), bal);
    }
}