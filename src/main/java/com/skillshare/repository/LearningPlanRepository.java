package com.skillshare.repository;

//import
import com.skillshare.model.LearningPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
//lerningPlansrepository
public interface LearningPlanRepository extends MongoRepository<LearningPlan, String> {
    List<LearningPlan> findByUserId(String userId);
    List<LearningPlan> findBySkill(String skill);
    List<LearningPlan> findBySkillLevel(String skillLevel);
}