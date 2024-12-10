package com.example.casestudy.service;

import com.example.casestudy.model.User;
import com.example.casestudy.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public User authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
