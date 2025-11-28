package com.example.academy.spring.web;

import com.example.academy.spring.model.Course;
import com.example.academy.spring.model.Student;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    private final CourseService courseService;
    private final StudentService studentService;

    public ViewController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courseForm", new Course());
        model.addAttribute("studentForm", new Student());
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

    @PostMapping("/courses/{id}/update")
    public String updateCourse(@PathVariable Long id,
                               @RequestParam String code,
                               @RequestParam String title,
                               @RequestParam(required = false) String description) {
        Course updated = new Course();
        updated.setCode(code);
        updated.setTitle(title);
        updated.setDescription(description);
        courseService.update(id, updated);
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

    @PostMapping("/students/{id}/update")
    public String updateStudent(@PathVariable Long id,
                                @RequestParam String fullName,
                                @RequestParam String email,
                                @RequestParam Integer studyYear,
                                @RequestParam(required = false) Long courseId) {
        Student updated = new Student();
        updated.setFullName(fullName);
        updated.setEmail(email);
        updated.setStudyYear(studyYear);
        studentService.update(id, updated, courseId);
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
        return "index";
    }
}
