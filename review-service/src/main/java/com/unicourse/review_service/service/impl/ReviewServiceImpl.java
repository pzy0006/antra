package com.unicourse.review_service.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicourse.review_service.dto.ReviewRequest;
import com.unicourse.review_service.dto.ReviewResponse;
import com.unicourse.review_service.exception.DuplicateReviewException;
import com.unicourse.review_service.exception.ResourceNotFoundException;
import com.unicourse.review_service.model.Review;
import com.unicourse.review_service.repository.ReviewRepository;
import com.unicourse.review_service.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponse addReview(ReviewRequest reviewRequest) {
        if (reviewRepository.findByCourseIdAndUserId(reviewRequest.getCourseId(), reviewRequest.getUserId()).isPresent()) {
            throw new DuplicateReviewException(
                String.format("User %d has already submitted a review for course %d.", 
                reviewRequest.getUserId(), reviewRequest.getCourseId())
            );
        }
        
        Review review = Review.builder()
                .courseId(reviewRequest.getCourseId())
                .userId(reviewRequest.getUserId())
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .createdAt(LocalDateTime.now()) 
                .build();
        
        Review savedReview = reviewRepository.save(review);
        log.info("Review {} added for course {}.", savedReview.getId(), savedReview.getCourseId());
        
        return mapToReviewResponse(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByCourseId(Long courseId) {
        return reviewRepository.findByCourseId(courseId).stream()
                .map(this::mapToReviewResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public ReviewResponse getReviewById(Long id) {
         Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id)); 
         
         return mapToReviewResponse(review);
    }
    
    @Override
    public void deleteReview(Long id) {
         Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found for deletion: " + id)); 
         
         reviewRepository.delete(review);
         log.info("Review {} deleted.", id);
    }

    private ReviewResponse mapToReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .courseId(review.getCourseId())
                .userId(review.getUserId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
