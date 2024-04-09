package com.csye6220.eduverse.pojo;

import java.time.LocalDateTime;

public class AnnouncementDTO {

    private String title;

    private String content;

    private LocalDateTime dateTime;

    private Long instructorId;

    private Long courseOfferingId;

    public AnnouncementDTO() {
    }

    public AnnouncementDTO(String title, String content, LocalDateTime dateTime, Long instructorId, Long courseOfferingId) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.instructorId = instructorId;
        this.courseOfferingId = courseOfferingId;
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

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
