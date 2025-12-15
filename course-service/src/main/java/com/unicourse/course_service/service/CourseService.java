package com.unicourse.course_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.unicourse.course_service.dto.CourseRequest;
import com.unicourse.course_service.dto.CourseResponse;

@Service
public interface CourseService {
    CourseResponse createCourse(CourseRequest courseRequest);
    CourseResponse getCourseById(UUID id);
    List<CourseResponse> getAllCourses();
    CourseResponse updateCourse(UUID id, CourseRequest courseRequest);
}
