package com.example.academy.spring.service;

import com.example.academy.spring.model.Course;
import com.example.academy.spring.model.Student;
import com.example.academy.spring.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
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
        return studentRepository.save(student);
    }

    @Transactional
    public Student update(Long id, Student data, Long courseId) {
        Student existing = get(id);
        existing.setFullName(data.getFullName());
        existing.setEmail(data.getEmail());
        existing.setStudyYear(data.getStudyYear());
        existing.setCourse(resolveCourse(courseId));
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    private Course resolveCourse(Long courseId) {
        if (courseId == null) {
            return null;
        }
        return courseService.get(courseId);
    }
}
