package com.example.academy.spring.web;

import com.example.academy.spring.model.Course;
import com.example.academy.spring.model.Student;
import com.example.academy.spring.repository.ChangeLogRepository;
import com.example.academy.spring.service.CourseService;
import com.example.academy.spring.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    private final CourseService courseService;
    private final StudentService studentService;
    private final ChangeLogRepository changeLogRepository;

    public ViewController(CourseService courseService, StudentService studentService, ChangeLogRepository changeLogRepository) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.changeLogRepository = changeLogRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courseForm", new Course());
        model.addAttribute("studentForm", new Student());
        model.addAttribute("changeLogs", changeLogRepository.findTop50ByOrderByCreatedAtDesc());
        return "index";
    }

    @PostMapping("/courses")
    public String createCourse(@ModelAttribute("courseForm") @Valid Course course,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return reloadIndex(model, bindingResult);
        }
        courseService.create(course);
        return "redirect:/";
    }

    @PostMapping("/students")
    public String createStudent(@ModelAttribute("studentForm") @Valid Student student,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return reloadIndex(model, bindingResult);
        }
        Long courseId = student.getCourse() != null ? student.getCourse().getId() : null;
        studentService.create(student, courseId);
        return "redirect:/";
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/";
    }

    private String reloadIndex(Model model, BindingResult bindingResult) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("errors", bindingResult.getAllErrors());
        model.addAttribute("changeLogs", changeLogRepository.findTop50ByOrderByCreatedAtDesc());
        return "index";
    }
}
