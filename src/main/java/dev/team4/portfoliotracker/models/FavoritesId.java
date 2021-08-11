package dev.team4.portfoliotracker.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoritesId implements Serializable {

    @Column(name = "user_id")
    private User user;

    @Column(name = "portfolio_id")
    private Portfolio portfolio;

    public FavoritesId() {
        super();
    }

    public FavoritesId(User user, Portfolio portfolio) {
        this.user = user;
        this.portfolio = portfolio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritesId that = (FavoritesId) o;
        return Objects.equals(user, that.user) && Objects.equals(portfolio, that.portfolio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, portfolio);
    }

    @Override
    public String toString() {
        return "FavoritesId{" +
                "user=" + user +
                ", portfolio=" + portfolio +
                '}';
    }
}
