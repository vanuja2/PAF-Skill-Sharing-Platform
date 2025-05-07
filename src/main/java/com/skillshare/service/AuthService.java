package com.skillshare.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.skillshare.controller.AuthController.AuthResponse;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

     public AuthResponse login(String email, String password) {
        log.debug("Attempting to login user with email: {}", email);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.debug("Invalid password for user: {}", email);
            throw new RuntimeException("Invalid password");
        }
        
        log.debug("Login successful for user: {}", email);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, mapToUserResponse(user));
    }

    public AuthResponse register(
        String email,
        String password,
        String firstName,
        String lastName,
        String address,
        String birthday
    ) 
}
