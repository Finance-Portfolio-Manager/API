package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.News;
import dev.team4.portfoliotracker.models.NewsApiResponse;
import dev.team4.portfoliotracker.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author: David Garcia
 */

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private News news;


    public List<News> getNewsList(){
        return newsRepository.findAll();
    }

    public News saveNews(News news){
        return newsRepository.saveAndFlush(news);
    }
    public News findNewsById(int id){
        return newsRepository.findById(id);
    }

    @Transactional
    public List<News> saveAllNews(List<News> newsList){
        return newsRepository.saveAll(newsList);
    }

}
