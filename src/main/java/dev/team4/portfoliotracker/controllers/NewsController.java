package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.services.NewsService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<News> saveNews(
            @RequestBody String title, String description, String url, String urlToImage, int newsId){
        News news = new News(newsId, title, description, url, urlToImage);


        return new ResponseEntity<News>(newsService.saveNews(news), HttpStatus.OK);
    }

//    @PostMapping(value="/post-news",consumes="application/json", produces="application/json")
//    public ResponseEntity<News> saveNews(@RequestBody)
}
