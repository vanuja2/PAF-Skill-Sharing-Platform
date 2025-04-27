package com.skillshare.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
