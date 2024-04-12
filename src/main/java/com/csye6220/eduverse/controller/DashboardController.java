package com.csye6220.eduverse.controller;

import com.csye6220.eduverse.entity.User;
import com.csye6220.eduverse.pojo.CourseOfferingDTO;
import com.csye6220.eduverse.pojo.UserDTO;
import com.csye6220.eduverse.security.SecurityUtil;
import com.csye6220.eduverse.service.DashboardService;
import com.csye6220.eduverse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class DashboardController {

    DashboardService dashboardService;

    UserService userService;

    @Autowired
    public DashboardController(DashboardService dashboardService, UserService userService) {
        this.dashboardService = dashboardService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getDashboard(Model model) {
        Authentication authentication = SecurityUtil.getSessionUser();
        if(Objects.nonNull(authentication)) {
            UserDTO userDTO = userService.searchByUserName(authentication.getName());
            if(SecurityUtil.isAuthorisedStudent(authentication)) {
                List<CourseOfferingDTO> coursesEnrolled = dashboardService.getEnrollmentsByStudent(authentication.getName());
                model.addAttribute("courses", coursesEnrolled);
            } else if(SecurityUtil.isAuthorisedInstructor(authentication)) {
                List<CourseOfferingDTO> coursesCreated = dashboardService.getCoursesByInstructor(authentication.getName());
                model.addAttribute("courses", coursesCreated);
            }
            model.addAttribute("userFullName", userDTO.getFirstName() + " " + userDTO.getLastName());
        }
        model.addAttribute("activeTab", "dashboard");
        return "dashboard";
    }
}
