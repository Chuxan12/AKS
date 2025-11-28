package com.example.academy.spring.api.dto;

import com.example.academy.spring.model.Student;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "students")
public class StudentList {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "student")
    private List<Student> items;

    public StudentList() {
    }

    public StudentList(List<Student> items) {
        this.items = items;
    }

    public static StudentList of(List<Student> items) {
        return new StudentList(items);
    }

    public List<Student> getItems() {
        return items;
    }

    public void setItems(List<Student> items) {
        this.items = items;
    }
}
