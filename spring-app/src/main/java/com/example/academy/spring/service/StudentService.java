package com.example.academy.spring.service;

import com.example.academy.spring.model.Course;
import com.example.academy.spring.model.Student;
import com.example.academy.spring.messaging.ChangeEventPublisher;
import com.example.academy.spring.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private final ChangeEventPublisher publisher;

    public StudentService(StudentRepository studentRepository, CourseService courseService, ChangeEventPublisher publisher) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student get(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    @Transactional
    public Student create(Student student, Long courseId) {
        student.setId(null);
        student.setCourse(resolveCourse(courseId));
        Student saved = studentRepository.save(student);
        publisher.publish("Student", saved.getId(), "CREATE",
                Map.of("fullName", saved.getFullName(), "email", saved.getEmail(), "studyYear", saved.getStudyYear()));
        return saved;
    }

    @Transactional
    public Student update(Long id, Student data, Long courseId) {
        Student existing = get(id);
        existing.setFullName(data.getFullName());
        existing.setEmail(data.getEmail());
        existing.setStudyYear(data.getStudyYear());
        existing.setCourse(resolveCourse(courseId));
        publisher.publish("Student", existing.getId(), "UPDATE",
                Map.of("fullName", existing.getFullName(), "email", existing.getEmail(), "studyYear", existing.getStudyYear()));
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        Student existing = get(id);
        studentRepository.delete(existing);
        publisher.publish("Student", id, "DELETE",
                Map.of("fullName", existing.getFullName(), "email", existing.getEmail(), "studyYear", existing.getStudyYear()));
    }

    private Course resolveCourse(Long courseId) {
        if (courseId == null) {
            return null;
        }
        return courseService.get(courseId);
    }
}
