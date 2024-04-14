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

    public StudentAssignmentDTO() {
    }

    public StudentAssignmentDTO(Long id, Long studentId, Long assignmentId, MultipartFile submissionData, String fileName, String fileType, LocalDateTime dateTime) {
        this.id = id;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
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
}
