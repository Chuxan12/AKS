package com.example.academy.service;

import com.example.academy.entity.Course;
import com.example.academy.entity.Student;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class StudentService {

    @PersistenceContext(unitName = "academyPU")
    private EntityManager em;

    @EJB
    private CourseService courseService;

    public List<Student> findAll() {
        return em.createQuery(
                        "SELECT s FROM Student s LEFT JOIN FETCH s.course ORDER BY s.fullName",
                        Student.class)
                .getResultList();
    }

    public Student find(Long id) {
        return em.find(Student.class, id);
    }

    public Student create(Student student, Long courseId) {
        student.setId(null);
        student.setCourse(resolveCourse(courseId));
        em.persist(student);
        return student;
    }

    public Student update(Long id, Student data, Long courseId) {
        Student existing = find(id);
        if (existing == null) {
            throw new NotFoundException("Student not found");
        }
        existing.setFullName(data.getFullName());
        existing.setEmail(data.getEmail());
        existing.setStudyYear(data.getStudyYear());
        existing.setCourse(resolveCourse(courseId));
        return existing;
    }

    public void delete(Long id) {
        Student existing = find(id);
        if (existing == null) {
            throw new NotFoundException("Student not found");
        }
        em.remove(existing);
    }

    private Course resolveCourse(Long courseId) {
        if (courseId == null) {
            return null;
        }
        Course course = courseService.find(courseId);
        if (course == null) {
            throw new NotFoundException("Course not found");
        }
        return course;
    }
}
