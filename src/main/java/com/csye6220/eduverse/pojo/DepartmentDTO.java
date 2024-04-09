package com.csye6220.eduverse.pojo;

public class DepartmentDTO {

    private Long id;

    private String departmentName;

    private String departmentCode;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, String departmentName, String departmentCode) {
        this.id = id;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}
