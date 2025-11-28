package com.example.academy.spring.service;

import com.example.academy.spring.model.Course;
import com.example.academy.spring.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course get(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    @Transactional
    public Course create(Course course) {
        course.setId(null);
        return courseRepository.save(course);
    }

    @Transactional
    public Course update(Long id, Course data) {
        Course existing = get(id);
        existing.setCode(data.getCode());
        existing.setTitle(data.getTitle());
        existing.setDescription(data.getDescription());
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
