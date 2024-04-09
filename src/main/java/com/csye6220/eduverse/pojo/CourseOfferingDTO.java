package com.csye6220.eduverse.pojo;

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
    private String role;

    public CourseOfferingDTO() {
    }

    public CourseOfferingDTO(Long courseOfferingId, Long departmentId, Long instructorId, Long courseId, String courseTitle, String courseDescription, String courseCode, String term, String instructorName, String departmentName, String role) {
        this.courseOfferingId = courseOfferingId;
        this.departmentId = departmentId;
        this.instructorId = instructorId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseCode = courseCode;
        this.term = term;
        this.instructorName = instructorName;
        this.departmentName = departmentName;
        this.role = role;
    }

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
