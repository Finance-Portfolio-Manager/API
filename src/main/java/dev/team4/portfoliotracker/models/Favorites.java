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

//    @EmbeddedId
//    FavoritesId id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "favorite_id")
    private int favoriteId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("user")
    @Column(name = "user_id")
    private int userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("portfolio")
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

    @Override
    public String toString() {
        return "Favorites{" +
                "userId=" + userId +
                ", portfolioId=" + portfolioId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorites = (Favorites) o;
        return userId == favorites.userId && portfolioId == favorites.portfolioId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, portfolioId);
    }
}
