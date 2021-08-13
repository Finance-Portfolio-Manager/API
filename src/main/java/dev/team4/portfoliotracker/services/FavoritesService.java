package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Favorites;
import dev.team4.portfoliotracker.models.Portfolio;
import dev.team4.portfoliotracker.models.User;
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

    public List<Portfolio> getFavoritesByUser(User user) {
        List<Favorites> favList = favoritesRepo.getFavoritesByUser(user);
        List<Portfolio> favoritePortfolios = new ArrayList<>();

        PortfolioService portfolioService = new PortfolioService();
        //for each entry in the favorites table for a user, return the portfolio for the Id listed
        for (Favorites favorite : favList) {
            System.out.println(favorite);

            Portfolio tempPort = portfolioService.getPortfolioByPortfolioId(favorite.getPortfolio().getPortfolioId());
            System.out.println(tempPort);
            favoritePortfolios.add(tempPort);

        }
        return favoritePortfolios;
    }
}
