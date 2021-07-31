package dev.team4.portfoliotracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@CrossOrigin
@RequestMapping(value = "/")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<String> healthCheck(HttpServletResponse response) throws IOException {
        String string = "{\"status\" : \"UP\" }";
        return new ResponseEntity<>(string, HttpStatus.OK);
    }
}
