package com.skillshare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressTemplate {
    private String type;
    
    @Builder.Default
    private List<String> completed = new ArrayList<>();
    
    
