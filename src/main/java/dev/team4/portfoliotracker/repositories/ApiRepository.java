package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiRepository extends JpaRepository<News,Integer> {

    boolean saveNews(News news);
    List<News> findAllNews();
}
