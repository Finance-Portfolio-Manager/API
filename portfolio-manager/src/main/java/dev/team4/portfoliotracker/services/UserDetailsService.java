package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.User;
import dev.team4.portfoliotracker.repositories.UserRepository;
import dev.team4.portfoliotracker.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(dev.team4.portfoliotracker.models.User user){
        return userRepository.save(user);
    }

    public void removeUser(dev.team4.portfoliotracker.models.User user){
        userRepository.delete(user);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}