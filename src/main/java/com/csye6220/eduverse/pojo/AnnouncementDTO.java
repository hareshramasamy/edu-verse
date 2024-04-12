package com.csye6220.eduverse.pojo;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class AnnouncementDTO {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private LocalDateTime dateTime;

    private Long instructorId;
    private String instructorName;

    private Long courseOfferingId;

    public AnnouncementDTO() {
    }

    public AnnouncementDTO(Long id,String title, String content, LocalDateTime dateTime, Long instructorId, Long courseOfferingId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.instructorId = instructorId;
        this.courseOfferingId = courseOfferingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
