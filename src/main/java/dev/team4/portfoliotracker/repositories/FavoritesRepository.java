package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
    List<Favorites> getFavoritesByUserId(int userId);
}
