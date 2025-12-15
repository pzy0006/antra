package com.unicourse.course_service.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicourse.course_service.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>{
    Optional<Course> findByCode(String code);

}
