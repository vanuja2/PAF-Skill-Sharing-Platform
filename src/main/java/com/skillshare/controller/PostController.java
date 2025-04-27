package com.skillshare.controller;

import com.skillshare.model.Post;
import com.skillshare.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            log.debug("Fetching all posts");
            List<Post> posts = postRepository.findAll();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error fetching posts", e);
            throw new RuntimeException("Failed to fetch posts", e);
        }
    }