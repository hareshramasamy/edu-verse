package com.csye6220.eduverse.pojo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class GradeDTO {

    @DecimalMin(value = "0.0", message = "Enter a value from 0 to 100")
    @DecimalMax(value = "100.0", message = "Enter a value from 0 to 100")
    private double score;
    private Long studentAssignmentId;
    private Long assignmentId;
    private String assignmentName;
    private Long studentId;
    private String studentName;
    private String comment;
    private LocalDateTime dueDate;
    private LocalDateTime submissionDate;

    public GradeDTO() {
    }
    public GradeDTO(double score, Long studentAssignmentId, Long assignmentId, String assignmentName, Long studentId, String studentName, String comment, LocalDateTime dueDate, LocalDateTime submissionDate) {
        this.score = score;
        this.studentAssignmentId = studentAssignmentId;
        this.assignmentId = assignmentId;
        this.assignmentName = assignmentName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.comment = comment;
        this.dueDate = dueDate;
        this.submissionDate = submissionDate;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}
