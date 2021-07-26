package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.services.UserDetailsService;
import dev.team4.portfoliotracker.security.AuthenticationRequest;
import dev.team4.portfoliotracker.security.AuthenticationResponse;
import dev.team4.portfoliotracker.security.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createNewUser(@RequestBody User user){
        return new ResponseEntity<User>(userDetailsService.createUser(user), HttpStatus.CREATED);
    }

    @DeleteMapping(consumes = "application/json")
    public HttpStatus deleteUser(@RequestBody User user){
        return HttpStatus.OK;
    }

    @RequestMapping(value ="/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password, please try again.");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtility.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
