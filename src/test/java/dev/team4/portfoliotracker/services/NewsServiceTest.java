package dev.team4.portfoliotracker.services;


import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.NewsApiResponse;
import dev.team4.portfoliotracker.repositories.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
public class NewsServiceTest {

    private MockMvc mvc;

    @MockBean
    NewsRepository newsRepository;

    @Autowired
    News news;

    @Autowired
    NewsService newsService;

    @BeforeEach
    public void setUp(){
        this.mvc = MockMvcBuilders.standaloneSetup(newsRepository).build();
    }

    @Test
    public void newsObjectIsReturnedTest(){
        News news1 = new News(1,"test Title","test Description","Test URL", "Test IMG URL");
        News news2 = new News(2,"test Title","test Description","Test URL", "Test IMG URL");
        News news3 = new News(3,"test Title","test Description","Test URL", "Test IMG URL");
        News news4 = new News(4,"test Title","test Description","Test URL", "Test IMG URL");
        News news5 = new News(5,"test Title","test Description","Test URL", "Test IMG URL");

        List<News> newsList = Arrays.asList(news1,news2,news3,news4,news5);
        doReturn(newsList).when(newsRepository).findAllNews();

        assertEquals(newsList,newsService.getNewsList());

//
//        when(mockNewsRepository.findAllNews()).thenReturn(newsList);
//
//
////        Mockito.doReturn(newsList).when(mockNewsRepository).findAllNews();
//
//        NewsRepository newsRepository = applicationContext.getBean(NewsRepository.class);
//        System.out.println(newsRepository.findAllNews());
//        assertEquals(newsList,newsService.getNewsList());

    }
}
