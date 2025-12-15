package com.unicourse.course_service.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unicourse.course_service.dto.CourseRequest;
import com.unicourse.course_service.dto.CourseResponse;
import com.unicourse.course_service.exception.ResourceNotFoundException;
import com.unicourse.course_service.model.Course;
import com.unicourse.course_service.repo.CourseRepository;
import com.unicourse.course_service.service.CourseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        if(courseRepository.findByCode(courseRequest.getCode()).isPresent()){
            throw new RuntimeException("Course code" + courseRequest.getCode() + " already exists.");
        }
        Course course = Course.builder()
        .id(UUID.randomUUID())
        .code(courseRequest.getCode())
        .title(courseRequest.getTitle())
        .creditHours(courseRequest.getCreditHours())
        .instructorId(courseRequest.getInstructorId())
        .description(courseRequest.getDescription())
        .build();

        Course savedCourse  = courseRepository.save(course);
        log.info("Course {} created successfully.", savedCourse.getId());
        return mapToCourseResponse(savedCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(UUID id) {
        Course course = courseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        return mapToCourseResponse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream().map(this::mapToCourseResponse).collect(Collectors.toList());
    }

    @Override
    public CourseResponse updateCourse(UUID id, CourseRequest courseRequest) {
        Course courseToUpdate = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found for update: " + id));
        courseToUpdate.setTitle(courseRequest.getTitle());
        courseToUpdate.setDescription(courseRequest.getDescription());
        courseToUpdate.setCreditHours(courseRequest.getCreditHours());

        Course updatCourse = courseRepository.save(courseToUpdate);
        log.info("Course {} updated successfully", updatCourse.getId());

        return mapToCourseResponse(updatCourse);
    }

    private CourseResponse mapToCourseResponse(Course course){
        return CourseResponse.builder()
        .id(course.getId())
        .title(course.getTitle())
        .code(course.getCode())
        .creditHours(course.getCreditHours())
        .instructorId(course.getInstructorId())
        .description(course.getDescription())
        .build();
    }

    
}
