package com.example.academy.spring.service;

import com.example.academy.spring.model.Course;
import com.example.academy.spring.messaging.ChangeEventPublisher;
import com.example.academy.spring.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ChangeEventPublisher publisher;

    public CourseService(CourseRepository courseRepository, ChangeEventPublisher publisher) {
        this.courseRepository = courseRepository;
        this.publisher = publisher;
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
        Course saved = courseRepository.save(course);
        publisher.publish("Course", saved.getId(), "CREATE",
                Map.of("code", saved.getCode(), "title", saved.getTitle()));
        return saved;
    }

    @Transactional
    public Course update(Long id, Course data) {
        Course existing = get(id);
        existing.setCode(data.getCode());
        existing.setTitle(data.getTitle());
        existing.setDescription(data.getDescription());
        publisher.publish("Course", existing.getId(), "UPDATE",
                Map.of("code", existing.getCode(), "title", existing.getTitle()));
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
        publisher.publish("Course", id, "DELETE", Map.of());
    }
}
