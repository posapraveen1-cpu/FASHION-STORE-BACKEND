package com.fashionstore.service;

import com.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.model.User;
import com.fashionstore.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository users;
    public UserService(UserRepository users) { this.users = users; }
    public User getByEmail(String email) {
        return users.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
