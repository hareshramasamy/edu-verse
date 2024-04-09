package com.csye6220.eduverse.pojo;

public class StudentAssignmentDTO {
    private Long studentId;

    private Long assignmentId;

    public StudentAssignmentDTO() {
    }

    public StudentAssignmentDTO(Long studentId, Long assignmentId) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }
}
