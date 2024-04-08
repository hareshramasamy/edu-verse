package com.csye6220.eduverse.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RegistrationDTO {

    @NotEmpty(message = "First Name is required")
    private String firstName;
    @NotEmpty(message = "Last Name is required")
    private String lastName;
    @NotNull(message = "Integer value must not be null")
    @Min(value = 1, message = "Integer value must be greater than 0")
    private Long departmentId;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
    @NotEmpty(message = "Confirm Password is required")
    private String confirmPassword;
    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Role is required")
    private String role;
}
