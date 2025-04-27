package com.skillshare.controller;

import com.skillshare.model.Notification;
import com.skillshare.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(
        @RequestParam(required = false) Boolean unreadOnly
    ) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userId = auth.getName();
            
            List<Notification> notifications = unreadOnly != null && unreadOnly
                ? notificationRepository.findByUserIdAndReadOrderByCreatedAtDesc(userId, false)
                : notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
                
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            log.error("Error fetching notifications", e);
            throw new RuntimeException("Failed to fetch notifications", e);
        }
    }
}
