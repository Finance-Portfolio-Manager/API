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
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtility jwtUtility;


//    @GetMapping(produces = "application/json")
//    public ResponseEntity<List<User>> getAllAccounts() {
//        return new ResponseEntity<>(userDetailsService.checkAllUser(), HttpStatus.OK);
//    }


    @GetMapping(value = "/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userDetailsService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createNewUser(@RequestBody User user){
//        System.out.println("create");
//        String subject = "Thank you for creating an account";
//        String text = "Your username is " + user.getUsername();
//        EmailUtil.sendEmail(user.getEmail(), subject, text);

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


    //fuming
    @PostMapping(value = "/code")
    public ResponseEntity<String> generateCode(@RequestParam(value = "email") String email) {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String digits = String.format("%06d", number);
        System.out.println(digits);
        System.out.println(email);
        User user = userDetailsService.getUserByEmail(email);
        if(user == null){
            System.out.println("User not found" );
            return new ResponseEntity<String>("User not found", HttpStatus.UNAUTHORIZED);
        }
        user.setCode(digits);
        userDetailsService.createUser(user);
        String subject = "Here is confirmation code for your account";
        String text = "confirmation code: " + digits +"\nYou can copy this code and paste it to your form to complete the email verification process";

        EmailUtil.sendEmail(email,subject, text);
        return new ResponseEntity<String>(digits, HttpStatus.CREATED);
    }

    //fuming
    @PostMapping(value = "/password", consumes = "application/json")
    public ResponseEntity<String> changePassword(@RequestBody User oldUser) {
        System.out.println(oldUser);
        User user = userDetailsService.getUserByEmail(oldUser.getEmail());
        if(!oldUser.getCode().equals(user.getCode())){
            System.out.println("code does not match code in database: " + user.getCode() + " code entered: " + oldUser.getCode() );
            return new ResponseEntity<>("code does not match", HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(oldUser.getPassword());
        user.setCode(null);
        userDetailsService.createUser(user);
        return new ResponseEntity<String>("password changed", HttpStatus.OK);
    }

}
