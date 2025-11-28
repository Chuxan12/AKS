package com.example.academy.spring.repository;

import com.example.academy.spring.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
