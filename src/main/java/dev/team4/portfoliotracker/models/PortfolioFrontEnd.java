package dev.team4.portfoliotracker.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class PortfolioFrontEnd {
    //THIS CLASS IS NOT AN ENTITY IN THE DATABASE, DO NOT MAP TO A TABLE

    private String name;
    private int portfolioId;
    private String username;
    private boolean isPublic;
    private BigDecimal value;
    List<Stock> stocks;
    List<Transaction> transactions;

    public PortfolioFrontEnd() {
        super();
    }

    public PortfolioFrontEnd(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
    }

    public PortfolioFrontEnd(String name, boolean isPublic, List<Stock> stocks) {
        this.name = name;
        this.isPublic = isPublic;
        this.stocks = stocks;
    }
    public PortfolioFrontEnd(Portfolio portfolio) {
        this.portfolioId = portfolio.getPortfolioId();
        this.username = portfolio.getUser().getUsername();
        this.name = portfolio.getName();
        this.isPublic = portfolio.getPublic();
        this.transactions = portfolio.getTransactions();
    }

    public PortfolioFrontEnd(BigDecimal value) {
        this.value = value;
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


    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(List<Stock> stocks) {
        //Calculate the total value of portfolio based on stock list
        this.value = BigDecimal.ZERO; //initialize to 0 before adding totals

        for (Stock stock : stocks) {
            this.value = this.value.add(BigDecimal.valueOf(stock.getQuantity())
                    .multiply(stock.getCurrentPrice())).setScale(2, RoundingMode.CEILING);
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
        PortfolioFrontEnd that = (PortfolioFrontEnd) o;
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
