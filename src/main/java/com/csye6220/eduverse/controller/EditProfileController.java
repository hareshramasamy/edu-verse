package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.pojo.UserDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.UserService;
import com.csye6220.eduverse.validator.UpdateUserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Objects;

@Controller
public class EditProfileController {

    UserService userService;

    UpdateUserValidator updateUserValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(updateUserValidator);
    }

    @Autowired
    public EditProfileController(UserService userService, UpdateUserValidator updateUserValidator) {
        this.userService = userService;
        this.updateUserValidator = updateUserValidator;
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            model.addAttribute("user", userDTO);
        }
        model.addAttribute("activeTab", "account");
        return "profile";
    }

    @PostMapping("/profile")
    public String updateUserProfile(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("user", userDTO);
        } else {
            Authentication authentication = SecurityUtil.getSessionUser();
            if(Objects.nonNull(authentication)) {
                UserDTO updatedUserDTO = userService.updateUser(userDTO, authentication.getName());
                model.addAttribute("user", updatedUserDTO);
            }
        }
        model.addAttribute("activeTab", "account");
        return "profile";
    }
}
