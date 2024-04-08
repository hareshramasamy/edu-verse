package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.entity.User;
import com.csye6220.eduverse.pojo.DepartmentDTO;
import com.csye6220.eduverse.service.DepartmentService;
import com.csye6220.eduverse.service.RegistrationService;
import com.csye6220.eduverse.service.UserService;
import com.csye6220.eduverse.validator.RegistrationValidator;
import com.csye6220.eduverse.pojo.RegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.WebDataBinder;

import java.util.List;

@Controller
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final DepartmentService departmentService;

    private final RegistrationService registrationService;

    private final RegistrationValidator registrationValidator;

    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService, DepartmentService departmentService, RegistrationService registrationService, RegistrationValidator registrationValidator) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
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
        List<DepartmentDTO> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("registrationDTO", new RegistrationDTO());
        return "register";
    }

    @PostMapping("/register/users")
    @ResponseBody
    public void createUsers(@RequestBody List<User> users) {
        for(User user : users) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        }
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
