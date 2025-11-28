package com.example.academy.service;

import com.example.academy.entity.Course;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class CourseService {

    @PersistenceContext(unitName = "academyPU")
    private EntityManager em;

    public List<Course> findAll() {
        return em.createQuery("SELECT c FROM Course c ORDER BY c.code", Course.class)
                .getResultList();
    }

    public Course find(Long id) {
        return em.find(Course.class, id);
    }

    public Course create(Course course) {
        course.setId(null);
        em.persist(course);
        return course;
    }

    public Course update(Long id, Course data) {
        Course existing = find(id);
        if (existing == null) {
            throw new NotFoundException("Course not found");
        }
        existing.setCode(data.getCode());
        existing.setTitle(data.getTitle());
        existing.setDescription(data.getDescription());
        return existing;
    }

    public void delete(Long id) {
        Course existing = find(id);
        if (existing == null) {
            throw new NotFoundException("Course not found");
        }
        em.remove(existing);
    }
}
