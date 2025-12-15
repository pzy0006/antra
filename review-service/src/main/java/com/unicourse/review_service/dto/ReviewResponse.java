package com.unicourse.review_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private Long id;
    private Long courseId;
    private Long userId; 
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
