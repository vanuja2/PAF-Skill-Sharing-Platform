package com.skillshare.service;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    
}
