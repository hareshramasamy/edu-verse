package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.EnrollmentDAO;
import com.csye6220.eduverse.entity.Enrollment;
import com.csye6220.eduverse.mapper.CourseOfferingMapper;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.dao.CourseOfferingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    EnrollmentDAO enrollmentDAO;

    CourseOfferingDAO courseOfferingDAO;

    CourseOfferingMapper courseOfferingMapper;

    @Autowired
    public DashboardServiceImpl(EnrollmentDAO enrollmentDAO, CourseOfferingDAO courseOfferingDAO, CourseOfferingMapper courseOfferingMapper) {
        this.enrollmentDAO = enrollmentDAO;
        this.courseOfferingDAO = courseOfferingDAO;
        this.courseOfferingMapper = courseOfferingMapper;
    }

    @Override
    public List<CourseOfferingDTO> getEnrollmentsByStudent(String username) {
        return enrollmentDAO.getEnrollmentsByStudent(username)
                .stream()
                .map(Enrollment::getCourseOffering)
                .map(courseOffering -> courseOfferingMapper.mapCourseOfferingToDTO(courseOffering))
                .toList();
    }

    @Override
    public List<CourseOfferingDTO> getCoursesByInstructor(String name) {
        return courseOfferingDAO.getEnrollmentsByStudent(name)
                .stream()
                .map(courseOffering -> courseOfferingMapper.mapCourseOfferingToDTO(courseOffering))
                .toList();
    }
}
