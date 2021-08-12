package dev.team4.portfoliotracker.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.NewsApiResponse;
import dev.team4.portfoliotracker.services.ApiService;
import dev.team4.portfoliotracker.services.NewsService;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class UpdateNews {

    @Autowired
    NewsService newsService;

    @Autowired
    Environment environment;

    public List<News> updateDailyNews() {
        System.out.println("test");
        String targetUrl = "https://newsapi.org/v2/everything?q=stocks&from=2021-08-09&to=2021-08-09&sortBy=popularity&domains=forbes.com&apiKey=" + environment.getProperty("NEWS_API_KEY") + "&pageSize=5&page=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper2 = new ObjectMapper();
        NewsApiResponse newsApiResponse = null;
        try {
            assert response != null;
            newsApiResponse = objectMapper2.readValue(response.body(), NewsApiResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try{
            if(!("ok").equals(newsApiResponse.getStatus())){

            } else {
                List<News> newsList = newsApiResponse.getArticles();
                int z = 1;
                for (int i = 0; i < newsList.size() - 1; i++) {
                    newsList.get(i).setNewsId(z);
//                    newsList.get(i).setTitle(newsList.get(i).getTitle().substring(0,200));
                    if(newsList.get(i).getTitle().length() > 201){
                        newsList.get(i).setTitle(newsList.get(i).getTitle().substring(0,200));
                    }
                    if(newsList.get(i).getDescription().length() > 201){
                        newsList.get(i).setDescription(newsList.get(i).getDescription().substring(0,200));
                    }
                    z++;
                }
                System.out.println(newsList);
                newsService.saveAllNews(newsList);
                return newsList;
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }
}

