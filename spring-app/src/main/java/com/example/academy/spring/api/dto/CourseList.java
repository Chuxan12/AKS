package com.example.academy.spring.api.dto;

import com.example.academy.spring.model.Course;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "courses")
public class CourseList {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "course")
    private List<Course> items;

    public CourseList() {
    }

    public CourseList(List<Course> items) {
        this.items = items;
    }

    public static CourseList of(List<Course> items) {
        return new CourseList(items);
    }

    public List<Course> getItems() {
        return items;
    }

    public void setItems(List<Course> items) {
        this.items = items;
    }
}
