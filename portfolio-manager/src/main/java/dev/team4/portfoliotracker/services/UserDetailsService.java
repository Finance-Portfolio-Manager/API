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

    /**
     * Adds user to the database
     * @param user
     * @return added User
     */
    public User createUser(dev.team4.portfoliotracker.models.User user){
        return userRepository.save(user);
    }

    /**
     * Deletes user matching username from the database
     * @param user
     */
    public void removeUser(dev.team4.portfoliotracker.models.User user){
        if(user.getUsername() == null){
            throw new UsernameNotFoundException(user.getUsername());
        } else {
            userRepository.delete(user);
        }
    }

    /**
     * Gets User matching username
     * @param username
     * @return User matching username
     */
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * stores User matching username in secure UserDetails
     *@param username
     *@return UserDetails holding user
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}