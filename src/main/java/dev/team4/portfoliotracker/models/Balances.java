package dev.team4.portfoliotracker.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Entity
@Table(name = "balances")
public class Balances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private int balanceId;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "portfolio_id")
    private int portfolioId;

    @Column(name = "balance_type")
    private String balanceType;

    public Balances() {
    }

    public Balances(BigDecimal balance, LocalDateTime date, int userId, int portfolioId, String balanceType) {
        this.balance = balance;
        this.date = date;
        this.userId = userId;
        this.portfolioId = portfolioId;
        this.balanceType = balanceType;
    }

    public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balances balances = (Balances) o;
        return balanceId == balances.balanceId && userId == balances.userId && portfolioId == balances.portfolioId && Objects.equals(balance, balances.balance) && Objects.equals(date, balances.date) && Objects.equals(balanceType, balances.balanceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceId, balance, date, userId, portfolioId, balanceType);
    }

    @Override
    public String toString() {
        return "Balances{" +
                "balanceId=" + balanceId +
                ", balance=" + balance +
                ", date=" + date +
                ", userId=" + userId +
                ", portfolioId=" + portfolioId +
                ", balanceType='" + balanceType + '\'' +
                '}';
    }
}