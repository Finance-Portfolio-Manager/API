package dev.team4.portfoliotracker.util;


import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.NewsApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UpdateNewsTest {
    @Autowired
    UpdateNews updateNews;

    @Autowired
    NewsApiResponse newsApiResponse;

    @Autowired
    News news;

    @Test
    public void updateNewsTest(){
        List<News> newsList = updateNews.updateDailyNews();

        System.out.println(newsList.get(0));
        assertNotNull(newsList.get(0).getNewsId());
    }

}
