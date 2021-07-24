package dev.team4.portfoliotracker.models;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "user_stocks")
public class Stock {
	
	@Id
	@Column
	private int userId;
	
	@Id
	@Column
	private int stockId;
	
	@Column
	private int stockQuantity;

	public Stock(int userId, int stockId, int stockQuantity) {
		super();
		this.userId = userId;
		this.stockId = stockId;
		this.stockQuantity = stockQuantity;
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

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Override
	public boolean equals(Object obj) {
		Stock s = (Stock) obj;
		return (userId == s.userId  && 
				stockId == s.stockId && 
				stockQuantity == s.stockQuantity);
	}

	@Override
	public String toString() {
		return "\nStock: \n" +
				"userId: " + userId + "\n" +
				"stockId: " + stockId + "\n" +
				"stockQuantity: " + stockQuantity;
	}

}
