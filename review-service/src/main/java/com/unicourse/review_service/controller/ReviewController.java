package com.unicourse.review_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unicourse.review_service.dto.ReviewRequest;
import com.unicourse.review_service.dto.ReviewResponse;
import com.unicourse.review_service.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse addReview(@RequestBody @Valid ReviewRequest reviewRequest) {
        return reviewService.addReview(reviewRequest);
    }


    @GetMapping("/courses/{courseId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getReviewsByCourseId(@PathVariable Long courseId) {
        return reviewService.getReviewsByCourseId(courseId);
    }
    

    @GetMapping("/reviews/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponse getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }
    

    @DeleteMapping("/reviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
