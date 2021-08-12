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

    public List<Favorites> getFavoritesByUserId(int userId) {
        List<Favorites> favList = favoritesRepo.getFavoritesByUserId(userId);
        System.out.println(favList);
        return favList;
    }
}
