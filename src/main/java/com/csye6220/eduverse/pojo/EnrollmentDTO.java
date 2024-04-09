package com.csye6220.eduverse.pojo;

public class EnrollmentDTO {

    private Long studentId;

    private Long courseOfferingId;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(Long studentId, Long courseOfferingId) {
        this.studentId = studentId;
        this.courseOfferingId = courseOfferingId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
