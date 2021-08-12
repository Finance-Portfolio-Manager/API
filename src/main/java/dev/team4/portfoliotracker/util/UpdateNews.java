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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

@Component
public class UpdateNews {

    @Autowired
    NewsService newsService;

    @Autowired
    Environment environment;

    /**
     * Author: David Garcia
     * Sends http request to news api, parses news fields to make sure they have valid lengths
     * for persisting into database
     * @return List of News which has been persisted to the H2 database
     */

    public List<News> updateDailyNews() {
        String targetUrl = "https://newsapi.org/v2/everything?q=stocks&from=2021-08-09&to=2021-08-09&sortBy=popularity&domains=forbes.com&apiKey=" + environment.getProperty("NEWS_API_KEY") + "&pageSize=5&page=1";

        URL url = null;
        try {
            url = new URL(targetUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(targetUrl))
//                .header("Accept","application/json")
//                .build();
//
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofString());

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept","application/json");
            int status = con.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer content = new StringBuffer();

        try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));){
            String inputLine;
            while((inputLine = in.readLine())!= null ){
                content.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        con.disconnect();

        ObjectMapper objectMapper2 = new ObjectMapper();
        NewsApiResponse newsApiResponse = null;
        try {
            newsApiResponse = objectMapper2.readValue(content.toString(), NewsApiResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(targetUrl))
//                .header("Accept", "application/json")
//                .build();
//
//        HttpResponse<String> response = null;
//        try {
//            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        NewsApiResponse newsApiResponse = null;
//        try {
//            assert response != null;
//            newsApiResponse = objectMapper2.readValue(response.body(), NewsApiResponse.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        try{
            if(!("ok").equals(newsApiResponse.getStatus())){

            } else {
                List<News> newsList = newsApiResponse.getArticles();
                int z = 1;
                for (int i = 0; i < newsList.size(); i++) {

                    newsList.get(i).setNewsId(z);
                    z++;
//                    newsList.get(i).setTitle(newsList.get(i).getTitle().substring(0,200));
                    if(newsList.get(i).getTitle().length() > 201){
                        newsList.get(i).setTitle(newsList.get(i).getTitle().substring(0,200));
                    }
                    if(newsList.get(i).getDescription().length() > 201){
                        newsList.get(i).setDescription(newsList.get(i).getDescription().substring(0,200));
                    }

                }
                newsService.saveAllNews(newsList);
                return newsList;
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }
}

