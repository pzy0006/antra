package com.unicourse.review_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicourse.review_service.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long>{
    List<Review> findByCourseId(Long courseId);
    Optional<Review> findByCourseIdAndUserId(Long courseId, Long userId);

}
