package com.fashionstore.service;

import com.fashionstore.config.JwtService;
import com.fashionstore.dto.LoginRequest;
import com.fashionstore.dto.RegisterRequest;
import com.fashionstore.model.Role;
import com.fashionstore.model.User;
import com.fashionstore.repository.UserRepository;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.users = users; this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager; this.jwtService = jwtService;
    }

    public Map<String, Object> register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        if (users.existsByEmail(email)) throw new IllegalArgumentException("An account with this email already exists");
        User user = users.save(new User(request.name().trim(), email, passwordEncoder.encode(request.password()), Role.CUSTOMER));
        return response(user);
    }

    public Map<String, Object> login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));
        return response(users.findByEmail(email).orElseThrow());
    }

    private Map<String, Object> response(User user) {
        return Map.of("id", user.getId(), "name", user.getName(), "email", user.getEmail(),
                "role", user.getRole().name(), "token", jwtService.createToken(user.getEmail()));
    }
}
