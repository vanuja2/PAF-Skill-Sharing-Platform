package com.skillshare.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

Slf4j
@RestController
@RequestMapping("/learning-plans")
@RequiredArgsConstructor
public class LearningPlanController {
     private final LearningPlanRepository learningPlanRepository;

    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans(
        @RequestParam(required = false) String skill,
        @RequestParam(required = false) String skillLevel
    ) {
        try {
            List<LearningPlan> plans;
            
            if (skill != null && skillLevel != null) {
                plans = learningPlanRepository.findAll().stream()
                    .filter(plan -> plan.getSkill().equals(skill) && plan.getSkillLevel().equals(skillLevel))
                    .toList();
            } else if (skill != null) {
                plans = learningPlanRepository.findBySkill(skill);
            } else if (skillLevel != null) {
                plans = learningPlanRepository.findBySkillLevel(skillLevel);
            } else {
                plans = learningPlanRepository.findAll();
            }
            
            return ResponseEntity.ok(plans);
        } catch (Exception e) {
            log.error("Error fetching learning plans", e);
            throw new RuntimeException("Failed to fetch learning plans", e);
        }
    }
     @GetMapping("/my-plans")
    public ResponseEntity<List<LearningPlan>> getMyLearningPlans() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userId = auth.getName();
            
            List<LearningPlan> plans = learningPlanRepository.findByUserId(userId);
            return ResponseEntity.ok(plans);
        } catch (Exception e) {
            log.error("Error fetching user's learning plans", e);
            throw new RuntimeException("Failed to fetch learning plans", e);
        }
    }

     @PostMapping
    public ResponseEntity<LearningPlan> createLearningPlan(@RequestBody LearningPlan plan) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userId = auth.getName();
            
            LearningPlan newPlan = LearningPlan.builder()
                .userId(userId)
                .title(plan.getTitle())
                .thumbnail(plan.getThumbnail())
                .skill(plan.getSkill())
                .skillLevel(plan.getSkillLevel())
                .description(plan.getDescription())
                .lessons(plan.getLessons())
                .duration(plan.getDuration())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
                
            LearningPlan savedPlan = learningPlanRepository.save(newPlan);
            return ResponseEntity.ok(savedPlan);
        } catch (Exception e) {
            log.error("Error creating learning plan", e);
            throw new RuntimeException("Failed to create learning plan", e);
        }
    }

}  
