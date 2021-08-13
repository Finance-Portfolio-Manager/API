package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Integer> {

    News findById(int id);
    List<News> findAll();

}