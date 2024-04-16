package com.csye6220.eduverse.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class StudentAssignmentDTO {
    private Long id;
    private Long studentId;

    private Long assignmentId;

    private MultipartFile submissionData;

    private String fileName;

    private String fileType;

    private LocalDateTime dateTime;

    private String studentName;

    private String studentEmail;

    private String assignmentName;

    public StudentAssignmentDTO() {
    }

    public StudentAssignmentDTO(Long id, Long studentId, Long assignmentId, MultipartFile submissionData, String fileName, String fileType, LocalDateTime dateTime, String studentName, String studentEmail, String assignmentName) {
        this.id = id;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.submissionData = submissionData;
        this.fileName = fileName;
        this.fileType = fileType;
        this.dateTime = dateTime;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.assignmentName = assignmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MultipartFile getSubmissionData() {
        return submissionData;
    }

    public void setSubmissionData(MultipartFile submissionData) {
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }
}
