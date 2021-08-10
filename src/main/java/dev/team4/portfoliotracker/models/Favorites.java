package dev.team4.portfoliotracker.models;

import java.io.Serializable;
import java.util.Objects;

public class Favorites implements Serializable {

    private int userId;

    private int stockId;

    public Favorites() {
        super();
    }

    public Favorites(int userId, int stockId) {
        super();
        this.userId = userId;
        this.stockId = stockId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorites = (Favorites) o;
        return userId == favorites.userId && stockId == favorites.stockId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, stockId);
    }
}
