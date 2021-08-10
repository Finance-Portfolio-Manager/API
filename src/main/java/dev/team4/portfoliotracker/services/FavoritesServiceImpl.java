package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.Favorites;
import dev.team4.portfoliotracker.repositories.FavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    private FavoritesRepository favoritesRepo;

    @Override
    public Favorites addFavorite(Favorites favorites) {
        return favoritesRepo.save(favorites);
    }

    @Override
    public void deleteFavorite(Favorites favorites) {
        favoritesRepo.delete(favorites);
    }

    @Override
    public List<Favorites> getFavorite(int userId) {
        return favoritesRepo.getFavoritesByUserId(userId);
    }
}
