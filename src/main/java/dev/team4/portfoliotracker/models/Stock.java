package dev.team4.portfoliotracker.models;

import java.math.BigDecimal;
import java.util.Objects;
import dev.team4.portfoliotracker.services.ApiService;

public class Stock {
    //THIS CLASS IS NOT AN ENTITY IN THE DATABASE, DO NOT MAP TO A TABLE

    private String symbol;
    private int quantity;
    private double avgBuyPrice;
    private double currentPrice;
    private double changePercentage;

    Stock() {
        super();
    }

    Stock(String symbol) {
        this.symbol = symbol;
    }

    Stock(String symbol, int quantity, double avgBuyPrice, double currentPrice, double changePercentage) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
        this.currentPrice = currentPrice;
        this.changePercentage = changePercentage;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(double avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }

    public double getCurrentPrice() {
        //make a call to the setPrice method for updated info
        this.setCurrentPrice();

        return currentPrice;
    }

    public void setCurrentPrice() {
        //Calls to web scraper for updated pricing
        ApiService apiService = new ApiService();
        String[] symbolInput = new String[]{this.symbol};
        BigDecimal apiOut = apiService.getSymbolPrices(symbolInput).get(this.symbol);
        this.currentPrice = apiOut.doubleValue();
    }

    public double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return quantity == stock.quantity && Double.compare(stock.avgBuyPrice, avgBuyPrice) == 0 && Double.compare(stock.currentPrice, currentPrice) == 0 && Double.compare(stock.changePercentage, changePercentage) == 0 && Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, quantity, avgBuyPrice, currentPrice, changePercentage);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", quantity=" + quantity +
                ", avgBuyPrice=" + avgBuyPrice +
                ", currentPrice=" + currentPrice +
                ", changePercentage=" + changePercentage +
                '}';
    }
}
