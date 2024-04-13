package com.csye6220.eduverse.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class FileDTO {

    private Long id;

    private MultipartFile data;

    private String fileName;

    private String fileType;

    private LocalDateTime dateTime;

    private Long courseOfferingId;

    public FileDTO() {
    }

    public FileDTO(Long id, MultipartFile data, String fileName, String fileType, LocalDateTime dateTime, Long courseOfferingId) {
        this.id = id;
        this.data = data;
        this.fileName = fileName;
        this.fileType = fileType;
        this.dateTime = dateTime;
        this.courseOfferingId = courseOfferingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getData() {
        return data;
    }

    public void setData(MultipartFile data) {
        this.data = data;
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

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
