package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.UserDetailsService;
import dev.team4.portfoliotracker.security.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        return new ResponseEntity<User>(userDetailsService.createUser(user), HttpStatus.CREATED);
    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        userDetailsService.removeUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
