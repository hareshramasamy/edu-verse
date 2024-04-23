package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.EnrollmentDAO;
import com.csye6220.eduverse.entity.Enrollment;
import com.csye6220.eduverse.mapper.CourseOfferingMapper;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.dao.CourseOfferingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EnrollmentDAO enrollmentDAO;

    private final CourseOfferingDAO courseOfferingDAO;

    private final CourseOfferingMapper courseOfferingMapper;
    @Autowired
    public DashboardServiceImpl(EnrollmentDAO enrollmentDAO, CourseOfferingDAO courseOfferingDAO, CourseOfferingMapper courseOfferingMapper) {
        this.enrollmentDAO = enrollmentDAO;
        this.courseOfferingDAO = courseOfferingDAO;
        this.courseOfferingMapper = courseOfferingMapper;
    }

    @Override
    public List<CourseOfferingDTO> getEnrollmentsByStudent(String username) {
        List<CourseOfferingDTO> enrollments =  enrollmentDAO.getEnrollmentsByStudent(username)
                .stream()
                .map(Enrollment::getCourseOffering)
                .map(courseOfferingMapper::mapCourseOfferingToDTO)
                .toList();
        enrollments.forEach(courseOfferingDTO -> courseOfferingDTO.setRole("Student"));
        return enrollments;
    }

    @Override
    public List<CourseOfferingDTO> getCoursesByInstructor(String name) {
        List<CourseOfferingDTO> courseListForInstructor = courseOfferingDAO.getEnrollmentsByInstructor(name)
                .stream()
                .map(courseOfferingMapper::mapCourseOfferingToDTO)
                .toList();
        courseListForInstructor.forEach(courseOfferingDTO -> courseOfferingDTO.setRole("Instructor"));
        return courseListForInstructor;
    }
}
