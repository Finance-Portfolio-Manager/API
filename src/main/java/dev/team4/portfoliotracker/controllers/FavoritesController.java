package dev.team4.portfoliotracker.controllers;


import dev.team4.portfoliotracker.models.Favorites;
import dev.team4.portfoliotracker.services.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{user_id}/favorites")
public class FavoritesController {

    @Autowired
    FavoritesService favoritesService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Favorites>> getFavorites(@PathVariable("user_id") int id) {
        return new ResponseEntity<>(favoritesService.getFavorite(id), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Favorites> addNewFavorites(@RequestBody Favorites favorites) {
        Favorites f = favoritesService.addFavorite(favorites);
        return new ResponseEntity<>(f, HttpStatus.CREATED);
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<Favorites> deleteFavorites(@RequestBody Favorites favorites) {
        favoritesService.deleteFavorite(favorites);
        return new ResponseEntity<>(favorites, HttpStatus.ACCEPTED);
    }
}
