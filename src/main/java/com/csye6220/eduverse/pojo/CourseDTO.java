package com.csye6220.eduverse.pojo;

public class CourseDTO {

    private Long id;

    private String name;

    private String description;

    private String code;

    private Long departmentId;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String name, String description, String code, Long departmentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.departmentId = departmentId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}