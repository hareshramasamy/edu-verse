package com.csye6220.eduverse.pojo;

import jakarta.validation.constraints.*;

public class RegistrationDTO {

    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @NotNull(message = "Integer value must not be null")
    @Min(value = 1, message = "Integer value must be greater than 0")
    private Long departmentId;
    @Email
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Role is required")
    private String role;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String firstName, String lastName, Long departmentId, String email, String password, String confirmPassword, String username, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
