package com.example.academy.spring.api;

import com.example.academy.spring.api.dto.StudentPayload;
import com.example.academy.spring.model.Student;
import com.example.academy.spring.service.StudentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.academy.spring.api.dto.StudentList;

@RestController
@RequestMapping(value = "/api/students", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public StudentList all() {
        return StudentList.of(studentService.findAll());
    }

    @GetMapping("/{id}")
    public Student one(@PathVariable Long id) {
        return studentService.get(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Student> create(@RequestBody @Valid StudentPayload payload) {
        Student created = studentService.create(toEntity(payload), payload.getCourseId());
        return ResponseEntity.created(URI.create("/api/students/" + created.getId())).body(created);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Student update(@PathVariable Long id, @RequestBody @Valid StudentPayload payload) {
        return studentService.update(id, toEntity(payload), payload.getCourseId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Student toEntity(StudentPayload payload) {
        Student student = new Student();
        student.setFullName(payload.getFullName());
        student.setEmail(payload.getEmail());
        student.setStudyYear(payload.getStudyYear());
        return student;
    }
}
