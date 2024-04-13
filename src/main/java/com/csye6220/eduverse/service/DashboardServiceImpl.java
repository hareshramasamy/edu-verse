package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.EnrollmentDAO;
import com.csye6220.eduverse.dao.TAAssignmentsDAO;
import com.csye6220.eduverse.entity.Enrollment;
import com.csye6220.eduverse.entity.TAAssignment;
import com.csye6220.eduverse.mapper.CourseOfferingMapper;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.dao.CourseOfferingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EnrollmentDAO enrollmentDAO;

    private final CourseOfferingDAO courseOfferingDAO;

    private final CourseOfferingMapper courseOfferingMapper;

    private final TAAssignmentsDAO taAssignmentsDAO;

    @Autowired
    public DashboardServiceImpl(EnrollmentDAO enrollmentDAO, CourseOfferingDAO courseOfferingDAO, CourseOfferingMapper courseOfferingMapper, TAAssignmentsDAO taAssignmentsDAO) {
        this.enrollmentDAO = enrollmentDAO;
        this.courseOfferingDAO = courseOfferingDAO;
        this.courseOfferingMapper = courseOfferingMapper;
        this.taAssignmentsDAO = taAssignmentsDAO;
    }

    @Override
    public List<CourseOfferingDTO> getEnrollmentsByStudent(String username) {
        List<CourseOfferingDTO> taAssignments = taAssignmentsDAO.getTAAssignmentsByStudent(username)
                .stream()
                .map(TAAssignment::getCourseOffering)
                .map(courseOffering -> courseOfferingMapper.mapCourseOfferingToDTO(courseOffering))
                .toList();
        taAssignments.forEach(courseOfferingDTO -> courseOfferingDTO.setRole("TA"));
        List<CourseOfferingDTO> enrollments =  enrollmentDAO.getEnrollmentsByStudent(username)
                .stream()
                .map(Enrollment::getCourseOffering)
                .map(courseOffering -> courseOfferingMapper.mapCourseOfferingToDTO(courseOffering))
                .toList();
        enrollments.forEach(courseOfferingDTO -> courseOfferingDTO.setRole("Student"));
        return Stream.concat(taAssignments.stream(), enrollments.stream())
                .toList();
    }

    @Override
    public List<CourseOfferingDTO> getCoursesByInstructor(String name) {
        List<CourseOfferingDTO> courseListForInstructor = courseOfferingDAO.getEnrollmentsByInstructor(name)
                .stream()
                .map(courseOffering -> courseOfferingMapper.mapCourseOfferingToDTO(courseOffering))
                .toList();
        courseListForInstructor.forEach(courseOfferingDTO -> courseOfferingDTO.setRole("Instructor"));
        return courseListForInstructor;
    }
}
