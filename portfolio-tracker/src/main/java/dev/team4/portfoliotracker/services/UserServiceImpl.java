package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Override
    public void removeUser(User user){
        userRepository.delete(user);
    }

    @Override
    public boolean loginAttempt(String username, String password){
        User user = userRepository.findByUsername(username);
        if(user != null){
            if(password == user.getPassword()){
                //send JWT
                return true;
            }
        }
        return false;
    }
}
