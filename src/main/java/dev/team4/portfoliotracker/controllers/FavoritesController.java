package dev.team4.portfoliotracker.controllers;


import dev.team4.portfoliotracker.models.Favorites;
import dev.team4.portfoliotracker.services.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    @Autowired
    FavoritesService favoritesService;

    //Relocated the get favorites for a user functionality to portfolio controller

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Favorites> addNewFavorites(@RequestBody Favorites favorites) {
        Favorites f = new Favorites();
        f.setUserId(favorites.getUserId());
        f.setPortfolioId(favorites.getPortfolioId());
        favoritesService.addFavorite(f);
        return new ResponseEntity<>(f, HttpStatus.CREATED);
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<Favorites> deleteFavorites(@RequestBody Favorites favorites) {
        favoritesService.deleteFavorite(favorites);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
