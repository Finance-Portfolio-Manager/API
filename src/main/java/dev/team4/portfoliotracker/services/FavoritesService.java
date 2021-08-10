package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Favorites;

import java.util.List;

public interface FavoritesService {

    Favorites addFavorite(Favorites favorites);
    void deleteFavorite(Favorites favorites);
    List<Favorites> getFavorite(int userId);
}
