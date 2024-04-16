package com.csye6220.eduverse.service;

import com.csye6220.eduverse.entity.CourseOffering;
import com.csye6220.eduverse.entity.Student;
import com.csye6220.eduverse.pojo.CourseDTO;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.pojo.UserDTO;

import java.util.List;

public interface CourseOfferingService {

    List<CourseDTO> getAllCourses(String username) throws Exception;

    void createCourseOffering(CourseOfferingDTO courseOfferingDTO, String username);

    boolean checkCurrentUserEnrollment(Long courseOfferingId, String name);

    CourseOffering getCourseOfferingById(Long courseOfferingId);
    boolean checkCurrentUserIsCourseInstructor(Long instructorId, String username);

    CourseOfferingDTO mapCourseOfferingToDTO(CourseOffering courseOffering);

    List<Student> getEnrolledStudents(Long courseOfferingId);
    boolean checkCourseOfferingExists(Long courseOfferingId);

    CourseOfferingDTO getCourseOfferingDTOById(Long courseOfferingId);

    void enrollStudentsToCourse(Long courseOfferingId, List<String> emailList);
}
