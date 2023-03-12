package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Role;
import com.example.moneytransfer.data.entities.User;
import com.example.moneytransfer.data.repositories.UserRepository;
import com.example.moneytransfer.payloads.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegistrationRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getPassword());
        return (User) this.userRepository.save(user);
    }
}
