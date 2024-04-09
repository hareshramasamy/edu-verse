package com.csye6220.eduverse.validator;

import com.csye6220.eduverse.pojo.UserDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Controller
public class UpdateUserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UpdateUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            UserDTO currentuserDTO = userService.searchByUserName(authentication.getName());
            if(!userDTO.getEmail().isBlank() && !userDTO.getUsername().isBlank()) {
                validateExistingEmail(userDTO,currentuserDTO, errors);
            }
        }
    }

    private void validateExistingEmail(UserDTO userDTO, UserDTO currentUserDTO, Errors errors) {
        UserDTO existingUserEmail = userService.searchByEmail(userDTO.getEmail());
        if (existingUserEmail != null && !Objects.equals(userDTO.getEmail(), currentUserDTO.getEmail())) {
            errors.rejectValue("email", "Duplicate", "Email already exists");
        }
    }
}
