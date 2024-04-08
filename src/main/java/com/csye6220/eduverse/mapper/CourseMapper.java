package com.csye6220.eduverse.mapper;

import com.csye6220.eduverse.entity.Course;
import com.csye6220.eduverse.pojo.CourseDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CourseMapper {

    public CourseDTO mapCoursesToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setCode(course.getCode());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setDepartmentId(Objects.nonNull(course.getDepartment()) ? course.getDepartment().getId() : null);
        return courseDTO;
    }
}
