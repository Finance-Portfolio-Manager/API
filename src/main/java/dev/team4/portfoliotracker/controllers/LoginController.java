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
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping(consumes = "application/json")
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

    @GetMapping(produces = "application/json")
    public ResponseEntity<User> getUserByToken(@RequestParam(value = "token") String token) {
        System.out.println("HELLLLLO");
        return new ResponseEntity<>(userDetailsService.getUserByUsername(jwtUtility.getUsernameFromToken(token)), HttpStatus.OK);
    }

}
