package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.*;
import com.csye6220.eduverse.entity.CourseOffering;
import com.csye6220.eduverse.entity.Enrollment;
import com.csye6220.eduverse.entity.Instructor;
import com.csye6220.eduverse.entity.Student;
import com.csye6220.eduverse.mapper.CourseMapper;
import com.csye6220.eduverse.mapper.CourseOfferingMapper;
import com.csye6220.eduverse.pojo.CourseDTO;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.pojo.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseOfferingServiceImpl implements CourseOfferingService {

    private final CourseDAO courseDAO;

    private final CourseMapper courseMapper;

    private final InstructorDAO instructorDAO;

    private final CourseOfferingMapper courseOfferingMapper;

    private final CourseOfferingDAO courseOfferingDAO;
    private final EnrollmentDAO enrollmentDAO;

    @Autowired
    public CourseOfferingServiceImpl(CourseDAO courseDAO, CourseMapper courseMapper, InstructorDAO instructorDAO, CourseOfferingMapper courseOfferingMapper, CourseOfferingDAO courseOfferingDAO, EnrollmentDAO enrollmentDAO) {
        this.courseDAO = courseDAO;
        this.courseMapper = courseMapper;
        this.instructorDAO = instructorDAO;
        this.courseOfferingMapper = courseOfferingMapper;
        this.courseOfferingDAO = courseOfferingDAO;
        this.enrollmentDAO = enrollmentDAO;
    }

    @Override
    public List<CourseDTO> getAllCourses(String username) throws Exception {
        Instructor instructor = instructorDAO.getInstructorByUsername(username);
        if(Objects.isNull(instructor) || Objects.isNull(instructor.getDepartment())) {
            throw new Exception("Couldn't find Instructor's department");
        }
        return courseDAO.getCoursesByDepartment(instructor.getDepartment().getId())
                .stream()
                .map(course -> courseMapper.mapCoursesToDTO(course))
                .collect(Collectors.toList());
    }

    @Override
    public void createCourseOffering(CourseOfferingDTO courseOfferingDTO, String username) {
        CourseOffering courseOffering = courseOfferingMapper.mapDTOToCourseOffering(courseOfferingDTO, username);
        courseOfferingDAO.createCourseOffering(courseOffering);
    }

    @Override
    public boolean checkCurrentUserEnrollment(Long courseOfferingId, String name) {
        return enrollmentDAO.checkCurrentCourseEnrollment(courseOfferingId, name);
    }

    @Override
    public CourseOffering getCourseOfferingById(Long courseOfferingId) {
        return courseOfferingDAO.getCourseOfferingById(courseOfferingId);
    }

    @Override
    public boolean checkCurrentUserIsCourseInstructor(Long instructorId, String username) {
        Instructor instructor =  instructorDAO.getInstructorByUsername(username);
        return Objects.equals(instructor.getId(), instructorId);
    }

    @Override
    public CourseOfferingDTO mapCourseOfferingToDTO(CourseOffering courseOffering) {
        return courseOfferingMapper.mapCourseOfferingToDTO(courseOffering);
    }

    @Override
    public List<Student> getEnrolledStudents(Long courseOfferingId) {
        return enrollmentDAO.getEnrollmentsByCourseOfferingId(courseOfferingId)
                .stream()
                .map(Enrollment::getStudent)
                .toList();
    }

    @Override
    public boolean checkCourseOfferingExists(Long courseOfferingId) {
        CourseOffering courseOffering = getCourseOfferingById(courseOfferingId);
        return Objects.nonNull(courseOffering);
    }

    @Override
    public CourseOfferingDTO getCourseOfferingDTOById(Long courseOfferingId) {
        return mapCourseOfferingToDTO(getCourseOfferingById(courseOfferingId));
    }
}
