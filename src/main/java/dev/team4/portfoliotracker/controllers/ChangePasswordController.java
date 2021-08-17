package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.UserDetailsService;
import dev.team4.portfoliotracker.security.JwtUtility;
import dev.team4.portfoliotracker.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@CrossOrigin

@RequestMapping(value = "/password")
public class ChangePasswordController {

    @Autowired
    private UserDetailsService userDetailsService;

    //fuming
    @GetMapping()
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
    @PostMapping(consumes = "application/json")
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
