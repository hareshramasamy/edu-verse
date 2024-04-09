package com.csye6220.eduverse.pojo;

import java.util.Date;

public class AssignmentDTO {

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private Long courseOfferingId;

    public AssignmentDTO() {
    }

    public AssignmentDTO(String name, String description, Date startDate, Date endDate, Long courseOfferingId) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseOfferingId = courseOfferingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
