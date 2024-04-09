package com.csye6220.eduverse.pojo;

public class GradeDTO {

    private double score;

    private Long studentAssignmentId;

    public GradeDTO() {
    }

    public GradeDTO(double score, Long studentAssignmentId) {
        this.score = score;
        this.studentAssignmentId = studentAssignmentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Long getStudentAssignmentId() {
        return studentAssignmentId;
    }

    public void setStudentAssignmentId(Long studentAssignmentId) {
        this.studentAssignmentId = studentAssignmentId;
    }
}
