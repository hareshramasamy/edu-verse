package com.csye6220.eduverse.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseOfferingDTO {

    private Long courseOfferingId;
    private Long departmentId;
    private Long instructorId;
    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private String courseCode;
    private String term;
    private String instructorName;
    private String departmentName;
}
