package dev.team4.portfoliotracker.controllers;

import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.services.NewsService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping(value="/get-news",produces = "application/json")
    public ResponseEntity<List<News>> getNews(){
        return ResponseEntity.ok().body(newsService.getNewsList());
    }

    /**
     * Not for use with front-end
     *
     * Allows news object to be created and persisted to database via JSON string in request body
     * @param news
     * @return Response Entity of added News
     */
    @PostMapping(value="/post-news",consumes="application/json", produces="application/json")
    public ResponseEntity<News> saveNews(@RequestBody News news){
        return new ResponseEntity<News>(newsService.saveNews(news), HttpStatus.OK);
    }

    /**
     * Not for use with front-end
     *
     * Intended to take in JSON object which holds multiple news objects
     * @param newsList
     * @return
     */
    @PostMapping(value="/post-more-news", consumes="application/json",produces="application/json")
    public ResponseEntity<List<News>> saveMoreNews(@RequestBody List<News> newsList){
        return new ResponseEntity<List<News>>(newsService.saveAllNews(newsList), HttpStatus.OK);
    }

//    @PostMapping(value="/post-news",consumes="application/json", produces="application/json")
//    public ResponseEntity<News> saveNews(@RequestBody)
}
