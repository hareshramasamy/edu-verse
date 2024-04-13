package com.csye6220.eduverse.pojo;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class AssignmentDTO {

    private Long id;

    @NotEmpty(message = "Assignment Title is required")
    private String name;

    @NotEmpty(message = "Assignment Description is required")
    private String description;

    @FutureOrPresent(message = "The start date and time must be greater than current date and time")
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @FutureOrPresent(message = "The end date and time must be greater than current date and time")
    @NotNull(message = "Due date is required")
    private LocalDateTime endDate;

    private Long courseOfferingId;

    public AssignmentDTO() {
    }

    public AssignmentDTO(Long id, String name, String description, LocalDateTime startDate, LocalDateTime endDate, Long courseOfferingId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseOfferingId = courseOfferingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
