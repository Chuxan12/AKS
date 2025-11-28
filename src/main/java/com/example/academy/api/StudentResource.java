package com.example.academy.api;

import com.example.academy.api.dto.StudentPayload;
import com.example.academy.entity.Student;
import com.example.academy.service.StudentService;
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

@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    @EJB
    private StudentService studentService;

    @GET
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GET
    @Path("{id}")
    public Student findOne(@PathParam("id") Long id) {
        Student student = studentService.find(id);
        if (student == null) {
            throw new NotFoundException("Student not found");
        }
        return student;
    }

    @POST
    public Response create(StudentPayload payload) {
        Student created = studentService.create(toEntity(payload), payload.getCourseId());
        return Response.created(URI.create("/api/students/" + created.getId()))
                .entity(created)
                .build();
    }

    @PUT
    @Path("{id}")
    public Student update(@PathParam("id") Long id, StudentPayload payload) {
        return studentService.update(id, toEntity(payload), payload.getCourseId());
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        studentService.delete(id);
        return Response.noContent().build();
    }

    private Student toEntity(StudentPayload payload) {
        Student student = new Student();
        student.setFullName(payload.getFullName());
        student.setEmail(payload.getEmail());
        student.setStudyYear(payload.getStudyYear());
        return student;
    }
}
