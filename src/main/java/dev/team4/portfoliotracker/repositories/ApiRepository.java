package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<News,Integer> {


}
