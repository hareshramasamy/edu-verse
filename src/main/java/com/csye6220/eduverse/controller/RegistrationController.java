package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.pojo.DepartmentDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.DepartmentService;
import com.csye6220.eduverse.service.RegistrationService;
import com.csye6220.eduverse.validator.RegistrationValidator;
import com.csye6220.eduverse.pojo.RegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.WebDataBinder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class RegistrationController {

    private final DepartmentService departmentService;

    private final RegistrationService registrationService;

    private final RegistrationValidator registrationValidator;

    @Autowired
    public RegistrationController(DepartmentService departmentService, RegistrationService registrationService, RegistrationValidator registrationValidator) {
        this.departmentService = departmentService;
        this.registrationService = registrationService;
        this.registrationValidator = registrationValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(registrationValidator);
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();

        if(Objects.nonNull(authentication) && authentication.getAuthorities().stream().anyMatch(grantedAuthority -> Arrays.asList("ROLE_STUDENT", "ROLE_INSTRUCTOR").contains(grantedAuthority.getAuthority()))) {
            return "redirect:/";
        }

        List<DepartmentDTO> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@Valid @ModelAttribute("registrationDTO") RegistrationDTO registrationDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
                List<DepartmentDTO> departmentList = departmentService.getAllDepartments();
                model.addAttribute("departmentList", departmentList);
                model.addAttribute("registrationDTO",registrationDTO);
                return "register";
            }
        registrationService.registerUser(registrationDTO);
        return "success-page";
    }
}

