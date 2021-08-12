//package dev.team4.portfoliotracker.models;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Embeddable
//public class FavoritesId implements Serializable {
//
//    @Column(name = "user_id")
//    private int userId;
//
//    @Column(name = "portfolio_id")
//    private int portfolioId;
//
//    public FavoritesId() {
//        super();
//    }
//
//    public FavoritesId(int userId, int portfolioId) {
//        this.userId = userId;
//        this.portfolioId = portfolioId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public int getPortfolioId() {
//        return portfolioId;
//    }
//
//    public void setPortfolioId(int portfolioId) {
//        this.portfolioId = portfolioId;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        FavoritesId that = (FavoritesId) o;
//        return userId == that.userId && portfolioId == that.portfolioId;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userId, portfolioId);
//    }
//
//    @Override
//    public String toString() {
//        return "FavoritesId{" +
//                "userId=" + userId +
//                ", portfolioId=" + portfolioId +
//                '}';
//    }
//}
