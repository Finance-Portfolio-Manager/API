package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Favorites;
import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.repositories.FavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FavoritesService {

    @Autowired
    FavoritesRepository favoritesRepo;

    public Favorites addFavorite(Favorites favorites) {
        return favoritesRepo.save(favorites);
    }

    public void deleteFavorite(Favorites favorites) {
        favoritesRepo.delete(favorites);
    }

    public List<Portfolio> getFavoritesByUserId(int userId) {
        List<Favorites> favList = favoritesRepo.getFavoritesByUserId(userId);
        List<Portfolio> favoritePortfolios = new ArrayList<>();

        PortfolioService portfolioService = new PortfolioService();
        //for each entry in the favorites table for a user, return the portfolio for the Id listed
        for (Favorites favorite : favList) {
            Portfolio tempPort = portfolioService.getPortfolioById(favorite.getPortfolioId());
            favoritePortfolios.add(tempPort);
        }
        return favoritePortfolios;
    }
}
