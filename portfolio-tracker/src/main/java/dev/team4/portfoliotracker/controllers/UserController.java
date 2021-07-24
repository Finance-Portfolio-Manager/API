package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
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
        userService.removeUser(user);
        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }
}
