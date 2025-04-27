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
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        try {
            return userRepository.findById(id)
                .map(user -> {
                    // Clear sensitive data
                    user.setPassword(null);
                    user.setEmail(null);
                    user.setAddress(null);
                    user.setBirthday(null);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error fetching user: {}", id, e);
            throw new RuntimeException("Failed to fetch user", e);
        }
    }

    @GetMapping("/{id}/private")
public ResponseEntity<User> getPrivateProfile(@PathVariable String id) {
    try {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        // Users can only access their own private profile
        if (!userId.equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return userRepository.findById(id)
            .map(user -> {
                user.setPassword(null);  // Don't expose the password
                return ResponseEntity.ok(user);
            })
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());  // Return 404 if user is not found
    } catch (Exception e) {
        log.error("Error fetching private profile for user ID: {}", id, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Return 500 on any error
    }
}
@PutMapping("/{id}")
public ResponseEntity<User> updateUser(
    @PathVariable String id,
    @RequestBody User updatedUser
) {
    try {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        if (!userId.equals(id)) {
            return ResponseEntity.status(403).build();
        }

        return userRepository.findById(id)
            .map(existingUser -> {
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                existingUser.setAddress(updatedUser.getAddress());
                existingUser.setBirthday(updatedUser.getBirthday());
                existingUser.setAvatarUrl(updatedUser.getAvatarUrl());
                existingUser.setBio(updatedUser.getBio());
                existingUser.setUpdatedAt(Instant.now());

                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                User savedUser = userRepository.save(existingUser);
                savedUser.setPassword(null);
                return ResponseEntity.ok(savedUser);
            })
            .orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
        log.error("Error updating user: {}", id, e);
        throw new RuntimeException("Failed to update user", e);
    }
}