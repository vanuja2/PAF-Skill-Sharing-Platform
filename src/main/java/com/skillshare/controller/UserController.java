package com.skillshare.controller;

import com.skillshare.model.MediaItem;
import com.skillshare.model.User;
import com.skillshare.repository.UserRepository;
import com.skillshare.service.MediaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MediaService mediaService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            // Clear sensitive data before returning the users
            users.forEach(user -> {
                user.setPassword(null);
                user.setEmail(null);
                user.setAddress(null);
                user.setBirthday(null);
            });
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Error fetching all users", e);
            return ResponseEntity.status(500).body(null);
        }
    }