package dev.team4.portfoliotracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.team4.portfoliotracker.models.UserStocks;

public interface StockRepository extends JpaRepository<UserStocks, Integer> {
}
