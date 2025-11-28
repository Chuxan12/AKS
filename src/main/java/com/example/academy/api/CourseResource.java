package com.example.academy.api;

import com.example.academy.entity.Course;
import com.example.academy.service.CourseService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    @EJB
    private CourseService courseService;

    @GET
    public List<Course> findAll() {
        return courseService.findAll();
    }

    @GET
    @Path("{id}")
    public Course findOne(@PathParam("id") Long id) {
        Course course = courseService.find(id);
        if (course == null) {
            throw new NotFoundException("Course not found");
        }
        return course;
    }

    @POST
    public Response create(Course course) {
        Course created = courseService.create(course);
        return Response.created(URI.create("/api/courses/" + created.getId()))
                .entity(created)
                .build();
    }

    @PUT
    @Path("{id}")
    public Course update(@PathParam("id") Long id, Course course) {
        return courseService.update(id, course);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        courseService.delete(id);
        return Response.noContent().build();
    }
}
