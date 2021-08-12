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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "favorite_id")
    private int favoriteId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "portfolio_id")
    private int portfolioId;


    public Favorites() {
        super();
    }

    public Favorites(int userId, int portfolioId) {
        this.userId = userId;
        this.portfolioId = portfolioId;
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

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorites = (Favorites) o;
        return favoriteId == favorites.favoriteId && userId == favorites.userId && portfolioId == favorites.portfolioId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteId, userId, portfolioId);
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "favoriteId=" + favoriteId +
                ", userId=" + userId +
                ", portfolioId=" + portfolioId +
                '}';
    }
}
