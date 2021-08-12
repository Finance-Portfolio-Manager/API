package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.Stock;
import dev.team4.portfoliotracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    //User findUserByUserId(int userId);

    User findByUserId(int userId);
    List<User> findByEmail(String email);
}
