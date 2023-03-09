package com.kfh.service.courses.repositories;

import com.kfh.service.courses.entities.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
