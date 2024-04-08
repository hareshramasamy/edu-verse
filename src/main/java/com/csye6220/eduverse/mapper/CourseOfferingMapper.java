package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.dao.CourseDAO;
import com.csye6220.eduverse.dao.InstructorDAO;
import com.csye6220.eduverse.entity.Course;
import com.csye6220.eduverse.entity.Instructor;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.entity.CourseOffering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CourseOfferingMapper {

    CourseDAO courseDAO;

    InstructorDAO instructorDAO;
    @Autowired
    public CourseOfferingMapper(CourseDAO courseDAO, InstructorDAO instructorDAO) {
        this.courseDAO = courseDAO;
        this.instructorDAO = instructorDAO;
    }

    public CourseOfferingDTO mapCourseOfferingToDTO(CourseOffering courseOffering) {
        CourseOfferingDTO courseOfferingDTO = new CourseOfferingDTO();
        courseOfferingDTO.setCourseOfferingId(courseOffering.getId());
        courseOfferingDTO.setCourseId(Objects.nonNull(courseOffering.getCourse()) ? courseOffering.getCourse().getId() : null);
        courseOfferingDTO.setCourseCode(Objects.nonNull(courseOffering.getCourse()) ? courseOffering.getCourse().getCode() : "");
        courseOfferingDTO.setCourseTitle(Objects.nonNull(courseOffering.getCourse()) ? courseOffering.getCourse().getName() : "");
        courseOfferingDTO.setCourseDescription(Objects.nonNull(courseOffering.getCourse()) ? courseOffering.getCourse().getDescription() : "");
        courseOfferingDTO.setTerm(courseOffering.getTerm());
        courseOfferingDTO.setInstructorId(Objects.nonNull(courseOffering.getInstructor()) ? courseOffering.getInstructor().getId() : null);
        courseOfferingDTO.setInstructorName(Objects.nonNull(courseOffering.getInstructor()) && Objects.nonNull(courseOffering.getInstructor().getUser()) ? courseOffering.getInstructor().getUser().getFirstName() : "");
        courseOfferingDTO.setDepartmentId(Objects.nonNull(courseOffering.getCourse()) && Objects.nonNull(courseOffering.getCourse().getDepartment()) ? courseOffering.getCourse().getDepartment().getId() : null);
        courseOfferingDTO.setDepartmentName(Objects.nonNull(courseOffering.getCourse()) && Objects.nonNull(courseOffering.getCourse().getDepartment()) ? courseOffering.getCourse().getDepartment().getDepartmentName() : "");
        return courseOfferingDTO;
    }

    public CourseOffering mapDTOToCourseOffering(CourseOfferingDTO courseOfferingDTO, String username) {
        CourseOffering courseOffering = new CourseOffering();
        Course course = courseDAO.getCourseById(courseOfferingDTO.getCourseId());
        Instructor instructor = instructorDAO.getInstructorByUsername(username);
        courseOffering.setCourse(course);
        courseOffering.setTerm(courseOfferingDTO.getTerm());
        courseOffering.setInstructor(instructor);
        return courseOffering;
    }
}
