package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping(value="/get-news",produces = "application/json")
    public ResponseEntity<News> getNews(){
        return ResponseEntity.ok().body(newsService.findNewsById(1));
    }

    @PostMapping(value="/post-news",consumes="application/json", produces="application/json")
    public ResponseEntity<News> saveNews(@RequestBody)
}
