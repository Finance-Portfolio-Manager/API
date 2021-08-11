package dev.team4.portfoliotracker.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Component
@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private int portfolioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "portfolio_name")
    private String name;

    @Column(name = "is_public", columnDefinition = "boolean default false")
    private boolean isPublic;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "portfolio")
    private List<Transaction> transactions;

    @OneToMany(targetEntity = Favorites.class, fetch = FetchType.LAZY, mappedBy = "portfolio")
    private List<User> favorited;

    public Portfolio() {
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Portfolio)) return false;
        Portfolio portfolio = (Portfolio) o;
        return portfolioId == portfolio.portfolioId && isPublic == portfolio.isPublic && Objects.equals(user, portfolio.user) && Objects.equals(transactions, portfolio.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, user, isPublic, transactions);
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId=" + portfolioId +
                ", user=" + user +
                ", isPublic=" + isPublic +
                ", transactions=" + transactions +
                '}';
    }
}
