package dev.team4.portfoliotracker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Component
@Entity
@Table(name = "favorites")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Favorites implements Serializable {

    @EmbeddedId
    FavoritesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("portfolio")
    @JoinColumn(name = "portfolio_id", insertable = false, updatable = false)
    private Portfolio portfolio;


    public Favorites() {
        super();
    }

    public Favorites(User user, Portfolio portfolio) {
        super();
        this.id = new FavoritesId(user, portfolio);
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

    public FavoritesId getId() {
        return id;
    }

    public void setId(FavoritesId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "id=" + id +
                ", user=" + user +
                ", portfolio=" + portfolio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorites = (Favorites) o;
        return Objects.equals(id, favorites.id) && Objects.equals(user, favorites.user) && Objects.equals(portfolio, favorites.portfolio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, portfolio);
    }
}
