package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

   // public User getUserByLogin(String username, String password){
   //     return userRepository.findBy
   // }

}
