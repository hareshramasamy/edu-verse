package com.csye6220.eduverse.entity;

import jakarta.persistence.*;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double score;

    private String comment;

    @JoinColumn(name = "student_assignment_id")
    @OneToOne()
    private StudentAssignment studentAssignment;

    public Grade() {
    }

    public Grade(Long id, double score, StudentAssignment studentAssignment, String comment) {
        this.id = id;
        this.score = score;
        this.studentAssignment = studentAssignment;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public StudentAssignment getStudentAssignment() {
        return studentAssignment;
    }

    public void setStudentAssignment(StudentAssignment studentAssignment) {
        this.studentAssignment = studentAssignment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
