package dev.team4.portfoliotracker.util;


import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.NewsApiResponse;
import dev.team4.portfoliotracker.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateNews {

    @Autowired
    ApiService apiService;

    public void updateDailyNews(){
        System.out.println("test");
//        NewsApiResponse newsApiResponse = apiService.getNewsApiResponseObject();
//
//        if(!("ok").equals(newsApiResponse.getStatus())){
//            // if status is not 200, handle that.
//        }
//
//        List<News> news = newsApiResponse.getArticles();
//
//        for(int i = 0; i < news.size() - 1; i++){
//            news.get(i).setNewsId(i++);
        }
}

