package dev.team4.portfoliotracker.models;

import java.util.List;
import java.util.Objects;

public class PortfolioObj {
    //THIS CLASS IS NOT AN ENTITY IN THE DATABASE, DO NOT MAP TO A TABLE

    private String name;
    private int portfolioId;
    private int userId;
    private boolean isPublic;
    private double value;
    List<Stock> stocks;
    List<Transaction> transactions;

    PortfolioObj() {
        super();
    }

    PortfolioObj(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
    }

    PortfolioObj(String name, boolean isPublic, List<Stock> stocks) {
        this.name = name;
        this.isPublic = isPublic;
        this.stocks = stocks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public double getValue() {
        return value;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
        //THIS SHOULD CALL TO TRANS CONTROLLER METHOD FOR UPDATED LIST
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setValue(List<Stock> stocks) {
        //Calculate the total value of portfolio based on stock list
        this.value = 0; //initialize to 0 before adding totals

        for (Stock stock : stocks) {
            this.value += (stock.getQuantity()*stock.getCurrentPrice());
        }
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioObj that = (PortfolioObj) o;
        return isPublic == that.isPublic && value == that.value && Objects.equals(name, that.name) && Objects.equals(stocks, that.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isPublic, value, stocks);
    }

    @Override
    public String toString() {
        return "PortfolioObj{" +
                "name='" + name + '\'' +
                ", public=" + isPublic +
                ", value=" + value +
                ", stocks=" + stocks +
                '}';
    }
}
