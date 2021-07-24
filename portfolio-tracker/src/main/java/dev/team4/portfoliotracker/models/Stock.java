package dev.team4.portfoliotracker.models;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "user_stocks")
public class Stock {
	
	@Column(name = "user_id")
	private int userId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_id")
	private int stockId;
	
	@Column(name = "stock_quantity")
	private double stockQuantity;

	public Stock() {
	}

	public Stock(int userId, double stockQuantity) {
		super();
		this.userId = userId;
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

	public double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(double stockQuantity) {
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
