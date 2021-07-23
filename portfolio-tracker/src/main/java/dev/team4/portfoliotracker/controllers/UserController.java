package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }

    //will implement once JWT is complete
//    @PostMapping("/login")
//    public ResponseEntity<String> authenticate(@RequestParam("username") String username, @RequestParam("password") String pass){
//        if(user is valid){
//
//        }else{
//
//        }
//    }

    @DeleteMapping(consumes = "application/json")
    public ResponseEntity<String> deleteUser(@RequestBody User user){
        return ResponseEntity.ok("User deleted");
    }
}
