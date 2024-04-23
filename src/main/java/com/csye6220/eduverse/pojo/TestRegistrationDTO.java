package com.csye6220.eduverse.pojo;

public class TestRegistrationDTO {

    private String firstName;
    private String lastName;
    private Long departmentId;
    private String email;
    private String password;
    private String username;
    private String role;

    public TestRegistrationDTO() {
    }

    public TestRegistrationDTO(String firstName, String lastName, Long departmentId, String email, String password, String username, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
