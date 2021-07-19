package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}
