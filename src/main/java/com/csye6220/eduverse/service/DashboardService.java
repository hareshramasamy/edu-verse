package com.csye6220.eduverse.service;

import com.csye6220.eduverse.pojo.CourseOfferingDTO;

import java.util.List;

public interface DashboardService {
    List<CourseOfferingDTO> getEnrollmentsByStudent(String username);

    List<CourseOfferingDTO> getCoursesByInstructor(String name);
}
