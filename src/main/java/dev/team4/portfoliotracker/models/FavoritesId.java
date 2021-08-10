package dev.team4.portfoliotracker.models;

import java.io.Serializable;
import java.util.Objects;

public class FavoritesId implements Serializable {
    private int userId;
    private int portfolioId;

    public FavoritesId() {
        super();
    }

    public FavoritesId(int userId, int portfolioId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritesId that = (FavoritesId) o;
        return userId == that.userId && portfolioId == that.portfolioId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, portfolioId);
    }
}
