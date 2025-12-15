package com.unicourse.course_service.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course") // usually it is t_course
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    private UUID id;

    private String title;

    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @Column(name = "credit_hours", nullable = false)
    private Integer creditHours;

    @Column(name = "instructor_id")
    private UUID instructorId;

    private String description;


}
