package com.example.academy.spring.api;

import com.example.academy.spring.model.Course;
import com.example.academy.spring.service.CourseService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    private final CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> all() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public Course one(@PathVariable Long id) {
        return courseService.get(id);
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody @Valid Course course) {
        Course created = courseService.create(course);
        return ResponseEntity.created(URI.create("/api/courses/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody @Valid Course course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
