package com.skillshare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "learning_plans")
public class LearningPlan {
    @Id
    private String id;
    
    @Field("user_id")
    //userID
    private String userId;
    // title,thumbnail,skill
    private String title;
    private String thumbnail;
    private String skill;
    
    @Field("skill_level")
    //skillLevel,description,
    private String skillLevel;
    
    private String description;
    
    @Builder.Default
    private List<Lesson> lessons = new ArrayList<>();
    //duration
    private String duration;
    //CreateOne
    @Field("created_at")
    private Instant createdAt;
    //UpdateOne
    @Field("updated_at")
    private Instant updatedAt;
}
