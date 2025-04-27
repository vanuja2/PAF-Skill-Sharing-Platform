package com.skillshare.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillshare.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository; 
    private final NotificationRepository notificationRepository;

    
    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable String postId) {
        try {
            log.debug("Fetching comments for post: {}", postId);
            List<Comment> comments = commentRepository.findByPostId(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            log.error("Error fetching comments for post: {}", postId, e);
            throw new RuntimeException("Failed to fetch comments", e);
        }
    }
    
}
