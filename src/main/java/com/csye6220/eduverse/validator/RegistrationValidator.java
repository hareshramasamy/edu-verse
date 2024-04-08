package com.csye6220.eduverse.validator;

import com.csye6220.eduverse.entity.User;
import com.csye6220.eduverse.service.UserService;
import com.csye6220.eduverse.pojo.RegistrationDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class RegistrationValidator implements Validator {

    private final UserService userService;

    public RegistrationValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDTO registrationDTO = (RegistrationDTO) target;
        if(!registrationDTO.getEmail().isBlank() && !registrationDTO.getUsername().isBlank()) {
            validateExistingEmail(registrationDTO, errors);
            validateExistingUsername(registrationDTO, errors);
        }
        validatePasswordsMatch(registrationDTO, errors);
    }

    private void validateExistingEmail(RegistrationDTO registrationDTO, Errors errors) {
        User existingUserEmail = userService.searchByEmail(registrationDTO.getEmail());
        if (existingUserEmail != null) {
            errors.rejectValue("email", "Duplicate", "Email already exists");
        }
    }

    private void validateExistingUsername(RegistrationDTO registrationDTO, Errors errors) {
        User existingUserUsername = userService.searchByUserName(registrationDTO.getUsername());
        if (existingUserUsername != null) {
            errors.rejectValue("username", "Duplicate", "Username already exists");
        }
    }

    private void validatePasswordsMatch(RegistrationDTO registrationDTO, Errors errors) {
        if(Strings.isNotBlank(registrationDTO.getPassword()) && Strings.isNotBlank(registrationDTO.getConfirmPassword())) {
            if(!Objects.equals(registrationDTO.getPassword().trim(), registrationDTO.getConfirmPassword().trim())) {
                errors.rejectValue("confirmPassword", "Password Mismatch", "Passwords do not match");
            }
        }
    }
}
