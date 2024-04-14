package com.csye6220.eduverse.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_assignment")
public class StudentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB", name = "submission_data")
    private byte[] submissionData;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public StudentAssignment() {
    }

    public StudentAssignment(Long id, Student student, Assignment assignment, byte[] submissionData, String fileName, String fileType, LocalDateTime dateTime) {
        this.id = id;
        this.student = student;
        this.assignment = assignment;
        this.submissionData = submissionData;
        this.fileName = fileName;
        this.fileType = fileType;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public byte[] getSubmissionData() {
        return submissionData;
    }

    public void setSubmissionData(byte[] submissionData) {
        this.submissionData = submissionData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
