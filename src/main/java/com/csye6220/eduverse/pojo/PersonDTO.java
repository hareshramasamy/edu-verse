package com.csye6220.eduverse.pojo;

import jakarta.validation.constraints.NotBlank;

public class PersonDTO {

    @NotBlank(message = "Email cannot be blank")
    private String email;

    public PersonDTO() {
    }

    public PersonDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }
}
