package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.UserDetailsService;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtility jwtUtility;


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAllAccounts() {
        return new ResponseEntity<>(userDetailsService.checkAllUser(), HttpStatus.OK);
    }


    @GetMapping(value = "/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userDetailsService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        System.out.println("create");
        String subject = "Thank you for creating an account";
        String text = "Your username is " + user.getUsername();
        EmailUtil.sendEmail(user.getEmail(), subject, text);

        return new ResponseEntity<User>(userDetailsService.createUser(user), HttpStatus.CREATED);
    }


    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        System.out.println(user);
        User toDelete = userDetailsService.getUserByUsername(jwtUtility.getUsernameFromToken(user.getUsername()));
        if(toDelete.getPassword().equals(user.getPassword())){
            userDetailsService.removeUser(toDelete);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
