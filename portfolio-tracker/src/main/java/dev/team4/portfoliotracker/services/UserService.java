package dev.team4.portfoliotracker.services;

import dev.team4.portfoliotracker.models.User;

public interface UserService {

    public User createUser(User user);

    public void removeUser(User user);

    public boolean loginAttempt(String username, String password);

}
