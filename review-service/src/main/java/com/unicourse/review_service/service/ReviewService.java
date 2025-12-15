package com.unicourse.review_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unicourse.review_service.dto.ReviewRequest;
import com.unicourse.review_service.dto.ReviewResponse;

@Service
public interface ReviewService {

    ReviewResponse addReview(ReviewRequest reviewRequest);
    
    List<ReviewResponse> getReviewsByCourseId(Long courseId);
    
    ReviewResponse getReviewById(Long id);
    
    void deleteReview(Long id);
}
