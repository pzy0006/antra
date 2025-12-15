package com.unicourse.course_service.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {
    private String title;
    private String code;
    private Integer creditHours;
    private UUID instructorId;
    private String description;
}
