package com.fashionstore.controller;

import com.fashionstore.dto.LoginRequest;
import com.fashionstore.dto.RegisterRequest;
import com.fashionstore.service.AuthService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }
    @PostMapping("/register")
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request) { return authService.register(request); }
    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequest request) { return authService.login(request); }
}
