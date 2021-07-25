package dev.team4.portfoliotracker.repositories;

import dev.team4.portfoliotracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
