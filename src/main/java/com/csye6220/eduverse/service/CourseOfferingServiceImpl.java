package com.csye6220.eduverse.service;

import com.csye6220.eduverse.dao.CourseDAO;
import com.csye6220.eduverse.dao.InstructorDAO;
import com.csye6220.eduverse.entity.Instructor;
import com.csye6220.eduverse.mapper.CourseMapper;
import com.csye6220.eduverse.pojo.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseOfferingServiceImpl implements CourseOfferingService {

    CourseDAO courseDAO;

    CourseMapper courseMapper;

    InstructorDAO instructorDAO;

    @Autowired
    public CourseOfferingServiceImpl(CourseDAO courseDAO, CourseMapper courseMapper, InstructorDAO instructorDAO) {
        this.courseDAO = courseDAO;
        this.courseMapper = courseMapper;
        this.instructorDAO = instructorDAO;
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
}
